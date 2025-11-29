USE suyh_cdap_doris;


-- 创建自定义函数
-- 功能：计算日期在对应时区0 点时间戳，单位：秒
-- 参数：日期，时区
CREATE FUNCTION midnight_timestamp(Int, String) RETURNS BigInt PROPERTIES (
    "file"="file:///opt/suyh/doris/suyh-doris-udf-1.0-SNAPSHOT.jar",
    "symbol"="com.suyh.doris.udf.DateMidnightTimestampUdf",
    "always_nullable"="true",
    "type"="JAVA_UDF"
);
-- 删除这个自定义函数
DROP FUNCTION midnight_timestamp(Int, String);

-- 创建自定义函数
-- 功能：计算日期偏移天数后的日期
-- 参数：日期，偏移天数（正负数都可以）
CREATE FUNCTION plus_days(Int, Int) RETURNS Int PROPERTIES (
    "file"="file:///opt/suyh/doris/suyh-doris-udf-1.0-SNAPSHOT.jar",
    "symbol"="com.suyh.doris.udf.DatePlusDaysUdf",
    "always_nullable"="true",
    "type"="JAVA_UDF"
);
-- 删除这个自定义函数
DROP FUNCTION plus_days(Int, Int);

-- 创建自定义函数
-- 功能：计算同期值
-- 参数：行为时间戳，注册时间戳 单位：秒
CREATE FUNCTION generate_cohort(BIGINT, BIGINT) RETURNS Int PROPERTIES (
    "file"="file:///opt/suyh/doris/suyh-doris-udf-1.0-SNAPSHOT.jar",
    "symbol"="com.suyh.doris.udf.GenerateCohortUdf",
    "always_nullable"="true",
    "type"="JAVA_UDF"
);
-- 删除这个自定义函数
DROP FUNCTION generate_cohort(BIGINT, BIGINT);

-- 创建自定义函数
-- 功能：计算时间戳在对应时区的日期
-- 参数：时间戳，时区
CREATE FUNCTION timestamp_date(BIGINT, String) RETURNS Int PROPERTIES (
    "file"="file:///opt/suyh/doris/suyh-doris-udf-1.0-SNAPSHOT.jar",
    "symbol"="com.suyh.doris.udf.DateFromTsUdf",
    "always_nullable"="true",
    "type"="JAVA_UDF"
);
-- 删除这个自定义函数
DROP FUNCTION timestamp_date(BIGINT, String);

-- 创建UDAF 函数
-- 功能：统计计算。得到最小时间戳的那一条记录信息
-- 参数：来源表，主键id, uid, channel, ctime, gaid, pn, day
CREATE AGGREGATE FUNCTION user_register_info(String, BIGINT, String, String, BIGINT, String, String, INT) 
RETURNS STRUCT<source_tb:String, id:BIGINT, uid:String, channel:String, min_ctime:BIGINT, gaid:String, pn:String, day:INT> PROPERTIES (
    "file"="file:///opt/suyh/doris/suyh-doris-udf-1.0-SNAPSHOT.jar",
    "symbol"="com.suyh.doris.udaf.UserRegisterInfoUdaf",
    "always_nullable"="true",
    "type"="JAVA_UDF"
);
DROP FUNCTION user_register_info(String, BIGINT, String, String, BIGINT, String, String, INT);

