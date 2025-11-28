
-- 1. 日期参数为活跃日期
-- 2. 查询的日期范围为相邻两日，包含活跃日期当天，以及其后一天
-- 3. cohort 是一个24 小时范围，那么它的开始时间戳必须要落在活跃日期当天的24 小时内。
--     也就是说cohort 的开始时间戳不在活跃日期当天的24 小时内的这部分数据要过滤丢弃掉
-- 
-- 需要
--     注册时间戳、注册日期、注册gaid、首充时间戳、首充日期、
--     归因属性
--     计算出cohort、chort 开始时间戳
--     统计  活跃人数、充值人数、充值金额、提现人数、提现金额



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


--  TODO: suyh - 可以按需修改目标日期
SET @target_dates = 20220108;
SET @next_dates = plus_days(@target_dates, 1);

SELECT @target_dates, @next_dates;

-- 一个日期，在对应项目时区的0 点和24 点时间戳，[start, end) 为一个完整天
DROP TABLE IF EXISTS tmp_behavior_dates;
CREATE TABLE tmp_behavior_dates
(
  dates INT,
  ts_begin BIGINT,
  ts_end BIGINT, 
  pn VARCHAR(10)
);

INSERT INTO tmp_behavior_dates(pn, dates, ts_begin, ts_end)
-- UDF: 自定义函数（midnight_timestamp），计算日期在指定时区 0 点时的时间戳
SELECT pn, @target_dates AS dates, 
  midnight_timestamp(@target_dates, zone_id) AS ts_begin, midnight_timestamp(@next_dates, zone_id) AS ts_end
FROM project;

-- SELECT * FROM tmp_behavior_dates ORDER BY ts_begin;

-- -- 暂时忽略时区的影响
-- SELECT 'register' AS category, `day`, id, uid, channel, gaid AS reg_gaid, pn, ctime AS ts, ctime AS reg_ts FROM tb_user WHERE `day` IN (@target_dates, @next_dates)
-- limit 10;
-- 
-- -- 按uid, channel, day 去重，同时有登录与注册时保留注册中的数据
-- SELECT 'login' AS category,    `day`, id, uid, channel, gaid AS reg_gaid, pn, ctime AS ts, ctime AS reg_ts FROM tb_user WHERE `day` IN (@target_dates, @next_dates)
-- UNION ALL
-- SELECT 'login' AS category,    `day`, id, uid, channel, NULL AS reg_gaid, pn, ctime AS ts, NULL  AS reg_ts FROM tb_user_login l WHERE `day` IN (@target_dates, @next_dates)
--   AND NOT EXISTS (SELECT 1 FROM tb_user u WHERE `day` IN (@target_dates, @next_dates) AND u.uid = l.uid AND u.channel = l.channel AND u.`day` = l.`day`);
-- 
-- -- 验证上面的 NOT EXISTS
-- SELECT l.id AS login_id, u.id AS register_id 
-- FROM tb_user_login l 
-- INNER JOIN tb_user u ON u.uid = l.uid AND u.channel = l.channel AND u.`day` = l.`day`
-- WHERE l.`day` IN (@target_dates, @next_dates);
-- SELECT 'recharge'   AS category, `day`, id, uid, channel, NULL AS reg_gaid, pn, mtime AS ts, NULL AS reg_ts FROM tb_recharge   WHERE `day` IN (@target_dates, @next_dates);
-- SELECT 'withdrawal' AS category, `day`, id, uid, channel, NULL AS reg_gaid, pn, mtime AS ts, NULL AS reg_ts FROM tb_withdrawal WHERE `day` IN (@target_dates, @next_dates);

-- 在其他三张行为表中的记录，却在tb_user 表中找不到注册信息时，补充其注册信息记录。
-- DROP TABLE IF EXISTS tb_user_other;
CREATE TABLE IF NOT EXISTS tb_user_other 
(
  source_tb VARCHAR(16) NOT NULL COMMENT '来源表名',
  source_id BIGINT NOT NULL,
  uid VARCHAR(100),
  channel VARCHAR(100),
  ctime BIGINT,
  gaid VARCHAR(255),
  pn VARCHAR(20),
  `day` INT
);

