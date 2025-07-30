#!/bin/bash

# MySQL连接参数（请替换为实际信息）
MYSQL_USER="root"
MYSQL_PASSWORD="aiteer"
MYSQL_HOST="localhost"
MYSQL_PORT="3306"

# 导出文件存放目录（请修改为实际路径）
EXPORT_DIR="/home/suyunhong/mysql-dump/dbs"

# 创建导出目录（如果不存在）
# mkdir -p "$EXPORT_DIR"

# 忽略的系统数据库（不需要导出的数据库）
IGNORED_DBS="information_schema performance_schema mysql sys"

# 获取所有数据库列表
# DATABASES=$(mysql -h localhost -P 3306 -u root -p"aiteer" -e "SHOW DATABASES;" | grep -vE "($(echo "information_schema performance_schema mysql sys" | tr ' ' '|'))")
DATABASES=$(mysql -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SHOW DATABASES;" | sed 1d | grep -vE "($(echo $IGNORED_DBS | tr ' ' '|'))")

# 遍历每个数据库并导出
for DB in $DATABASES; do
    echo "导出数据库: $DB"
    # 导出文件名格式：数据库名_日期.sql
    FILENAME="$EXPORT_DIR/${DB}_$(date +%Y%m%d).sql"
    # 执行导出（包含存储过程、事件等）
    mysqldump -h "$MYSQL_HOST" -P "$MYSQL_PORT" -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" --databases "$DB" --routines --events --single-transaction > "$FILENAME"
    
    # 可选：压缩SQL文件
    # gzip "$FILENAME"
    
    # 检查导出是否成功
    if [ $? -eq 0 ]; then
        echo "数据库 $DB 导出成功，文件: $FILENAME"
    else
        echo "数据库 $DB 导出失败！"
    fi
done

echo "所有数据库导出完成！"


