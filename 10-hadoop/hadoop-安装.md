



## 安装好jdk

> jdk 8  还可以用



## 安装hadoop

### 解压

```shell
tar -zxvf hadoop-3.2.4.tar.gz  -C /opt/module
```

### 配置环境变量

> `vim /etc/profile`

```shell
export HADOOP_HOME=/opt/module/hadoop-3.2.4

export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

```