-- 每次都需要创建的临时表
-- 这张表来存放，不在注册表（tb_user）不在扩展注册表（tb_user_other）中的用户数据，后续将要查询这些用户的注册信息
DROP TABLE IF EXISTS tmp_user_other;
CREATE TABLE tmp_user_other 
(
  uid VARCHAR(100),
  channel VARCHAR(100),
  ctime BIGINT 
);

-- 来源表：登录表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM tb_user_login l WHERE `day` IN (@target_dates, @next_dates)
  AND NOT EXISTS (  -- 在注册表中不存在记录
    SELECT 1 FROM tb_user u WHERE u.uid = l.uid AND u.channel = l.channel
  ) AND NOT EXISTS (  -- 在扩展注册表中不存在记录
    SELECT 1 FROM tb_user_other u WHERE u.uid = l.uid AND u.channel = l.channel
  );
-- 来源表：充值表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM tb_recharge l WHERE `day` IN (@target_dates, @next_dates)
  AND NOT EXISTS (  -- 在注册表中不存在记录
    SELECT 1 FROM tb_user u WHERE u.uid = l.uid AND u.channel = l.channel
  ) AND NOT EXISTS (  -- 在扩展注册表中不存在记录
    SELECT 1 FROM tb_user_other u WHERE u.uid = l.uid AND u.channel = l.channel
  );
-- 来源表：提现表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM tb_withdrawal l WHERE `day` IN (@target_dates, @next_dates)
  AND NOT EXISTS (  -- 在注册表中不存在记录
    SELECT 1 FROM tb_user u WHERE u.uid = l.uid AND u.channel = l.channel
  ) AND NOT EXISTS (  -- 在扩展注册表中存在记录
    SELECT 1 FROM tb_user_other u WHERE u.uid = l.uid AND u.channel = l.channel
  );

-- SELECT COUNT(1) FROM tmp_user_other;

-- 找到这些不在注册表中的记录的注册信息并永久存储到表 tb_user_other 中
INSERT INTO tb_user_other(source_tb,  source_id, uid, channel, ctime, gaid, pn, `day`)
SELECT 
  struct_element(reg_info, 'source_tb')     AS source_tb,
  struct_element(reg_info, 'id')            AS source_id,
  struct_element(reg_info, 'uid')           AS uid,
  struct_element(reg_info, 'channel')       AS channel,
  struct_element(reg_info, 'min_ctime')     AS ctime,
  struct_element(reg_info, 'gaid')          AS gaid,
  struct_element(reg_info, 'pn')            AS pn,
  struct_element(reg_info, 'day')           AS `day`
FROM (
  SELECT uid, channel, user_register_info(source_tb, id, uid, channel, ctime, gaid, pn, `day`) AS reg_info
  FROM (
    SELECT 'tb_user_login' AS source_tb, ul.id, ul.uid, ul.channel, ul.ctime AS ctime, ul.gaid AS gaid, ul.pn, ul.`day`
    FROM tmp_user_other tuo 
    LEFT JOIN tb_user_login ul ON tuo.uid = ul.uid AND tuo.channel = ul.channel AND tuo.ctime >= ul.ctime 
    UNION ALL 
    SELECT 'tb_recharge' AS source_tb, rcg.id, rcg.uid, rcg.channel, rcg.ctime AS ctime, rcg.gaid AS gaid, rcg.pn, rcg.`day`
    FROM tmp_user_other tuo 
    LEFT JOIN tb_recharge rcg ON tuo.uid = rcg.uid AND tuo.channel = rcg.channel AND tuo.ctime >= rcg.ctime 
    UNION ALL 
    SELECT 'tb_withdrawal' AS source_tb, wtd.id, wtd.uid, wtd.channel, wtd.ctime AS ctime, wtd.gaid AS gaid, wtd.pn, wtd.`day`
    FROM tmp_user_other tuo 
    LEFT JOIN tb_withdrawal wtd ON tuo.uid = wtd.uid AND tuo.channel = wtd.channel AND tuo.ctime >= wtd.ctime 
  ) t 
  GROUP BY uid, channel 
) ft;

-- suyh - 到此，所有的用户注册信息都全部找到，要么在tb_user 中，要么在 tb_user_other 中。

