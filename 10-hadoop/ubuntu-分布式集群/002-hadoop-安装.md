

## 操作系统

> ubuntu 22.04



## ubuntu 安装 一下lrzsz

```shell
sudo apt-get update
sudo apt-get install lrzsz
```

## 

## 创建用户 hdp

```shell
adduser hdp
```

## 为hdp 用户添加sudo 权限

> 参考 05-ubuntu/ubunt-添加用户.md 文档



## 配置hosts

> 为所有主机配置对应的主机名，让相互之间能通过内网IP互相访问
>
> > vim /etc/hosts
>
> ```txt
> 172.31.3.101 hadoop101
> 172.31.3.102 hadoop102
> 172.31.3.103 hadoop103
> 172.31.3.104 hadoop104
> 172.31.3.105 hadoop105
> 172.31.3.106 hadoop106
> 172.31.3.107 hadoop107
> 172.31.3.108 hadoop108
> ```
>
> 使用ping 进行测试验证，以防止哪一个写错了。
>
> ```shell
> ping hadoop101 -c 3
> ping hadoop102 -c 3
> ping hadoop103 -c 3
> ping hadoop104 -c 3
> ping hadoop105 -c 3
> ping hadoop106 -c 3
> ping hadoop107 -c 3
> ping hadoop108 -c 3
> ```
>
> 



## 下载地址

```txt
https://dlcdn.apache.org/hadoop/common/
https://downloads.apache.org/hadoop/common/
```

## 安装好jdk

> 忽略

## 安装hadoop

### 解压

```shell
tar -zxvf hadoop-3.2.4.tar.gz  -C /opt/module
```

### 配置环境变量

> `vim /etc/profile`

```shell
# hadoop 环境配置
export HADOOP_HOME=/opt/module/hadoop-3.2.4
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

```



## 主机规划



| 主机名 | hadoop101    | hadoop102       | hadoop103         |
| ------ | ------------ | --------------- | ----------------- |
| IP     | 172.31.3.101 | 172.31.3.102    | 172.31.3.103      |
| HDFS   | NameNode     |                 | SecondaryNameNode |
| YARN   |              | ResourceManager |                   |
|        |              |                 |                   |



| 主机名 | hadoop104    | hadoop105    | hadoop106    | hadoop107    | hadoop108    |
| ------ | ------------ | ------------ | ------------ | ------------ | ------------ |
| IP     | 172.31.3.104 | 172.31.3.105 | 172.31.3.106 | 172.31.3.107 | 172.31.3.108 |
| HDFS   | DataNode     | DataNode     | DataNode     | DataNode     | DataNode     |
| YARN   | NodeManager  | NodeManager  | NodeManager  | NodeManager  | NodeManager  |



## 配置好ssh 免密登录

> 忽略
>
> 需要注意的是，如果是ubuntu 系统，需要处理 ssh 连接上环境变量没正常处理的情况，需要处理`~/.bashrc` 文件的开关处。
>
> 不过这里我还想尝试下，hadoop 有自己的 hadoop-env.sh 脚本，是否是他自己所需要的相关的脚本文件。



### 默认配置文件

| 要获取的默认文件     | 文件存放在hadoop 的jar 包中的位置                         |
| -------------------- | --------------------------------------------------------- |
| [core-default.xml]   | hadoop-common-3.1.3.jar/core-default.xml                  |
| [hdfs-default.xml]   | hadoop-hdfs-3.1.3.jar/hdfs-default.xml                    |
| [yarn-default.xml]   | hadoop-yarn-common-3.1.3.jar/yarn-default.xml             |
| [mapred-default.xml] | hadoop-mapreduce-client-core-3.1.3.jar/mapred-default.xml |



### 自定义配置文件

> 所在目录：$HADOOP_HOME/etc/hadoop
>
> 即：/opt/module/hadoop-3.2.4/etc/hadoop

| 默认配置文件         | 自定义配置文件             |
| -------------------- | -------------------------- |
| [core-default.xml]   | etc/hadoop/core-site.xml   |
| [hdfs-default.xml]   | etc/hadoop/hdfs-site.xml   |
| [yarn-default.xml]   | etc/hadoop/yarn-site.xml   |
| [mapred-default.xml] | etc/hadoop/mapred-site.xml |

