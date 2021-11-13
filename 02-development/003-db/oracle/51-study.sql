
-- 格式化指令
    -- 设置每行显示数据的长度
        SQL> SET LINESIZE 300;

    -- 设置每页显示的数据行数
        SQL> SET PAGESIZE 30;

-- 启动本地的记事本
    -- 打开或者创建一个本地文件
        SQL> ed mldn
    -- 要想执行文件中的命令，则需要使用"@文件名称"(默认找到*.sql)
        SQL> @mldn

-- 切换用户
    -- 如果现在使用的是sys用户登录，那么必须要写上"AS SYSDBA"，否则无法登录
        SQL> CONN 用户名/密码 [AS SYSDBA]
    -- 使用system 登录
        SQL> CONN system/manager
    -- 使用sys登录
        SQL> CONN sys/change_on_install
        -- 提示错误
        -- ORA-28009: connection as SYS should be as SYSDBA or SYSOPER
        -- 警告: 您不再连接到 ORACLE。

        -- 这时必须要在后面跟上 AS SYSDBA才可以
        SQL> CONN sys/change_on_install AS SYSDBA

-- 调用本机命令
    -- 使用HOST  前缀
        SQL> HOST echo helloworld

-- 模式名称
    -- 现在模式名称都使用了用户名了
    SQL> SELECT * FROM scott.emp;

-- 查询一个用户下的所有数据表
    SQL> SELECT * FROM tab;

-- 指定列显示的长度
    -- 指定ename 列的显示长度为10列
    SQL> COL ename FOR A10;
    SQL> COL job FOR A10;

-- DML(数据库操作语言): 数据库的查询与更新操作
-- DLL(数据定义语言): 主要是数据对象的创建(表、用户)
-- DCL(数据控制语言): 主要是进行权限的管理操作






















