





## 默认配置文件

### 位置

- core-default.xml

  > hadoop-common-3.1.3.jar/core-default.xml

- hdfs-default.xml

  > hadoop-hdfs-3.1.3.jar/hdfs-default.xml

- yarn-default.xml

  > hadoop-yarn-common-3.1.3.yar/yarn.default.xml

- mapred-default.xml

  > hadoop-mapreduce-client-core-3.1.3.jar/mapred-default.xml

## 自定义配置文件

### 位置

> 下面的这四个文件存放在 `$HADOOP_HOME/etc/hadoop` 

- core-site.xml

  ```xml
  <configuration>
      <!-- 指定NameNode的地址，hadoop 内网之间进行通信的端口 -->
      <property>
          <name>fs.defaultFS</name>
          <value>hdfs://hadoop001:8020</value>
      </property>
  
      <!-- 指定hadoop数据的存储目录 -->
      <property>
          <name>hadoop.tmp.dir</name>
          <value>/opt/module/hadoop-3.2.4/data</value>
      </property>
  
      <!-- 配置HDFS网页登录使用的静态用户 -->
      <property>
          <name>hadoop.http.staticuser.user</name>
          <value>hdp</value>
      </property>
  </configuration>
  ```

  

- hdfs-site.xml

  ```xml
  <configuration>
      <!-- nn(NameNode) web端访问地址，提供给用户使用的页面地址-->
      <property>
          <name>dfs.namenode.http-address</name>
          <value>hadoop001:9870</value>
      </property>
  	<!-- 2nn(SecondaryNameNode) web端访问地址-->
      <property>
          <name>dfs.namenode.secondary.http-address</name>
          <value>hadoop003:9868</value>
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

  

- yarn-site.xml

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
          <value>hadoop002</value>
      </property>
  
      <!-- 环境变量的继承 -->
      <!-- TODO: suyh - 听说在3.2 版本以上就不需要配置了？？？ -->
      <property>
          <name>yarn.nodemanager.env-whitelist</name>
          <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
      </property>
  </configuration>
  
  ```

  

- mapred-site.xml

  ```xml
  <configuration>
      <!-- 指定MapReduce程序运行在Yarn上 -->
      <property>
          <name>mapreduce.framework.name</name>
          <value>yarn</value>
      </property>
  </configuration>
  ```

  