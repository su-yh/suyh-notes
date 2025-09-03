如果想要远程访问hive 数据库，则需要部署hiveserver2 服务



模拟用户功能

模拟 用户功能依赖于hadoop ，所以需要在hadoop 中添加相关配置：==core-site.xml== 原来的配置项不用动

```xml
<configuration>
  <property>
    <name>hadoop.proxyuser.hdp.hosts</name>
    <value>*</value>
  </property>
  <property>
    <name>hadoop.proxyuser.hdp.groups</name>
    <value>*</value>
  </property>
  <property>
    <name>hadoop.proxyuser.hdp.users</name>
    <value>*</value>
  </property>

</configuration>

```



hive 端配置

==hive-site.xml==

```xml

  <property>
    <name>hive.server2.thrift.bind.host</name>
      <value>0.0.0.0</value>
    <description>Bind host on which to run the HiveServer2 Thrift service.</description>
  </property>

  <property>
    <name>hive.server2.thrift.http.port</name>
    <value>10000</value>
    <description>Port number of HiveServer2 Thrift interface when hive.server2.transport.mode is 'http'.</description>
  </property>

```





启用 hiveserver2

```shell
# 2>&1 是指标准错误与标准输出重定向到同一个地方
nohup ${HIVE_HOME}/bin/hiveserver2 > /dev/null 2>&1 &
```





测试验证

```shell
hdp@user:~/apache-hive-3.1.2/bin$ ./beeline
Beeline version 3.1.2 by Apache Hive
beeline> !connect jdbc:hive2://hadoop-suyh:10000
Connecting to jdbc:hive2://hadoop-suyh:10000
Enter username for jdbc:hive2://hadoop-suyh:10000: hdp
Enter password for jdbc:hive2://hadoop-suyh:10000: 
Connected to: Apache Hive (version 3.1.2)
Driver: Hive JDBC (version 3.1.2)
Transaction isolation: TRANSACTION_REPEATABLE_READ
0: jdbc:hive2://hadoop-suyh:10000> show tables;
INFO  : Compiling command(queryId=hdp_20250901070542_489be2ea-ddb6-465b-a562-a1301091a891): show tables
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Semantic Analysis Completed (retrial = false)
INFO  : Returning Hive schema: Schema(fieldSchemas:[FieldSchema(name:tab_name, type:string, comment:from deserializer)], properties:null)
INFO  : Completed compiling command(queryId=hdp_20250901070542_489be2ea-ddb6-465b-a562-a1301091a891); Time taken: 1.034 seconds
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Executing command(queryId=hdp_20250901070542_489be2ea-ddb6-465b-a562-a1301091a891): show tables
INFO  : Starting task [Stage-0:DDL] in serial mode
INFO  : Completed executing command(queryId=hdp_20250901070542_489be2ea-ddb6-465b-a562-a1301091a891); Time taken: 0.044 seconds
INFO  : OK
INFO  : Concurrency mode is disabled, not creating a lock manager
+-----------+
| tab_name  |
+-----------+
| stu       |
+-----------+
1 row selected (1.876 seconds)
0: jdbc:hive2://hadoop-suyh:10000> 
```



