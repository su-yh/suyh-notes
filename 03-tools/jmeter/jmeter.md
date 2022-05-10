### JSON提取器

主要是用来处理http 请求的响应体。从响应体中提取结果值。

```json
{
  "success": true,
  "code": 0,
  "msg": "success",
  "data": {
    "uuid": "f55c1c65-dc7a-4cd3-bd8d-f30463831139",
    "hostIp": "7.180.149.119",
    "port": 51470
  }
}
```

json 提取表达式: `$.[?(@.success)].data.uuid`

当`success` 的结果为true 时，获取`uuid` 的值

得到的值为: `f55c1c65-dc7a-4cd3-bd8d-f30463831139`

### 调试取样器

它是直接创建出来 就好，需要配合`察看结果树`一起使用，它会将所有的变量的值都以结果的形式显示出来。

### 常量吞吐量定时器

固定每分钟发起的请求数量

