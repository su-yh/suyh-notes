







```shell

# 从保存点恢复，这里的目录要指定到 chk-4 那一层级才行，也就是里面有 _metadata 文件的那一级目录
# bin/flink run -s :savepointPath [:runArgs]
bin/flink run -p 2 -d -m localhost:8911 -s file:///home/suyunhong/module/flink-1.13.1/cds/v30/checkpoints/5612f5e546861a5b5ea6ad6cbe937c64/chk-4/  -c com.leomaster.process.FlinkMainProcess  ./flink_dynamic_trend_oper-30.jar

```



```shell
# 启动命令
/home/suyunhong/module/flink-1.13.1/bin/flink run  -p 4 -d -m localhost:8911 -c com.leomaster.process.FlinkMainProcess  ./flink_dynamic_trend_oper-32.jar --aiteerCfg /home/suyunhong/module/flink-1.13.1/cds/v32/conf/flink-job.properties --activeEnv sit
```