- etc/hadoop/core-site.xml

  ```xml
  <configuration>
      <!-- 指定NameNode的地址，hadoop 内网之间进行通信的端口 -->
      <property>
          <name>fs.defaultFS</name>
          <value>hdfs://hadoop101:8020</value>
      </property>
  
      <!-- 指定hadoop数据的存储目录 -->
      <property>
          <name>hadoop.tmp.dir</name>
          <!-- 注意一下这个地方换成对应的目录位置 -->
          <value>/opt/module/hadoop-3.2.4/data</value>
      </property>
  
      <!-- 配置HDFS网页登录使用的静态用户 -->
      <property>
          <name>hadoop.http.staticuser.user</name>
          <value>hdp</value>
      </property>
  </configuration>
  ```

  

- etc/hadoop/hdfs-site.xml

  ```xml
  <configuration>
      <!-- nn(NameNode) web端访问地址，提供给用户使用的页面地址-->
      <property>
          <name>dfs.namenode.http-address</name>
          <value>hadoop101:9870</value>
      </property>
  	<!-- 2nn(SecondaryNameNode) web端访问地址-->
      <property>
          <name>dfs.namenode.secondary.http-address</name>
          <value>hadoop103:9868</value>
      </property>
      <!-- 
      <property>
          <name>dfs.permissions.enabled</name>
          <value>false</value>
          <description>如果为"true"，则在HDFS中启用权限检查;如果为"false"，则关闭权限检查;默认值为"true"。</description>
      </property>
      -->
  </configuration>
  ```

  

- etc/hadoop/yarn-site.xml

  ```xml
  <configuration>
      <!-- 指定MR走shuffle -->
      <property>
          <name>yarn.nodemanager.aux-services</name>
          <value>mapreduce_shuffle</value>
      </property>
  
      <!-- 指定yarn ResourceManager的地址-->
      <property>
          <name>yarn.resourcemanager.hostname</name>
          <value>hadoop102</value>
      </property>
  
      <!-- 环境变量的继承 -->
      <!-- TODO: suyh - 听说在3.2 版本以上就不需要配置了？？？ -->
      <property>
          <name>yarn.nodemanager.env-whitelist</name>
          <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
      </property>
  </configuration>
  
  ```

  

- etc/hadoop/mapred-site.xml

  ```xml
  <configuration>
      <!-- 指定MapReduce程序运行在Yarn上 -->
      <property>
          <name>mapreduce.framework.name</name>
          <value>yarn</value>
      </property>
  </configuration>
  ```

  

### 配置workers

> 所在目录：$HADOOP_HOME/etc/hadoop
>
> 即：/opt/module/hadoop-3.2.4/etc/hadoop/worker
>
> 要注意不要有任何的其他空格或者空行

```txt
hadoop001
hadoop002
hadoop003
```

### 首次运行需要格式化 NameNode

> 注意：只需要格式化NameNode 所在的那台机器实例
>
> ```shell
> hdfs namenode -format
> ```
>
> 初始化完成会多出一个data 目录(存数据的目录) 以及log 目录
>
> 如果要重新格式化，则需要将 data 和log 目录删除之后再执行，否则会报错

## 启动

### 启动HDFS

> 注意：只需要在hadoop101 上面启动即可
>
> 不过，似乎在任意一台机器上面都可以启动呢
>
> $HADOOP_HOME/sbin/start-dfs.sh
>
> 如果启动报错，用户啥的权限啥的，就需要添加一些环境配置
>
> 还有就是JAVA_HOME 的配置

报错示例：

```txt
root@hadoop001:/opt/module/hadoop-3.2.4# ./sbin/start-dfs.sh 
Starting namenodes on [hadoop001]
ERROR: Attempting to operate on hdfs namenode as root
ERROR: but there is no HDFS_NAMENODE_USER defined. Aborting operation.
Starting datanodes
ERROR: Attempting to operate on hdfs datanode as root
ERROR: but there is no HDFS_DATANODE_USER defined. Aborting operation.
Starting secondary namenodes [hadoop003]
ERROR: Attempting to operate on hdfs secondarynamenode as root
ERROR: but there is no HDFS_SECONDARYNAMENODE_USER defined. Aborting operation.
```

