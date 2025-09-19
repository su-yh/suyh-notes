show tables;
select * from stu;
INSERT INTO stu values(1, "ss");

SHOW DATABASES;

CREATE DATABASE db_hive1;

USE db_hive1;

SHOW TABLES;

create table stu(id int, name string);

SHOW CREATE TABLE stu;

-- 使用本地模式，不用提交到yarn
set mapreduce.framework.name = local;
set mapreduce.framework.name;