-- 添加更完整的同期群数据信息
-- 基于登录表
DROP TABLE IF EXISTS tmp_behavior_cohort_login;
CREATE TABLE tmp_behavior_cohort_login
(
  category VARCHAR(12),
  source_id BIGINT,
  uid VARCHAR(50),
  channel VARCHAR(50),
  pn VARCHAR(10),
  cohort INT,
  behavior_ts BIGINT, -- 行为时间戳
  bdates INT, -- 行为日期：当前这条数据实际来源于哪一天
  reg_dates INT, -- 注册日期
  reg_gaid VARCHAR(50),
  reg_ts BIGINT   -- 注册时间戳
);

-- SELECT COUNT(1) FROM tb_user_login WHERE `day` IN (@target_dates, @next_dates);
-- 登录表在当天的相关数据
INSERT INTO tmp_behavior_cohort_login(category, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts)
SELECT 'login' AS category, ft.id AS source_id, uid, channel, ft.pn, 
  cohort, behavior_ts, 
  timestamp_date(behavior_ts, p.zone_id) AS bdates, 
  timestamp_date(reg_ts, p.zone_id) AS reg_dates, reg_gaid, reg_ts
FROM (
  SELECT ul.id, ul.uid, ul.channel, ul.ctime AS behavior_ts, 
    tu.ctime AS reg_ts, tu.gaid AS reg_gaid, tu.pn, 
    generate_cohort(ul.ctime, tu.ctime) AS cohort
  FROM tb_user_login ul 
  INNER JOIN tb_user tu ON ul.uid = tu.uid AND ul.channel = tu.channel 
  WHERE ul.`day` IN (@target_dates, @next_dates) AND ul.`day` != tu.`day`
  UNION ALL 
  SELECT ul.id, ul.uid, ul.channel, ul.ctime AS behavior_ts, 
    tu.ctime AS reg_ts, tu.gaid AS reg_gaid, tu.pn, 
    generate_cohort(ul.ctime, tu.ctime) AS cohort
  FROM tb_user_login ul 
  INNER JOIN tb_user_other tu ON ul.uid = tu.uid AND ul.channel = tu.channel 
  WHERE ul.`day` IN (@target_dates, @next_dates)
) ft
INNER JOIN project p ON ft.pn = p.pn; 
-- 注册数据也需要按登录数据一样的处理
INSERT INTO tmp_behavior_cohort_login(category, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts)
SELECT 'register' AS category, ft.id AS source_id, uid, channel, ft.pn, 
  cohort, behavior_ts, 
  timestamp_date(behavior_ts, p.zone_id) AS bdates, 
  timestamp_date(reg_ts, p.zone_id) AS reg_dates, reg_gaid, reg_ts
FROM (
  SELECT id, uid, channel, ctime AS behavior_ts, 
    ctime AS reg_ts, gaid AS reg_gaid, pn, 
    0 AS cohort
  FROM tb_user 
  WHERE `day` IN (@target_dates, @next_dates)
) ft
INNER JOIN project p ON ft.pn = p.pn;













