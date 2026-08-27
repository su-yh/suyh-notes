# MinIO 对象存储配置指南（medcase\-dev）

> 部分内容由豆包生成
> 
> 

# 文档说明

本文档面向开发与运维人员，说明如何在 MinIO 控制台完成一套对象存储访问配置：创建存储桶（Bucket）、创建读写策略（Policy）、创建用户并绑定策略、生成访问密钥（Access Key）。整个过程均通过 Web 控制台完成，无需命令行。

目标结果：得到一组**仅能访问 ****`medcase-dev`**** 桶**的 Access Key / Secret Key，供后端服务对接使用，避免使用 root 管理员账号直接对接业务。

# 前置条件

- MinIO 控制台已部署并可通过浏览器访问，默认端口为 `9001`。

- 拥有 root 管理员账号（默认 `minioadmin`）及密码，可登录控制台。

- 建议先确认本次对接的访问权限类型：读写（后端上传/下载/删除）或只读（仅前端读图）。

# 命名规划

为避免多环境（dev / sit / prod）之间权限混乱，建议按下表统一命名。本指南以 **dev 环境**为例。

|对象|命名规则|本示例|
|---|---|---|
|存储桶|`<业务>-<环境>`|`medcase-dev`|
|读写策略|`<业务>-<环境>-<权限>`|`medcase-dev-rw`|
|用户|`<业务>-<环境>-svc`|`medcase-dev-svc`|

说明：存储桶是"数据存放位置"，用户/访问密钥是"访问身份"。一个用户可被授权访问多个桶；按环境区分用户，可确保 dev 的密钥无法访问 sit / prod 的数据。

# 第一步：创建存储桶（Bucket）

1. 浏览器打开 MinIO 控制台，使用 root 账号登录。

2. 左侧菜单点击 **Buckets（存储桶）**。

3. 点击右上角 **Create Bucket（创建存储桶）**。

4. 在 **Bucket Name** 中输入 `medcase-dev`。

5. 按需配置可选项：

    - **Versioning（版本控制）**：建议开启，可保留对象历史版本，防止误覆盖/误删。

    - **Object Lock（对象锁）**：一般业务不需要，默认不开启。

    - **Quota（配额）**：可设置桶容量上限（如 `10 GiB`）。

6. 点击 **Create Bucket** 完成创建。

**可观察结果：**存储桶列表中新增 `medcase-dev`，点击进入后对象列表为空。

**桶命名规范：**MinIO 桶名仅支持小写字母、数字、点（\.）和连字符（\-），长度 3–63 位，**不支持下划线**。例如 `medcase_dev` 是不合法的。

# 第二步：创建读写策略（Policy）

创建一个仅针对 `medcase-dev` 桶的自定义策略，用于后续绑定到用户。

1. 左侧菜单点击 **Identity → Policies（身份 → 策略）**。

2. 点击 **Create Policy**。

3. 在 **Name** 中输入 `medcase-dev-rw`。

4. 在 JSON 编辑框中粘贴以下读写策略：

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:ListBucket",
        "s3:GetBucketLocation",
        "s3:GetObject",
        "s3:PutObject",
        "s3:DeleteObject"
      ],
      "Resource": [
        "arn:aws:s3:::medcase-dev",
        "arn:aws:s3:::medcase-dev/*"
      ]
    }
  ]
}
```

**关键说明：**Resource 必须同时包含两行——不带 `/*` 的是桶本身（供 ListBucket 列表操作使用），带 `/*` 的是桶内对象（供上传/下载/删除使用），两者缺一不可。

**只读变体：**若业务只需读取（如前端展示图片），删去 `s3:PutObject` 与 `s3:DeleteObject` 两行即可变为只读策略，建议命名为 `medcase-dev-ro`。

# 第三步：创建用户并绑定策略

1. 左侧菜单点击 **Identity → Users（身份 → 用户）**。

2. 点击 **Create User**。

3. 在 **Username** 中输入 `medcase-dev-svc`，并设置一个登录密码。

4. 在 **Assign Policies（分配策略）** 中勾选上一步创建的 `medcase-dev-rw`。

5. 点击 **Save** 完成创建。

**可观察结果：**用户列表中出现 `medcase-dev-svc`，其关联策略为 `medcase-dev-rw`。

**绑定机制说明：**MinIO 中访问密钥（Access Key）背后对应一个用户，用户权限由附加的策略决定。将策略绑定到用户而非直接写死到密钥，好处是后续修改策略（如改只读、增加新桶权限）可对用户下所有密钥同步生效，权限源头清晰、可审计、可单独回收。

# 第四步：生成访问密钥（Access Key）

1. 退出当前 root 账号，使用 `medcase-dev-svc` 账号重新登录控制台。

2. 左侧菜单点击 **Access Keys（访问密钥）**。

3. 点击 **Create access key（创建访问密钥）**。

4. **Restrict beyond user policy（超出用户策略的限制）** 保持 **OFF** 即可——密钥会自动继承用户已绑定的 `medcase-dev-rw` 策略。

5. 建议在 **Name** 中填写用途说明（如 `medcase-dev-svc`），便于日后识别。

6. **Expiry** 保持 **No Expiry**（业务密钥不要设过期时间，避免突然失效）。

7. 点击 **Create** 创建。

8. **立即复制并妥善保存弹出的 Access Key 与 Secret Key**——Secret Key 仅显示一次，关闭页面后无法再次查看。

**特别注意：**若以 root 管理员身份创建 Access Key，且未做额外限制，该密钥将继承 root 的全部管理员权限，可访问所有桶，无法起到"仅绑定 medcase\-dev"的隔离效果。因此必须使用 `medcase-dev-svc` 登录后创建。

# 第五步：验证

1. 确认 `medcase-dev-svc` 密钥可正常访问 `medcase-dev` 桶。

2. （可选）用该密钥访问其他桶，应返回 `AccessDenied`，说明隔离生效。

# 后续环境扩展（SIT / PROD）

新增环境时，按同一模式复制即可：

1. 创建存储桶 `medcase-sit`、`medcase-prod`。

2. 创建对应读写策略 `medcase-sit-rw`、`medcase-prod-rw`（Resource 中桶名相应替换）。

3. 创建用户 `medcase-sit-svc`、`medcase-prod-svc` 并绑定对应策略。

4. 分别登录创建 Access Key，分发给对应环境的后端服务。

# 常见问题

- **访问其他桶报 AccessDenied：**符合预期，说明策略隔离生效；请确认该桶是否应授权给当前用户。

- **上传/下载报 AccessDenied：**检查策略 JSON 的 Resource 是否包含 `桶名/*`，以及 Action 是否包含 `s3:PutObject` / `s3:GetObject`。

- **Connection refused：**检查控制台端口（9001）与 API 端口（9000）是否开放、防火墙是否放行。

- **密钥丢失无法找回：**Secret Key 只展示一次；可在 Access Keys 页面删除后重新创建。

\</document\>

> （注：部分内容可能由 AI 生成）
