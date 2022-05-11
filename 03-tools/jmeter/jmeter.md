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

### 时间戳函数

`${__time(yyyy-MM-dd HH:mm:ss.SSS,)}` 格式化时间：`2022-05-10 17:21:05.811`

`${__time(/1000,)}` 当前时间的秒值：`1652174465`

`${__time(,)}` 当前时间的毫秒值：`1652174465000`

### BeanShell PostProcessor 【BeanShell 后置处理器】

jmeter后置处理器主要完善测试脚本部分，进行数据关联，常用的后置处理器有正则表达式提取器（主要用于正则匹配）、JSON Extractor（适用于json提取）、BeanShellPostProcessor，当遇到负责的逻辑提取时BeanShellPostProcessor就比较方便了。比如：数据比较大且返回有多个list，正则表达式提取器需人工去判断需要取的值是否存在并且在什么位置，效率会有所降低并且容易出错，针对这种情况可以使用jmeter自带的功能后置处理器BeanShell PostProcessor，分别提取每个list的值。 

示例：

```java
// 由于jmeter 中对数字默认转换成int 类型，而时间的数值范围超过了int 的范围，
// 所以需要先处理成字符串然后再转换成long 类型，不然老是报错。
long tempLong = Long.parseLong("${__time(,)}");

tempLong += (30 - 1) * 60 * 1000;
// 打印日志
log.info("tempLong: {}", tempLong);

// 将结果写回到变量: expiredLoginTimestamp(用户变量)
// 但只能写成字符串，后续jmeter 会自己识别类型
vars.put("expiredLoginTimestamp", tempLong + "");

```



### if 条件控制器

expression中不能直接填写条件表达式，需要借助函数将条件表达式计算为true/false，可以借助的函数有_jexl3和_groovy，例如：直接填写${modelId}==5，是不能识别的 

示例：

```shell
${__groovy(${expiredLoginTimestamp} <= ${__time(,)})}
```



