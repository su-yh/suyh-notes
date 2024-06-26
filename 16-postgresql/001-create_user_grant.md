

**创建用户并授权**



```SQL
-- 这里都是SQL 语句，需要先进入到PGSQL 命令行

-- 如果没有初始化数据库集簇，则需要先进行初始化
-- initdb -D$PGDATA -W

-- 使用linux 用户 postgres 进入到用户终端界面
su - postgres

-- 首先使用超级用户登录到pgsql 数据库界面
psql -U postgres -d postgres


-- 创建用户 suyh 密码：suyunhong ，同时允许其创建数据库
CREATE USER suyh CREATEDB PASSWORD 'suyunhong';
-- 创建用户数据库，如：suyh_db，同时指定属主为用户 suyh
CREATE DATABASE suyh_db OWNER suyh;

-- 创建成功后就可以使用该用户登录并连接到该数据库
-- -U 登录用户名
-- -d 连接的数据库
-- -W 输入密码
-- -h 指定主机
-- -p 指定端口
psql -U suyh -d suyh_db -W -p 5432 -h localhost
```







