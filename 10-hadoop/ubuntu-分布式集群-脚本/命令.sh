./bin/flink run-application       -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar
./bin/flink run-application -p 16 -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar
./bin/flink run-application -p 4  -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar

./bin/flink run-application -p 4  -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib;hdfs://hadoop-name-node:8020/flink-logback" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar



hadoop fs -mkdir -p /flink-dist/plus
hadoop fs -mkdir -p /flink-usrlib/2nd
hadoop fs -mkdir -p /flink-usrlib/3rd
hadoop fs -mkdir /flink-jobs
hadoop fs -put lib/ /flink-dist
hadoop fs -put plugins/ /flink-dist


export FLINK_CONF_DIR=/opt/module/flink-1.18.0/conf-02; 
./bin/flink run-application -p 4 -t yarn-application -Dyarn.provided.lib.dirs="hdfs://hadoop-name-node:8020/flink-dist;hdfs://hadoop-name-node:8020/flink-usrlib" hdfs://hadoop-name-node:8020/flink-jobs/flink-job-app.jar;
export FLINK_CONF_DIR=


echo ${FLINK_CONF_DIR}
