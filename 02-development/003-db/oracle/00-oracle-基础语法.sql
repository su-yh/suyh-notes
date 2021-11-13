

-- 官方地址
    https://livesql.oracle.com/apex/livesql/file/tutorial_D39T3OXOCOQ3WK9EWZ5JTJA.html

-- 建表
    -- 建一张表，但是格式与另一张表一致
        -- WHERE 条件为真，则表的格式与数据都与源表一样
        -- WHERE 条件为假，则只要源表的格式，不要源表的数据
            CREATE TABLE BRIEF_FND_FLEX_VALUES_VL AS SELECT * FROM DBI_FND_FLEX_VALUES_VL WHERE 1=0;

-- 添加注释
    -- 添加表注释
        COMMENT ON TABLE  DBI_BALANCES  IS 'OF余额导入接口表';
    -- 添加字段注释
        COMMENT ON COLUMN DBI_FND_FLEX_VALUES_VL.BIZ_DATE IS '业务日期';

-- 单引号的转义
    -- 第一个和最后一个单引号用作包含字符，里面的每两个单引号转义出一个正常单引号
    -- 如下示例
        SELECT '''' FROM dual;    -- 输出一个单引号
        SELECT '''''' FROM dual;   -- 输出两个单引号
        SELECT '''''''' FROM dual;  -- 输出三个单引号
    -- 再看下例：
        SELECT 'I''m a pig' FROM dual;  -- 输出I'm a pig

-- 字符串字段类型
    VARCHAR  -- 存放定長的字符数据，最长2000個字符；

    VARCHAR2 -- 存放可变长字符数据，最大长度为4000字符。 





