-- DDL: "Data Definition Language"
    -- 操作数据库、操作表
-- DML: "Data Manipulation Language"
    -- INSERT、DELETE、UPDATE
-- DCL: "Data Control Language"
    -- GRANT、DENY、REVOKE


-- 创建数据库
CREATE DATABASE suyh DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 删除数据库
DROP DATABASE suyh;

-- 建表
-- mysql
CREATE TABLE person (
    SID integer NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    name varchar(30) COMMENT '姓名', 
    gender varchar(30) COMMENT '性别，男：male, 女：female'
) ENGINE = INNODB;

-- 删除表
    DROP TABLE person;

-- 插入数据
    INSERT INTO conf(note) VALUES ('conf_01');

-- 删除数据
    DELETE FROM TableName WHERE id = 1;
    e.g DELETE FROM conf WHERE id = 1;

-- CASE WHEN THEN
    CASE Subject WHEN '语文' THEN Score ELSE 0 END;

