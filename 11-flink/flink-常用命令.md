







```shell

# 从保存点恢复，这里的目录要指定到 chk-4 那一层级才行，也就是里面有 _metadata 文件的那一级目录
# bin/flink run -s :savepointPath [:runArgs]
bin/flink run -p 2 -d -m localhost:8911 -s file:///home/suyunhong/module/flink-1.13.1/cds/v30/checkpoints/5612f5e546861a5b5ea6ad6cbe937c64/chk-4/  -c com.leomaster.process.FlinkMainProcess  ./flink_dynamic_trend_oper-30.jar

```



```shell
# 启动命令
/home/suyunhong/module/flink-1.13.1/bin/flink run  -p 4 -d -m localhost:8911 -c com.leomaster.process.FlinkMainProcess  ./flink_dynamic_trend_oper-32.jar --aiteerCfg /home/suyunhong/module/flink-1.13.1/cds/v32/conf/flink-job.properties --activeEnv sit
```



```shell
# 在yarn 集群模式下提交成功的命令
# hdfs上的目录：
# /flink-jars/usrlib/ 这是用户目录，这个目录名(usrlib)不能改，必须是它。
# /flink-jars/usrlib/flink-springboot 这是打包好的springboot 的相关目录，使用spring-boot-maven-plugin 打包的jar 文件
# /flink-jars/flink-dist 用来存放flink 的相关jar 包
# /flink-jars/flink-dist/fink-dist  这是使用 maven-shade-plugin 插件打包的 flink 依赖的相关包，不过这个目录似乎可以直接放到用户目录下面
# /flink-jars/flink-dist/lib   这是执行flink 命令所使用的lib，我不知道是不是这样的，但是确实这样是可以运行不报错的。还要注意这个目录名(lib) 似乎不让用其他的
# /flink-jars/flink-dist/plugin 这是执行flink 命令所使用的plugin，我不知道是不是这样的，但是确实这样是可以运行不报错的。还要注意这个目录名(plugin) 似乎不让用其他的，加个s 都不行，会报错的。
bin/flink run-application -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoopNameNode:8020/flink-jars/flink-dist" -Dyarn.provided.usrlib.dir="hdfs://hadoopNameNode:8020/flink-jars/usrlib" hdfs://hadoopNameNode:8020/flink-jars/flink-app/d05-flink-app.jar



# 可以将-Dyarn.provided.lib.dirs="hdfs://hadoopNameNode:8020/flink-jars/flink-dist" -Dyarn.provided.usrlib.dir="hdfs://hadoopNameNode:8020/flink-jars/usrlib" 这两个参数添加到配置文件: conf/flink-conf.yaml 中
# yarn.provided.lib.dirs: hdfs://hadoopNameNode:8020/flink-jars/flink-dist
# yarn.provided.usrlib.dir: hdfs://hadoopNameNode:8020/flink-jars/usrlib
# 然后执行命令时参数就少了很多
bin/flink run-application -t yarn-application hdfs://hadoopNameNode:8020/flink-jars/flink-app/d05-flink-app.jar
```

