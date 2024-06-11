# 保存 hadoop classpath 输出
FULL_HADOOP_CLASSPATH=$(hadoop classpath)

# 排除 log4j 和 slf4j 的 JAR 文件
FILTERED_HADOOP_CLASSPATH=""
IFS=':' read -ra PATHS <<< "$FULL_HADOOP_CLASSPATH"
for path in "${PATHS[@]}"; do
    if [[ ! $path == *"log4j"* && ! $path == *"slf4j"* ]]; then
        FILTERED_HADOOP_CLASSPATH="$FILTERED_HADOOP_CLASSPATH:$path"
    fi
done


# 最后设置到环境变量 HADOOP_CLASSPATH 中
HADOOP_CLASSPATH="$FILTERED_HADOOP_CLASSPATH"
