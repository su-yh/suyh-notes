


-- ##### 开窗函数OVER()
-- 添加一个单独的自增序号列，但是必须要有分区(PARTITION BY)
ROW_NUMBER OVER(PARTITION BY xx)
    按指定分组后，然后添加一个单独的自增序号列(如下面的 rn 列)
        SELECT ROW_NUMBER() OVER(PARTITION BY name) rn, * FROM person;
    指定列分组且按age 排序排序，然后添加一个单独的自增序号列(如下面的 rn 列)
        SELECT ROW_NUMBER() OVER(PARTITION BY name ORDER BY age) rn, * FROM person;

-- ##########################################################
-- 查看表详细结构
DESC FORMATTED tableName;

-- ###########################################################
-- 建表
    -- 基本语法 1
        -- TEMPORARY 临时表，这个一般不用
        -- EXTERNAL 外部表，这个需要结合后面的 LOCATION 字段使用。与HDFS 上面的某个文件做映射
        CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name    -- (Note: TEMPORARY available in Hive 0.14.0 and later)
        -- (列名 数据类型 注释, ...)
        [(col_name data_type [COMMENT col_comment], ... [constraint_specification])]
        -- 表注释
        [COMMENT table_comment]
        -- 分区: 指定分区名字及类型
        [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]
        [CLUSTERED BY (col_name, col_name, ...) [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
        [SKEWED BY (col_name, col_name, ...)                  -- (Note: Available in Hive 0.10.0 and later)]
            ON ((col_value, col_value, ...), (col_value, col_value, ...), ...)
            [STORED AS DIRECTORIES]
        [
            -- 指定分隔符格式，具体关注后面对 row_format 的详细说明
            [ROW FORMAT row_format] 
            -- 文件格式，具体关注对 file_format 的详细说明
            [STORED AS file_format]
                | STORED BY 'storage.handler.class.name' [WITH SERDEPROPERTIES (...)]  -- (Note: Available in Hive 0.6.0 and later)
        ]
        -- HDFS 上面的文件路径，这个应该是一个目录，并不能指定到具体文件
        [LOCATION hdfs_path]
        [TBLPROPERTIES (property_name=property_value, ...)]   -- (Note: Available in Hive 0.6.0 and later)
        [AS select_statement];   -- (Note: Available in Hive 0.5.0 and later; not supported for external tables)
        
    -- 基本语法 2
        CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name
        LIKE existing_table_or_view_name
        [LOCATION hdfs_path];

    -- 详细说明: 
        data_type
        : primitive_type
        | array_type
        | map_type
        | struct_type
        | union_type  -- (Note: Available in Hive 0.7.0 and later)
        
        primitive_type
        : TINYINT
        | SMALLINT
        | INT
        | BIGINT
        | BOOLEAN
        | FLOAT
        | DOUBLE
        | DOUBLE PRECISION -- (Note: Available in Hive 2.2.0 and later)
        | STRING
        | BINARY      -- (Note: Available in Hive 0.8.0 and later)
        | TIMESTAMP   -- (Note: Available in Hive 0.8.0 and later)
        | DECIMAL     -- (Note: Available in Hive 0.11.0 and later)
        | DECIMAL(precision, scale)  -- (Note: Available in Hive 0.13.0 and later)
        | DATE        -- (Note: Available in Hive 0.12.0 and later)
        | VARCHAR     -- (Note: Available in Hive 0.12.0 and later)
        | CHAR        -- (Note: Available in Hive 0.13.0 and later)
        
        array_type
        : ARRAY < data_type >
        
        map_type
        : MAP < primitive_type, data_type >
        
        struct_type
        : STRUCT < col_name : data_type [COMMENT col_comment], ...>
        
        union_type
        : UNIONTYPE < data_type, data_type, ... >  -- (Note: Available in Hive 0.7.0 and later)
        
        row_format
        -- 指定读取文件的格式和规则。分隔符的指定
        : DELIMITED 
                -- 字段之间的分隔符
                [FIELDS TERMINATED BY char [ESCAPED BY char]] 
                -- 集合之间的分隔符
                [COLLECTION ITEMS TERMINATED BY char]
                -- map 类型之间的分隔符
                [MAP KEYS TERMINATED BY char] [LINES TERMINATED BY char]
                [NULL DEFINED AS char]   -- (Note: Available in Hive 0.13 and later)
        -- Hive 的序列化与反序列化(Serializer and Deserializer)
        | SERDE serde_name [WITH SERDEPROPERTIES (property_name=property_value, property_name=property_value, ...)]

        file_format:
        : SEQUENCEFILE
        | TEXTFILE    -- (Default, depending on hive.default.fileformat configuration)
        | RCFILE      -- (Note: Available in Hive 0.6.0 and later)
        | ORC         -- (Note: Available in Hive 0.11.0 and later)
        | PARQUET     -- (Note: Available in Hive 0.13.0 and later)
        | AVRO        -- (Note: Available in Hive 0.14.0 and later)
        | JSONFILE    -- (Note: Available in Hive 4.0.0 and later)
        | INPUTFORMAT input_format_classname OUTPUTFORMAT output_format_classname
        
        constraint_specification:
        : [, PRIMARY KEY (col_name, ...) DISABLE NOVALIDATE ]
            [, CONSTRAINT constraint_name FOREIGN KEY (col_name, ...) REFERENCES table_name(col_name, ...) DISABLE NOVALIDATE 





    -- 详细建表示例。
        CREATE TABLE psn(
            id INT,
            name STRING,
            likes ARRAY<STRING>,
            address map<STRING, STRING>
        ) ROW FORMAT DELIMITED
            -- 字段之间分隔符
            FIELDS TERMINATED BY ','
            -- 集合元素之间分隔符
            COLLECTION ITEMS TERMINATED BY '-'
            -- map k-v 之间分隔符
            MAP KEYS TERMINATED BY ':'
        -- 存储文件格式
        STORED AS TEXTFILE;

    -- 创建一张新表，数据和内容都与psn8 相同
        CREATE TABLE psn8 AS SELECT * FROM psn3;
    -- 创建一张新表(空表)，与psn3 表的结构相同
        CREATE TABLE psn9 LIKE psn3;


    --  建表示例
        -- Hive 正则匹配
        CREATE TABLE logtbl (
            host STRING,
            identity STRING,
            t_user STRING,
            time STRING,
            request STRING,
            referer STRING,
            agent STRING)
        ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
        WITH SERDEPROPERTIES (
            "input.regex" = "([^ ]*) ([^ ]*) ([^ ]*) \\[(.*)\\] \"(.*)\" (-|[0-9]*) (-|[0-9]*)"
        )
        STORED AS TEXTFILE;

        -- 下面是数据文件，与上面的建表语句进行匹配
            -- 下面的正则匹配：([^ ]*) ([^ ]*) ([^ ]*) \\[(.*)\\] \"(.*)\" (-|[0-9]*) (-|[0-9]*)
            192.168.57.4 - - [29/Feb/2016:18:14:35 +0800] "GET /bg-upper.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:35 +0800] "GET /bg-nav.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:35 +0800] "GET /asf-logo.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:35 +0800] "GET /bg-button.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:35 +0800] "GET /bg-middle.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET / HTTP/1.1" 200 11217
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET / HTTP/1.1" 200 11217
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.css HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /asf-logo.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-middle.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-button.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-nav.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-upper.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET / HTTP/1.1" 200 11217
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.css HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET / HTTP/1.1" 200 11217
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.css HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /tomcat.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-button.png HTTP/1.1" 304 -
            192.168.57.4 - - [29/Feb/2016:18:14:36 +0800] "GET /bg-upper.png HTTP/1.1" 304 -

    -- 建表，动态分区
    -- 注意: 动态分区对应的一定是离线数据。实时数据无法做动态分区
    -- 开启动态分区需要几个参数，详见笔记"hive基础.md"
        CREATE TABLE psn22(
            id INT,
            name STRING,
            likes ARRAY<STRING>,
            address MAP<STRING, STRING>
        )
        PARTITIONED BY (age INT, gender STRING)
        ROW FORMAT DELIMITED
        FIELDS TERMINATED BY ','
        COLLECTION ITEMS TERMINATED BY '-'
        MAP KEYS TERMINATED BY ':';



-- ########################################################

-- 下面的这个是对某个分区中没有这个目录的时候使用。
-- 它只是创建了这个目录，在这个目录下面是还没有任何数据的。
-- 这个数据会在后期添加进来
ALTER TABLE psn6 ADD PARTITION(age=30, sex='boy');
-- 删除分区也是一样的，只是将分区对应的值的目录删除了
-- 删除分区时，可以只指定一个分区
ALTER TABLE psn6 DROP PARTITION(sex='boy');
-- 如果同时创建目录也有数据的情况下，都是使用 LOAD .... 的方式
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)]


