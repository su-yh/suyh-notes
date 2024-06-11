





## yarn 模式

```txt
在yarn 模式下，我们只需要在提交flink 所在的机器实例配置好flink 的相关配置即可。
对于flink 的worker 节点，这些都交由yarn 来管理和分配。
所以这里面我们只需要配置好hadoop 以及yarn 相关的资源即可。
然后在flink 提交主机节点，修改好相关的配置项即可。
在flink 提交主机节点，修改配置文件就可以被提交到yarn 并运行。
如果是不同的flink 版本，我们只需要将对应的 jar 包提交到hdfs ，然后在运行flink 的时候指定对应目录即可，参数名为：-Dyarn.provided.lib.dirs="hdfs://hadoop001:8020/flink-dist"
或者这个也都可以直接放到flink.conf 配置文件中。
上面的这些都要验证测试一下。
还有一点要注意就是使用yarn 模式时，要注意配置虚拟核心数，如果核心数太少，而槽又配置太多，那么每个槽都会对应一个核心数。

默认值为-1，与槽的数量一致。不过该配置需要yarn 的调度器配置为公平调度器：FairScheduler
yarn.containers.vcores: 4
```



### 在hdfs 上创建目录

1.  根目录：`/flink`

2. flink 相关的jar 包: `/flink/flink-dist`

   在该目录下面目录名不能随便给，只能给：`lib`、`flink-dist`、`plugin`

3. 用户目录 `/flink/usrlib/`

   该目录名不能随便给，只能是`usrlib`

4. spring boot 的依赖包，放在 `/flink/usrlib/springboot-jars/`目录下面即可。

5. 打包好的flink 运行程序包，放在 `/flink/app-jar/` 目录下即可。

6. 其他

### 提交作业命令

> 提交作业时指定相关文件以及目录

```shell
# -Dyarn.provided.lib.dirs="hdfs://hadoopNameNode:8020/flink/flink-dist"
# -Dyarn.provided.usrlib.dir="hdfs://hadoopNameNode:8020/flink/usrlib" 
# hdfs://hadoopNameNode:8020/flink/app-jar/xxx.jar
bin/flink run-application -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoopNameNode:8020/flink/flink-dist" -Dyarn.provided.usrlib.dir="hdfs://hadoopNameNode:8020/flink/usrlib" hdfs://hadoopNameNode:8020/flink/app-jar/xxx.jar
```

