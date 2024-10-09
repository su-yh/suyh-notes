./bin/flink run-application       -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar
./bin/flink run-application -p 16 -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar
./bin/flink run-application -p 4  -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar

./bin/flink run-application -p 4  -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib;hdfs://hadoop-name-node:8020/flink-logback" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar




这里需要注意一下，只能是原始的jar 包
hadoop fs -put /opt/module/flink-1.18.0/lib/* /flink/flink-dist/flink-lib
hadoop fs -put /opt/module/flink-1.18.0/plugins/* /flink/flink-dist/flink-plugins



# 同期群作业：配置文件目录
export FLINK_CONF_DIR=/opt/module/flink-1.18.0/conf-cohort
# 同期群批模式
./bin/flink run-application -t yarn-application -Dexecution.runtime-mode=BATCH hdfs://hadoop-name-node:8020/flink/flink-projects/flink-cohort-job/job-jar/flink-cohort-job-1.0.0.jar date=20241006
# 同期群流模式
./bin/flink run-application -t yarn-application -p8 -Dexecution.runtime-mode=STREAMING hdfs://hadoop-name-node:8020/flink/flink-projects/flink-cohort-job/job-jar/flink-cohort-job-1.0.0.jar
# 清空环境变量，以防止下次运行命令时，忘记切换配置目录
export FLINK_CONF_DIR=


# 实时曲线作业：配置文件目录
export FLINK_CONF_DIR=/opt/module/flink-1.18.0/conf-realtime
# 实时曲线批模式
./bin/flink run-application -t yarn-application -Dexecution.runtime-mode=BATCH hdfs://hadoop-name-node:8020/flink/flink-projects/flink-realtime-trend/job-jar/realtime-trend-job-2.6.0.jar --realtime.trend.batch.runtime.dates=20241008
# 实时曲线流模式
./bin/flink run-application -t yarn-application -Dexecution.runtime-mode=STREAMING hdfs://hadoop-name-node:8020/flink/flink-projects/flink-realtime-trend/job-jar/realtime-trend-job-2.6.0.jar
# 清空环境变量，以防止下次运行命令时，忘记切换配置目录
export FLINK_CONF_DIR=


# 重复率作业：配置文件目录
export FLINK_CONF_DIR=/opt/module/flink-1.18.0/conf-repetition
# 重复率批模式
./bin/flink run-application -t yarn-application -Dexecution.runtime-mode=BATCH hdfs://hadoop-name-node:8020/flink/flink-projects/cdap-flink-repetition/job-jar/cdap-repetition-job-1.8.0.jar --cdap.batch.runtime.form-date=20241007
# 清空环境变量，以防止下次运行命令时，忘记切换配置目录
export FLINK_CONF_DIR=

echo ${FLINK_CONF_DIR}