-- #####################################
-- hive 中不支持  delete SQL 语法，如果想要删除表中的数据可以使用 TRANCATE
    TRANCATE TABLE tableName;





-- ########################
-- 加载数据从一个路径下(比较关键，需要掌握)
    -- 基本语法
    LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)]
    
    LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)] [INPUTFORMAT 'inputformat' SERDE 'serde'] (3.0 or later)

    -- 问题：
        -- 我使用这种方式从HDFS 里面加载数据到表中(内部表)，但是回头再看时，HDFS 中那个文件没有了？
        -- 难道这种方式会将HDFS 中的文件给删除掉吗？







-- ########################
-- 从查询结果插入数据到表中. (比较关键，需要掌握)
Inserting data into Hive Tables from queries
-- 官方语法: 
    Standard syntax:
    -- 基本插入语法
    -- 这种语法跟普通SQL 一样
    INSERT OVERWRITE TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...) [IF NOT EXISTS]] select_statement1 FROM from_statement;
    INSERT INTO TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...)] select_statement1 FROM from_statement;
    
    -- 扩展插入语法(用得相当多)
    -- 这种语法主是将FROM TABLE 放到前面，但是可以跟多条INSERT 语句。这样可以做一次查询然后插入到多张表中
    Hive extension (multiple inserts):

    FROM from_statement
    -- 这里的 [IF NOT EXISTS] 的意思应该是如果分区不存在则创建此分区
    INSERT OVERWRITE TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...) [IF NOT EXISTS]] select_statement1
    [INSERT OVERWRITE TABLE tablename2 [PARTITION ... [IF NOT EXISTS]] select_statement2]
    [INSERT INTO TABLE tablename2 [PARTITION ...] select_statement2] 
    ...;

    FROM from_statement
    -- 后面的... 是指可以跟很多的 INSERT INTO TABLE 语句，连接到一起
    INSERT INTO TABLE tablename1 [PARTITION (partcol1=val1, partcol2=val2 ...)] select_statement1
    [INSERT INTO TABLE tablename2 [PARTITION ...] select_statement2]
    [INSERT OVERWRITE TABLE tablename2 [PARTITION ... [IF NOT EXISTS]] select_statement2] 
    ...;

    -- Hive 动态分区
    -- 动态分区有一些参数需要指定
    -- 以插入的方式进行动态分区的话。指定的数据的最后一列将被作为分区列(这个地方必须是在最后的位置)。
    -- 插入的表必须在创建时指定了分区。
    Hive extension (dynamic partition inserts):
    INSERT OVERWRITE TABLE tablename PARTITION (partcol1[=val1], partcol2[=val2] ...) select_statement FROM from_statement;
    INSERT INTO TABLE tablename PARTITION (partcol1[=val1], partcol2[=val2] ...) select_statement FROM from_statement;

    -- 官方示例: 
    -- 从表 page_view_stg 表中查询数据。
    FROM page_view_stg pvs
    -- 将查询结果中的指定字段插入到 page_view 表中，
    -- OVERWRITE: 是指覆盖原来数据
    INSERT OVERWRITE TABLE page_view PARTITION(dt='2008-06-08', country)
        SELECT pvs.viewTime, pvs.userid, pvs.page_url, pvs.referrer_url, null, null, pvs.ip, pvs.cnt
    -- 后面还可以继续跟 INSERT 语句，都是从page_view_stg 表中查询结果中取数据

    -- 用途: 只读取表一次分别将不同的字段写到不同的表中。

    -- 动态分区，示例：
        FROM psn21
        INSERT INTO psn22 PARTITION(age, gender)
            SELECT id, name, likes, address, age, gender
    -- 前提是建表的时候需要先指定分区，如下
        CREATE TABLE psn22(
            id INT,
            name STRING,
            likes ARRAY<STRING>,
            address MAP<STRING, STRING>
        )
        PARTITIONED BY (age INT, gender STRING)
        ROW FORMAT DELIMITED
        FIELDS TERMINATED BY ','
        COLLECTION ITEMS TERMINATED BY '-'
        MAP KEYS TERMINATED BY ':';

