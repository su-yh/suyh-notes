-- 建表
CREATE TABLE person (
    SID integer NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    name varchar(30) COMMENT '姓名', 
    gender varchar(30) COMMENT '性别，男：male, 女：female'
) ENGINE = INNODB;



- ## 新数据库
- > 创建用户密码，并授权
    - > `GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED by 'user_password' WITH GRANT OPTION;`
        > - `FLUSH PRIVILEGES;`  记得要刷新权限
        >> - 这个命令之后就会为这个数据库添加一个`root`用户，密码为: `user_password` 
        >> - 并且这个用户拥有对所有库及表(`*.*`)的权限
        >> - 此用户还可以从任何地址访问(`'root'@'%'`)此数据库服务
    - > `DELETE FROM user WHERE host != '%'`
        > - 通过此命令来将其他用户全部删除





