为了给您最清晰的视角，我们需要区分 **DDL（结构定义）** 和 **DML（数据操作）** 的支持范围。

**特别说明**：这里的“支持”，是指在使用 **Flink CDC + Doris Connector** 进行整库同步或表同步时，**上游数据库（如 MySQL）发生变更，能否自动同步到 Doris**。如果是纯 Flink SQL 写死 Schema 的方式，DDL 是不存在同步概念的。

以下是基于目前主流版本（Flink Doris Connector 1.4.x - 1.6.x 及 Flink CDC 3.0）的详细兼容性矩阵。

---

### 一、 DDL 支持矩阵 (Schema Change)

Doris Connector 对 DDL 的支持主要集中在“表结构演进”，而非“数据库管理”。

#### ✅ 支持的 DDL (自动同步)

| DDL 操作 | 详细说明 | 注意事项 |
| --- | --- | --- |
| **ADD COLUMN** | **完全支持**。上游新增列，Doris 表自动追加该列。 | 默认追加到末尾。 |
| **DROP COLUMN** | **支持 (需配置)**。上游删除列，Doris 同步删除。 | 默认往往关闭，防止误删。需开启配置（如 `sink.enable-schema-change` 或具体框架的 `drop-column` 开关）。 |
| **RENAME COLUMN** | **部分支持**。上游改列名，Doris 同步修改。 | 依赖 Doris 版本（通常需 Doris 1.2+）及 Connector 版本。本质是先 Add 后 Drop 或直接 Rename。 |
| **MODIFY COLUMN** | **有限支持**。修改列的数据类型（如 `int` -> `bigint`）。 | 仅支持 **兼容性修改**（宽类型兼容窄类型）。若上游将 `String` 改为 `Int`，Doris 可能会报错或同步失败，因为 Doris 修改类型的限制比 MySQL 严格。 |
| **CREATE TABLE** | **支持** (仅限整库同步模式)。 | 当上游创建一个新表，且该表在同步的正则表达式范围内，Connector 会自动在 Doris 创建对应的表。 |

#### ❌ 不支持的 DDL (通常被忽略或报错)

| DDL 操作 | 说明 | 原因/替代方案 |
| --- | --- | --- |
| **DROP TABLE** | **不支持** | 为了数据安全，防止上游误删导致数仓数据丢失。需运维手动处理。 |
| **TRUNCATE TABLE** | **不支持** | 破坏性操作，Connector 会忽略此信号。需手动执行 `TRUNCATE TABLE`。 |
| **RENAME TABLE** | **不支持** | 涉及底层元数据映射断裂。需手动重建映射或迁移数据。 |
| **CREATE/DROP INDEX** | **不支持** | Doris 的索引体系（Bitmap/BloomFilter/Inverted）与 MySQL/PG 完全不同，无法直接映射同步。 |
| **ADD/DROP PARTITION** | **不支持** | Doris 通常使用“动态分区”功能自动管理分区，而不依赖上游的显式分区指令。 |
| **CONSTRAINT** | **不支持** | 如外键（Foreign Key）、Check 约束等不会同步。Doris 主要是分析型数据库，不强依赖外键约束。 |
| **DEFAULT VALUE** | **有限支持** | 修改默认值通常不会自动同步去修改 Doris 的元数据。 |

---

### 二、 DML 支持矩阵 (Data Manipulation)

Doris Connector 对 DML 的支持非常完善，但依赖于你所选择的 **Doris 表模型**（Unique Key, Aggregate Key, Duplicate Key）。

#### ✅ 支持的 DML

| DML 操作 | 支持情况 | 依赖模型与原理 |
| --- | --- | --- |
| **INSERT** | **完美支持** | 所有模型均支持。这是最基础的 Append 操作。 |
| **UPDATE** | **支持 (Upsert)** | **必须使用 Unique Key 模型**。Flink 此时发送的数据包含主键，Doris 收到相同主键的数据会覆盖旧数据（即 Upsert）。<br>

<br>如果是 Aggregate 模型，则根据聚合函数（Sum/Max/Replace）更新。 |
| **DELETE** | **支持** | **必须开启配置**。Flink CDC 会捕获 `DELETE` 事件（RowKind=DELETE），Connector 会将其翻译为 Doris 的删除信号（Hidden Column `__DORIS_DELETE_SIGN__` 或 Stream Load 的 Delete Flag）。 |

#### ⚠️ DML 的特殊限制

1. **Duplicate Key 模型**：
* 该模型下，**没有主键概念**。
* Flink 发送的 `UPDATE` 会被当作两条数据处理：一条 `DELETE`（可能无法执行）+ 一条 `INSERT`，或者直接变成两条 `INSERT`（导致数据重复）。
* **结论**：如果上游有 UPDATE/DELETE 操作，**不要使用 Duplicate Key 模型**，必须用 Unique Key 模型。


2. **Partial Update (部分列更新)**：
* **支持**。比如上游只 Update 了 3 个字段，Flink Connector 可以配置为只发送这 3 个字段和主键。
* **条件**：需要在 Doris 端开启 `enable_unique_key_merge_on_write` (MOW) 表属性，以获得最佳性能。



---

### 三、 总结配置建议

如果你希望尽可能多地支持 DDL 和 DML，建议的生产环境配置组合如下：

1. **Doris 表模型**：使用 **Unique Key** 模型，并开启 **Merge-on-Write** (MOW)。
2. **Flink配置**：
* 开启 `sink.enable-delete = true` (支持删除)。
* 开启 `sink.enable-schema-change = true` (支持 ADD/DROP 字段)。


3. **操作习惯**：
* **加减列**：放心在源端做。
* **删表/截断/改表名**：**严禁**依赖自动同步，必须在业务低峰期通过运维手段在源端和目标端配合手动执行。



**您目前是否遇到了某个具体的 DDL 报错，或者是在进行技术选型调研？** 如果是报错，可以把错误日志贴一部分给我，我可以帮您定位原因。


