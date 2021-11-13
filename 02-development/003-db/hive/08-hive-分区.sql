

-- 查看分区
    SHOW PARTITIONS TableName;

-- 动态分区
    -- 动态分区，需要设置相关参数
        hive.exec.dynamici.partition=true;  -- 开启动态分区，默认是false
        set hive.exec.dynamic.partition.mode=nonstrict; -- 开启允许所有分区都是动态的，否则必须要有静态分区才能使用
    -- 已建好表结构，从别的表查询。查询的列比插入的列多，则多出来的列则可以处理成新分区
        -- 这里 dpartition 表中只有两列，而查询的有三列，所以最后一列会被处理成分区.
        insert overwrite table dpartition
        partition(ct)
        select id ,name,city from  mytest_tmp2_p;
    -- 已建表结构，是否可以将某个字段再处理成分区?