-- #########################
-- 写数据到文件系统，从一个查询中(不推荐，选择忘记它就可以)
Writing data into the filesystem from queries
    -- 标准语法
    Standard syntax:

    -- 一定要注意：如果这个选择了[LOCAL] 那么后面的目录一定要慎重，它会将这个目录完全覆盖掉
    INSERT OVERWRITE [LOCAL] DIRECTORY directory1
    [ROW FORMAT row_format] [STORED AS file_format] (Note: Only available starting with Hive 0.11.0)
    SELECT ... FROM ...

    Hive extension (multiple inserts):
    FROM from_statement
    INSERT OVERWRITE [LOCAL] DIRECTORY directory1 select_statement1
    [INSERT OVERWRITE [LOCAL] DIRECTORY directory2 select_statement2] ...
    
    
    row_format
    : DELIMITED [FIELDS TERMINATED BY char [ESCAPED BY char]] [COLLECTION ITEMS TERMINATED BY char]
            [MAP KEYS TERMINATED BY char] [LINES TERMINATED BY char]
            [NULL DEFINED AS char] (Note: Only available starting with Hive 0.13)

-- ############################
-- 像关键型数据库一样插入数据(不推荐，选择忘记它就可以)
Inserting values into tables from SQL
    -- 基本语法: 
    Standard Syntax:
    INSERT INTO TABLE tablename [PARTITION (partcol1[=val1], partcol2[=val2] ...)] VALUES values_row [, values_row ...]
    
    Where values_row is:
    ( value [, value ...] )
    where a value is either null or any valid SQL literal

-- ###################
-- hive 支持update 但是有很多限制，且有很多配置。一般没人用它





-- #####################
-- Hive SerDe    
-- Hive 的序列化和反序列化: Serializer and Deserializer












