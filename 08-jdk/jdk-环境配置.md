



```shell
# 解压
tar -zxvf jdk-8u202-linux-x64.tar.gz -C /usr/local/java/
```





```shell
# 配置JAVA 环境
# vim /etc/profile
export JAVA_HOME=/usr/local/java/jdk1.8.0_202
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${PATH}:${JAVA_HOME}/bin:${JRE_HOME}/jre/bin


```

