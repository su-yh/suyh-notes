





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
```

