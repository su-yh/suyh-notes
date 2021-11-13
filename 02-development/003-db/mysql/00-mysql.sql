




-- 建表
CREATE TABLE person (
    SID integer NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    name varchar(30) COMMENT '姓名', 
    gender varchar(30) COMMENT '性别，男：male, 女：female'
) ENGINE = INNODB charset=utf8;