-- -- 第一个临时表，只有最简单的行为数据
-- DROP TABLE IF EXISTS tmp_simple_behavior;
-- CREATE TABLE tmp_simple_behavior
-- (
--   category VARCHAR(12),
--   source_id BIGINT,
--   uid VARCHAR(50),
--   channel VARCHAR(50),
--   pn VARCHAR(10),
--   behavior_ts BIGINT, -- 行为时间戳
--   behavior_order VARCHAR(64), -- 行为订单号
--   behavior_amount DECIMAL(16, 2) -- 行为订单金额
-- );
-- 
-- -- 注册表中的记录就不需要了
-- -- 登录表中的数据，但同时不是当天注册的记录
-- INSERT INTO tmp_simple_behavior(category,  source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount)
-- SELECT 'login' AS category,    id AS source_id, uid, channel, IF(pn IS NULL OR pn = '', 'hy', pn), ctime AS behavior_ts, NULL AS behavior_order, NULL AS behavior_amount FROM tb_user_login l WHERE `day` IN (@target_dates, @next_dates)
--   AND NOT EXISTS (SELECT 1 FROM tb_user u WHERE `day` IN (@target_dates, @next_dates) AND u.uid = l.uid AND u.channel = l.channel AND u.`day` = l.`day`);
-- -- 充值记录
-- INSERT INTO tmp_simple_behavior(category,  source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount)
-- SELECT 'recharge'   AS category, id AS source_id, uid, channel, IF(pn IS NULL OR pn = '', 'hy', pn), mtime AS behavior_ts, `order` AS behavior_order, goods_amt AS behavior_amount FROM tb_recharge   WHERE `day` IN (@target_dates, @next_dates);
-- -- 提现记录
-- INSERT INTO tmp_simple_behavior(category,  source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount)
-- SELECT 'withdrawal' AS category, id AS source_id, uid, channel, IF(pn IS NULL OR pn = '', 'hy', pn), mtime AS behavior_ts, `order` AS behavior_order, amount AS behavior_amount FROM tb_withdrawal WHERE `day` IN (@target_dates, @next_dates);
-- 
-- -- SELECT * FROM tmp_simple_behavior LIMIT 10;
-- 
-- 
-- -- 第二个临时表，补充了注册信息的行为数据
-- DROP TABLE IF EXISTS tmp_behavior_reg_info;
-- CREATE TABLE tmp_behavior_reg_info
-- (
--   category VARCHAR(12),
--   source_id BIGINT,
--   uid VARCHAR(50),
--   channel VARCHAR(50),
--   pn VARCHAR(10),
--   behavior_ts BIGINT, -- 行为时间戳
--   behavior_order VARCHAR(64), -- 行为订单号
--   behavior_amount DECIMAL(16, 2), -- 行为订单金额
--   reg_gaid VARCHAR(50),
--   reg_ts BIGINT   -- 注册时间戳
-- );
-- 
-- -- 注册表中的数据直接自带注册信息
-- INSERT INTO tmp_behavior_reg_info(category, source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount, reg_gaid, reg_ts)
-- SELECT 'register' AS category, id AS source_id, uid, channel, IF(pn IS NULL OR pn = '', 'hy', pn), ctime AS behavior_ts, NULL, NULL, gaid AS reg_gaid, ctime AS reg_ts FROM tb_user WHERE `day` IN (@target_dates, @next_dates);
-- 
-- -- 按uid, channel, day 去重，同时有登录与注册时保留注册中的数据
-- -- 注册表中的数据，需要作为登录数据一样的处理。
-- -- 同时不是当天注册的记录已经存放在tmp_simple_behavior 表中了
-- INSERT INTO tmp_behavior_reg_info(category, source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount, reg_gaid, reg_ts)
-- SELECT 'login' AS category, id AS source_id, uid, channel, IF(pn IS NULL OR pn = '', 'hy', pn), ctime AS behavior_ts, NULL, NULL, gaid AS reg_gaid, ctime AS reg_ts FROM tb_user WHERE `day` IN (@target_dates, @next_dates);
-- 
-- 
-- -- 将tmp_simple_behavior 表中的数据补充上注册信息然后插入到tmp_behavior_reg_info 表中
-- -- 1. 在注册表中存在的记录
-- INSERT INTO tmp_behavior_reg_info(category, source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount, reg_gaid, reg_ts)
-- SELECT t1.category,  t1.source_id, t1.uid, t1.channel, t1.pn, t1.behavior_ts, t1.behavior_order, t1.behavior_amount, 
--     u.gaid AS reg_gaid, u.ctime AS reg_ts
-- FROM tmp_simple_behavior t1 
-- INNER JOIN tb_user u ON t1.uid = u.uid AND t1.channel = u.channel AND t1.behavior_ts >= u.ctime;
-- 
-- 
-- -- tmp_simple_behavior 中没有找到注册信息的记录，将重新缓存在里面
-- DROP TABLE IF EXISTS tmp_behavior_lost_reg_info;
-- CREATE TABLE tmp_behavior_lost_reg_info
-- (
--   category VARCHAR(12),
--   source_id BIGINT,
--   uid VARCHAR(50),
--   channel VARCHAR(50),
--   pn VARCHAR(10),
--   behavior_ts BIGINT, -- 行为时间戳
--   behavior_order VARCHAR(64), -- 行为订单号
--   behavior_amount DECIMAL(16, 2) -- 行为订单金额
-- );
-- 
-- -- tmp_simple_behavior 中剩下的部分记录，要从三张行为表中找出注册时间戳
-- INSERT INTO tmp_behavior_lost_reg_info(category,  source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount)
-- SELECT t1.category,  t1.source_id, t1.uid, t1.channel, t1.pn, t1.behavior_ts, t1.behavior_order, t1.behavior_amount
--   FROM tmp_simple_behavior t1 
--   WHERE NOT EXISTS (SELECT 1 FROM tb_user u WHERE t1.uid = u.uid AND t1.channel = u.channel AND t1.behavior_ts >= u.ctime);
-- 
-- -- 将剩下的这些也插入到tmp_behavior_reg_info 中，已经补充上了注册信息
-- INSERT INTO tmp_behavior_reg_info(category, source_id, uid, channel, pn, behavior_ts, behavior_order, behavior_amount, reg_ts, reg_gaid)
-- SELECT t3.category, t3.source_id, t3.uid, t3.channel, t3.pn, t3.behavior_ts, t3.behavior_order, t3.behavior_amount, 
--   t3_reg.min_ts AS reg_ts, t3_reg.gaid AS reg_gaid
-- FROM tmp_behavior_lost_reg_info t3 
-- LEFT JOIN (
--   SELECT uid, channel, MIN(mts) AS min_ts, MAX(gaid) AS gaid
--   FROM (
--     SELECT t3.uid, t3.channel, ul.ctime AS mts, ul.gaid AS gaid
--     FROM tmp_behavior_lost_reg_info t3 
--     LEFT JOIN tb_user_login ul ON t3.uid = ul.uid AND t3.channel = ul.channel AND t3.behavior_ts >= ul.ctime 
--     UNION ALL 
--     SELECT t3.uid, t3.channel, rcg.mtime AS mts, rcg.gaid AS gaid
--     FROM tmp_behavior_lost_reg_info t3 
--     LEFT JOIN tb_recharge rcg ON t3.uid = rcg.uid AND t3.channel = rcg.channel AND t3.behavior_ts >= rcg.mtime 
--     UNION ALL 
--     SELECT t3.uid, t3.channel, wtd.mtime AS mts, wtd.gaid AS gaid
--     FROM tmp_behavior_lost_reg_info t3 
--     LEFT JOIN tb_withdrawal wtd ON t3.uid = wtd.uid AND t3.channel = wtd.channel AND t3.behavior_ts >= wtd.mtime 
--   ) t 
--   GROUP BY uid, channel 
-- ) t3_reg ON t3.uid = t3_reg.uid AND t3.channel = t3_reg.channel ;
-- 


