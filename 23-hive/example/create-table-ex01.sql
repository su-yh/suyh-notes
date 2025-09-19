
SHOW DATABASES;
SHOW TABLES;

-- 内部表   删除时，hdfs 上的文件将会被直接删除
CREATE TABLE IF NOT EXISTS student(
    id int,
    name string
)
-- 字段之间的分隔符使用 \t
row format delimited fields terminated by '\t'
location '/user/hive/warehouse/student';

SELECT * FROM student;

DROP TABLE student;

-- 外部表  删除时，hdfs 上的文件将会保留，而不会被删除
-- 唯一的区别就是添加了 external 关键字
CREATE external TABLE IF NOT EXISTS student(
    id int,
    name string
)
-- 字段之间的分隔符使用 \t
row format delimited fields terminated by '\t'
location '/user/hive/warehouse/student';




