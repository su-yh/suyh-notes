







## 特别提醒

> 在使用阿里云时，/etc/hosts 文件会默认有一行主机名对应IP 的记录。这一行记录一定要处理掉，不然就有可能 有问题。

```txt
127.0.0.1       localhost

# The following lines are desirable for IPv6 capable hosts
::1     ip6-localhost   ip6-loopback
fe00::0 ip6-localnet
ff00::0 ip6-mcastprefix
ff02::1 ip6-allnodes
ff02::2 ip6-allrouters
127.0.1.1       Aliyun

# 就是这一行
172.31.3.1      iZwz9hz1tiurpbd2nqbkt6Z iZwz9hz1tiurpbd2nqbkt6Z

```

> 在NameNode 格式化的时候，日志记录大概如下

```txt
/************************************************************
SHUTDOWN_MSG: Shutting down NameNode at iZwz9hz1tiurpbd2nqbktbZ/172.31.3.6
************************************************************/
```

> 如果要处理log4j 需要将hadoop 在每一台机器实例上的log4j 依赖jar 包都处理掉才行。
>
> 所以搞了这么久，还是不管了就使用log4j 而不使用logback 了。太废劲了！！！



## 报错处理

1. 日志问题

   ```txt
   Exception in thread "main" java.lang.ExceptionInInitializerError
   Caused by: org.apache.logging.log4j.LoggingException: log4j-slf4j-impl cannot be present with log4j-to-slf4j
   	at org.apache.logging.slf4j.Log4jLoggerFactory.validateContext(Log4jLoggerFactory.java:60)
   	at org.apache.logging.slf4j.Log4jLoggerFactory.newLogger(Log4jLoggerFactory.java:44)
   	at org.apache.logging.slf4j.Log4jLoggerFactory.newLogger(Log4jLoggerFactory.java:33)
   	at org.apache.logging.log4j.spi.AbstractLoggerAdapter.getLogger(AbstractLoggerAdapter.java:53)
   	at org.apache.logging.slf4j.Log4jLoggerFactory.getLogger(Log4jLoggerFactory.java:33)
   	at org.slf4j.LoggerFactory.getLogger(LoggerFactory.java:363)
   	at org.slf4j.LoggerFactory.getLogger(LoggerFactory.java:388)
   	at org.apache.flink.runtime.entrypoint.ClusterEntrypoint.<clinit>(ClusterEntrypoint.java:117)
   ```

   提交之后报日志包冲突

   处理：将springboot 的log4j 相关的包删除。

2. 其他







## flink部署

### 集群角色

- JobManager
- TaskManger
- FlinkClient

### 集群规划

| 节点服务器 | hadoop001   | hadoop002   | hadoop003   |
| ---------- | ----------- | ----------- | ----------- |
| 角色       | JobManager  | --          | --          |
|            | TaskManager | TaskManager | TaskManager |
|            |             |             |             |



### 下载安装包

官网：https://flink.apache.org

下载地址：https://dlcdn.apache.org/flink/flink-1.17.2/flink-1.17.2-bin-scala_2.12.tgz



 

```shell
# 解压在每一台机器
tar -zxvf flink-1.17.2-bin-scala_2.12.tgz -C /opt/module/
cd /opt/module/flink-1.17.2

```



### 配置

#### 修改配置 JobManager

所有机器实例上面都要配置

> vim  ${FLINK_HOME}/conf/flink-conf.yaml

```properties
# JobManager节点地址.
jobmanager.rpc.address: hadoop001
jobmanager.bind-host: 0.0.0.0
# web-ui 绑定的地址
rest.address: hadoop001
rest.bind-address: 0.0.0.0
# TaskManager节点地址.需要配置为当前机器名
# 注意：每一台机器都要配置成自己的主机名
taskmanager.host: hadoop001
taskmanager.bind-host: 0.0.0.0
```

> 修改workers
>
> vim conf/workers

```properties
hadoop001
hadoop002
hadoop003
```

> vim conf/masters

```properties
hadoop001:8081
```

### 启动/停止集群

> 这个还是得在hadoop001 上面运行，我试了一下，在其他的机器上面运行的话 StandaloneSessionClusterEntrypoint 就会在该台机器上面启动了。
>
> bin/start-cluster.sh
>
> bin/stop-cluster.sh
>
> 如果报错，就安装一个jdk 就好

### 网页访问

> hadoop001:8081
>
> 而访问 hadoop002:8081 是访问不通的，看来是只能在JobManager 所在的ip 才能访问。

### 命令行提交job

```shell
bin/flink run -m hadoop102:8081 -c com.atguigu.wc.SocketStreamWordCount ./FlinkTutorial-1.0-SNAPSHOT.jar
```

### 部署模式

> 它们的区别主要在于：集群的生命周期以及资源 的分配方式；以及应用的main 方法到底在哪里执行--客户端(Client) 还是JobManager

