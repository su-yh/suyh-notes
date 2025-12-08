







## 入口文件

文件：`resources/META-INF/services/org.apache.flink.table.factories.Factory`

显然是利用java 的SPI 机制实现的

## 入口类

==MySqlTableSourceFactory==

关键类：`MySqlTableSource`  --> `MySqlSource`  --> `DebeziumSourceFunction` --> `DebeziumChangeConsumer` implament `DebeziumEngine.ChangeConsumer`