> vim  etc/hadoop/hadoop-env.sh 

```shell
export JAVA_HOME=/usr/local/java/jdk1.8.0_202

# 这里的root 是根据上面的配置来的：hadoop.http.staticuser.user
export HDFS_NAMENODE_USER=root
export HDFS_DATANODE_USER=root
export HDFS_SECONDARYNAMENODE_USER=root
export YARN_RESOURCEMANAGER_USER=root
export YARN_NODEMANAGER_USER=root
```

### 停止HDFS

> $HADOOP_HOME/sbin/stop-dfs.sh

#### 成功之后会如配置的一样

- hadoop001

  ```txt
  root@hadoop001:/opt/module/hadoop-3.2.4# jps
  7095 Jps
  6830 DataNode
  6654 NameNode
  ```

- hadoop002

  ```txt
  root@hadoop002:/opt/module/hadoop-3.2.4# jps
  4338 DataNode
  4462 Jps
  ```

- hadoop003

  ```txt
  root@hadoop003:/opt/module/hadoop-3.2.4# jps
  4275 SecondaryNameNode
  4375 Jps
  4136 DataNode
  ```

### 启动(YARN)

>  ResourceManager

> 规划在hadoop002 上面，那么就需要到对应的机器 实例去运行启动脚本
>
> $HADOOP_HOME/sbin/start-yarn.sh

### 停止YARN

> $HADOOP_HOME/sbin/stop-yarn.sh

#### 成功之后会如配置的一样

- hadoop001

  ```txt
  root@hadoop001:/opt/module/hadoop-3.2.4# jps
  7203 NodeManager
  7306 Jps
  6830 DataNode
  6654 NameNode
  ```

- hadoop002

  ```txt
  root@hadoop002:/opt/module/hadoop-3.2.4# jps
  4338 DataNode
  4566 ResourceManager
  5050 Jps
  4910 NodeManager
  ```

- hadoop003

  ```txt
  root@hadoop003:/opt/module/hadoop-3.2.4# jps
  4275 SecondaryNameNode
  4487 NodeManager
  4136 DataNode
  4589 Jps
  ```

### web 页面

- HDFS 提供的NameNode页面

  > hadoopNameNode:9870
  >
  > 查看在HDFS 上存储的数据信息

- YARN 提供的ResourceManager页面

  > hadoopYarnRM:8088
  >
  > 查看YARN 上运行的Job 信息

## 历史服务器

### 配置(每一台实例)

> vim  etc/hadoop/mapred-site.xml
>
> ```xml
>     <!-- 历史服务器端地址 -->
>     <property>
>            <name>mapreduce.jobhistory.address</name>
>            <value>hadoop001:10020</value>
>     </property>
> 
>     <!-- 历史服务器web端地址 -->
>     <property>
>            <name>mapreduce.jobhistory.webapp.address</name>
>            <value>hadoop001:19888</value>
>     </property>
> ```

### 启动(在hadoop001 机器上面运行)

> ${HADOOP_HOME}/bin/mapred --daemon start historyserver

### 停止

> ${HADOOP_HOME}/bin/mapred --daemon stop historyserver

### 页面访问

> hadoop001:19888



## 常用端口

```txt
hadoop3.x
    HDFS NameNode 内部通信商品：8020 9000 9820
    HDFS NameNode 对用户的查询端口：9870
    Yarn 查看任务运行情况的：8088
    历史服务器：19888
hadoop2.x
    HDFS NameNode 内部通信商品：8020 9000 
    HDFS NameNode 对用户的查询端口：50070
    Yarn 查看任务运行情况的：8088
    历史服务器：19888
```

## 常用配置文件

```txt
hadoop3.x
    core-site.xml
    hdfs-site.xml
    yarn-site.xml
    mapred-site.xml
    workers
hadoop2.x
    core-site.xml
    hdfs-site.xml
    yarn-site.xml
    mapred-site.xml
    slaves
```

