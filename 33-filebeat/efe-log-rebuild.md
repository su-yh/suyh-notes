# suyh-efe-log 干净重建（REST API 脚本）

> **适用范围**：`suyh-efe-log` 日志链路（索引 / 索引模板 / ILM 策略）的干净重建。
> **删除范围严格限定** `suyh-efe-log-*`，**不影响其它索引与业务数据**。
> ⚠️ **会清空现有 `suyh-efe-log-*` 索引的数据**，重建后需重新用 filebeat 灌数据。
> 在 Kibana 的 **Dev Tools** 里逐条复制执行；`#` 开头是注释，不执行。

---

## A. 查询是否存在（先看现状）

### A1. 查询索引
```json
GET /_cat/indices/suyh-efe-log-*?v
```
期望：列出所有 suyh-efe-log-* 索引；若为空说明当前没有。

### A2. 查询索引模板
```json
GET /_index_template/suyh-efe-log-template
```
期望：返回模板定义；不存在则 404。

### A3. 查询 ILM 策略
```json
GET /_ilm/policy/suyh-efe-log-policy
```
期望：返回策略；不存在则 404。

---

## B. 删除（得到干净环境，按此顺序执行）

> **顺序很关键**：必须先删索引，再删策略——因为 ILM 策略正被索引引用时不能删除。

### B1. 删除索引（清掉现有 suyh-efe-log-* 数据）
```json
DELETE /suyh-efe-log-*
```

### B2. 删除索引模板
```json
DELETE /_index_template/suyh-efe-log-template
```

### B3. 删除 ILM 策略
```json
DELETE /_ilm/policy/suyh-efe-log-policy
```

### B4. 验证已删干净（三个都应空/404）
```json
GET /_cat/indices/suyh-efe-log-*?v
GET /_index_template/suyh-efe-log-template
GET /_ilm/policy/suyh-efe-log-policy
```

---

## C. 重建

### C1. 创建 ILM 策略
```json
PUT _ilm/policy/suyh-efe-log-policy
{
  "policy": {
    "phases": {
      "hot": {
        "min_age": "0ms",
        "actions": {
          "rollover": { "max_size": "50GB", "max_age": "30d" },
          "set_priority": { "priority": 100 }
        }
      },
      "warm": {
        "min_age": "30d",
        "actions": {
          "forcemerge": { "max_num_segments": 1 },
          "set_priority": { "priority": 50 }
        }
      },
      "cold": {
        "min_age": "60d",
        "actions": { "set_priority": { "priority": 0 } }
      },
      "delete": {
        "min_age": "90d",
        "actions": { "delete": {} }
      }
    }
  }
}
```

### C2. 创建索引模板（log.original = text + ik_max_word）
```json
PUT _index_template/suyh-efe-log-template
{
  "index_patterns": ["suyh-efe-log-*"],
  "priority": 100,
  "template": {
    "settings": {
      "index": {
        "number_of_shards": 3,
        "number_of_replicas": 1,
        "codec": "best_compression",
        "refresh_interval": "5s",
        "lifecycle": {
          "name": "suyh-efe-log-policy",
          "rollover_alias": "suyh-efe-log"
        }
      }
    },
    "mappings": {
      "dynamic_templates": [
        {
          "strings_as_keyword": {
            "match_mapping_type": "string",
            "mapping": { "type": "keyword", "ignore_above": 1024 }
          }
        }
      ],
      "properties": {
        "@timestamp": { "type": "date" },
        "log": {
          "properties": {
            "original": {
              "type": "text",
              "analyzer": "ik_max_word",
              "fields": { "keyword": { "type": "keyword", "ignore_above": 256 } }
            },
            "level":     { "type": "keyword" },
            "thread":    { "type": "keyword" },
            "logger":    { "type": "keyword" },
            "line":      { "type": "integer" },
            "timestamp_ms": { "type": "long" },
            "date":      { "type": "integer" },
            "trace_id":  { "type": "keyword" },
            "trace_user":{ "type": "keyword" },
            "user_id":   { "type": "keyword" },
            "file": {
              "properties": { "path": { "type": "keyword" } }
            }
          }
        }
      }
    },
    "aliases": {
      "suyh-efe-log": {}
    }
  }
}
```

### C3. 创建首个索引（带写别名）
```json
PUT suyh-efe-log-20261022
{
  "aliases": {
    "suyh-efe-log": { "is_write_index": true }
  }
}
```

### C4. 验证重建结果
```json
GET /_cat/indices/suyh-efe-log-*?v
GET /suyh-efe-log-20261022/_mapping/field/log.original
```
`log.original` 应返回：
```json
"original": { "type": "text", "analyzer": "ik_max_word", ... }
```

---

## D. 重新灌数据（filebeat 重跑）

> 必须先清 filebeat 注册表（否则认为已读过，不会重发）。

```bash
cd /Users/suyunhong/suyh-develop/github/suyh-notes/33-filebeat
rm -rf efe/data-es
/Users/suyunhong/suyh-develop/medcase/filebeat-7.17.29-darwin-x86_64/filebeat -e --once -c os_es/filebeat-efe-es.yml
```

灌完验证文档数（efe.log 应为 60）：
```json
GET /suyh-efe-log-20261022/_count
```
