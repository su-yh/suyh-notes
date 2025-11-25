

同步mysql 数据库表到 doris



flink 版本：1.20.3

一共需要4 个依赖jar

前置准备：需要mysql 对应的账户有复制bin-log 的权限

```shell
# 还需要准备好mysql 驱动
flink20@user:~/flink/flink-1.20.3$ ls -lh ${FLINK_HOME}/lib/cdc/
total 2.4M
-rw-r--r-- 1 flink20 flink20 2.4M Jan 16  2025 mysql-connector-java-8.0.27.jar
flink20@user:~/flink/flink-1.20.3$ 
```

```shell
# 准备好依赖jar
cd ~/flink/flink-1.20.3/cdc-pg-lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-postgres-cdc/3.2.0/flink-sql-connector-postgres-cdc-3.2.0.jar
wget https://repo1.maven.org/maven2/org/apache/flink/flink-cdc-base/3.2.0/flink-cdc-base-3.2.0.jar
wget https://repo1.maven.org/maven2/org/apache/doris/flink-doris-connector-1.20/25.1.0/flink-doris-connector-1.20-25.1.0.jar
```

```shell
# 提交作业命令参数
./bin/flink run \
    -d \
    -Dexecution.checkpointing.interval=10s \
    -Dparallelism.default=1 \
    -c org.apache.doris.flink.tools.cdc.CdcTools \
    -C file:///home/flink20/flink/flink-1.20.3/cdc-lib/flink-cdc-base-3.2.0.jar \
    -C file:///home/flink20/flink/flink-1.20.3/cdc-mysql-lib/flink-cdc-pipeline-connector-mysql-3.2.1.jar \
    ./cdc-lib/flink-doris-connector-1.20-25.1.0.jar \
    mysql-sync-database \
    --database suyh_cdap_doris \
    --mysql-conf hostname=192.168.8.10 \
    --mysql-conf port=3308 \
    --mysql-conf username=repl \
    --mysql-conf password=repl \
    --mysql-conf database-name=suyh_cem \
    --including-tables "scheduling_transfer_record|remittance_recharge_record" \
    --sink-conf fenodes=192.168.8.237:8030 \
    --sink-conf username=root \
    --sink-conf password=doris@2023 \
    --sink-conf jdbc-url=jdbc:mysql://192.168.8.237:9030 \
    --sink-conf sink.label-prefix=label \
    --table-conf replication_num=1 

```



