- 会话模式(Session Mode)

  ```txt
  会话模式比较适合于单个规模小、执行时间短的大量作业。
  ```

- 单作业模式(Per-Job Mode)  `在新版本中已经被标记为过时了`

  ```txt
  一个作业一个flink集群，它需要借助一些外部资源 管理系统，比较yarn 
  ```

- 应用模式(Application Mode)

  ```txt
  直接把应用提交到JobManager 上运行。而这也就代表着，我们需要为每一个提交的应用单独启动一个JobManager，也就是创建一个集群。这个JobManager 只为执行这一个应用而存在，执行结束之后JobManager 也就关闭了，这就是所谓的应用模式。
  ```


### 运行模式

> 运行模式主要在于由谁来管理资源

- Standalone (了解)
- YARN(重点)
- K8S(了解)

#### YARN模式

- 配置环境变量

  > vim /etc/profile

  ```shell
  # hadoop 环境配置，如下两个一般安装好了hadoop 它们应该是配置好了的
  # export HADOOP_HOME=/opt/module/hadoop-3.2.4
  # export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
  
  # flink yarn 配置
  export HADOOP_CONF_DIR=${HADOOP_HOME}/etc/hadoop
  export HADOOP_CLASSPATH=`hadoop classpath`
  ```

- 启动hdfs（参考hadoop）

- 启动yarn（参考hadoop）

- 其他

#### 会话模式(YARN-SESSION)

> ```shell
> # 默认方式启动
> root@hadoop001:/opt/module/flink-1.17.2# ./bin/yarn-session.sh
> 
> # 分离模式启动，不占用终端，并指定一个名称
> root@hadoop001:/opt/module/flink-1.17.2# ./bin/yarn-session.sh -d -nm test
> ```
>
> 停止
>
> ```shell
> echo "stop" | ./bin/yarn-session.sh -id application_1680702304497_0003
> ```
>
> 



##### 命令提交作业到yarn 模式

> ```shell
> # 跟上面的比较这里不需要通过 -m 指定IP:PORT 他会自动找到yarn
> bin/flink run -c com.atguigu.wc.SocketStreamWordCount ./FlinkTutorial-1.0-SNAPSHOT.jar
> ```

#### 应用模式提交作业

> ```shell
> bin/flink run-application -t yarn-application -c com.suyh.d01.WordCount03StreamUnboundedDemo ./d01.jar
> ```

#### hdfs 提交作业

> 四个目录，在flink-jars 根目录下面，提供给flink hdfs 的目录就给flink-jars
>
> ```shell
> root@flinkYarnClient:~/flink-jars# ls flink-jars
> flink-app  flink-lib  flink-plugins  flink-springboot
> 
> bin/flink run-application -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoopNameNode:8020/flink-jars"  hdfs://hadoopNameNode:8020/flink-app/d05-flink-app.jar
> ```
>
> 

> ${FLINK_HOME}/lib
>
> ${FLINK_HOME}/plugins
>
> ```shell
># 将flink 的自身依赖上传到hdfs
> # 在HDFS 上面创建目录
> root@hadoop001:/opt/module/flink-1.17.2# hadoop fs -mkdir /flink-dist
> # 将 lib 目录和plugins 目录上传到 hdfs 的/flink-dist 目录下面
> root@hadoop001:/opt/module/flink-1.17.2# hadoop fs -put lib/ /flink-dist
> root@hadoop001:/opt/module/flink-1.17.2# hadoop fs -put plugins/ /flink-dist
> 
> # 将自己的程序所依赖的包上传到hdfs
> # 创建一个目录，用来管理自己的jar 包
> root@hadoop001:/opt/module/flink-1.17.2# hadoop fs -mkdir /flink-jars
> # 将自己的flink 运行程序包上传
> root@hadoop001:/opt/module/flink-1.17.2# hadoop fs -put d01.jar /flink-jars
> 
> # 提交作业
> # yarn.provided.lib.dirs 指定是的flink 自身的依赖包
> bin/flink run-application -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop001:8020/flink-dist" -c com.suyh.d01.WordCount03StreamUnboundedDemo hdfs://hadoop001:8020/flink-jars/d01.jar
> ```

## 历史服务器

### 创建存储目录

```shell
hadoop fs -mkdir -p /logs/flink-job
```

### 配置

> vim conf/flink-conf.yaml

```yaml
jobmanager.archive.fs.dir: hdfs://hadoop001:8020/logs/flink-job
historyserver.web.address: hadoop001
historyserver.web.port: 8082
historyserver.archive.fs.dir: hdfs://hadoop001:8020/logs/flink-job
historyserver.archive.fs.refresh-interval: 5000
```

### 启动/停止历史服务器

> bin/historyserver.sh start
>
> bin/historyserver.sh stop



## 日志

```properties
# 我也不知道在哪里去找到的这个配置项了。
-Dlogback.configurationFile=xxx
```