-- 到这里，所有的记录都补充上了注册信息数据

-- DROP TABLE IF EXISTS tmp_behavior_reg_info;
-- 添加更完整的同期群数据信息
DROP TABLE IF EXISTS tmp_behavior_cohort;
CREATE TABLE tmp_behavior_cohort
(
  category VARCHAR(12),
  source_id BIGINT,
  uid VARCHAR(50),
  channel VARCHAR(50),
  pn VARCHAR(10),
  cohort INT,
  behavior_ts BIGINT, -- 行为时间戳
  behavior_order VARCHAR(64), -- 行为订单号
  behavior_amount DECIMAL(16, 2), -- 行为订单金额
  bdates INT, -- 行为日期
  reg_dates INT, -- 注册日期
  reg_gaid VARCHAR(50),
  reg_ts BIGINT   -- 注册时间戳
);

-- SELECT * FROM tmp_behavior_cohort  where cohort = 1 limit 10;

INSERT INTO tmp_behavior_cohort(category, source_id, uid, channel, pn, cohort, behavior_ts, behavior_order, behavior_amount, bdates, reg_dates, reg_gaid, reg_ts)
SELECT category, source_id, uid, channel, pn, generate_cohort(behavior_ts, reg_ts) AS cohort, behavior_ts, behavior_order, behavior_amount, timestamp_date() AS bdates, AS reg_dates, reg_gaid, reg_ts    
FROM tmp_behavior_reg_info;













