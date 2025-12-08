USE suyh_cdap_doris;

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

--  TODO: suyh - 可以按需修改目标日期
SET @target_dates = 20220120;
SET @next_dates = plus_days(@target_dates, 1);

SELECT @target_dates, @next_dates;


-- 添加更完整的同期群数据信息
-- 用于登录数据，每个日期（对应完整的同期值）一个
DROP TABLE IF EXISTS tmp_behavior_cohort_login;
CREATE TABLE tmp_behavior_cohort_login
(
  source_tb VARCHAR(16),
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

-- SELECT reg_dates, COUNT(1) FROM tmp_behavior_cohort_login GROUP BY reg_dates ORDER BY reg_dates;

-- SELECT COUNT(1) FROM doris_tb_user_login WHERE `day` IN (@target_dates, @next_dates);
-- 登录表在当天的相关数据
-- 这里求得的是，一个完整同期值对应的数据，其他数据要过滤掉。
INSERT INTO tmp_behavior_cohort_login(source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts)
SELECT 'tb_user_login' AS source_tb, ft.id AS source_id, uid, channel, ft.pn, 
  cohort, behavior_ts, 
  timestamp_date(behavior_ts, p.zone_id) AS bdates, 
  ft.reg_dates AS reg_dates, ft.reg_gaid, ft.reg_ts
FROM (
  SELECT ul.id, ul.uid, ul.channel, ul.ctime AS behavior_ts, 
    tup.ctime AS reg_ts, tup.reg_dates, tup.gaid AS reg_gaid, tup.pn, 
    generate_cohort(ul.ctime, tup.ctime) AS cohort
  FROM doris_tb_user_login ul 
  -- 当天注册的数据，会从tb_user 表中取出来，然后合并进来
  INNER JOIN tb_user_plus tup ON ul.uid = tup.uid AND ul.channel = tup.channel AND tup.source_tb != 'tb_user'
  WHERE ul.`day` IN (@target_dates, @next_dates)
) ft
INNER JOIN project p ON ft.pn = p.pn
-- 同期值对应日期的开始时间戳必须落在统计日期当天的24 小时内
WHERE ft.reg_ts + ft.cohort * 86400 >= midnight_timestamp(@target_dates, p.zone_id) AND ft.reg_ts + ft.cohort * 86400 < midnight_timestamp(@next_dates, p.zone_id);

-- 注册数据也需要按登录数据一样的处理
INSERT INTO tmp_behavior_cohort_login(source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts)
SELECT 'tb_user' AS source_tb, ft.id AS source_id, uid, channel, ft.pn, 
  cohort, behavior_ts, 
  timestamp_date(behavior_ts, p.zone_id) AS bdates, 
  timestamp_date(reg_ts, p.zone_id) AS reg_dates, reg_gaid, reg_ts
FROM (
  SELECT id, uid, channel, ctime AS behavior_ts, 
    ctime AS reg_ts, gaid AS reg_gaid, pn, 
    0 AS cohort
  FROM doris_tb_user 
  WHERE `day` IN (@target_dates)
) ft
INNER JOIN project p ON ft.pn = p.pn;


-- 最终长久存储到该表中
-- DROP TABLE IF EXISTS behavior_cohort_login;
CREATE TABLE IF NOT EXISTS behavior_cohort_login
(
  source_tb VARCHAR(16),
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
INSERT INTO behavior_cohort_login(source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts)
SELECT source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, reg_dates, reg_gaid, reg_ts
FROM tmp_behavior_cohort_login tp
WHERE NOT EXISTS (SELECT 1 FROM behavior_cohort_login bl WHERE tp.uid = bl.uid AND tp.channel = bl.channel);

-- 登录计算活跃
-- SELECT reg_dates, cohort, bdates, count(1) FROM behavior_cohort_login GROUP BY reg_dates, cohort, bdates ORDER BY reg_dates, cohort, bdates;







-- 添加更完整的同期群数据信息
-- 用于充值数据，每个日期（对应完整的同期值）一个
DROP TABLE IF EXISTS tmp_behavior_cohort_recharge;
CREATE TABLE tmp_behavior_cohort_recharge
(
  source_tb VARCHAR(16),
  source_id BIGINT,
  uid VARCHAR(50),
  channel VARCHAR(50),
  pn VARCHAR(10),
  cohort INT,
  behavior_ts BIGINT, -- 行为时间戳
  bdates INT, -- 行为日期：当前这条数据实际来源于哪一天
  recharge_order VARCHAR(64), 
  recharge_amount DECIMAL(18, 2),
  reg_dates INT, -- 注册日期
  reg_gaid VARCHAR(50),
  reg_ts BIGINT   -- 注册时间戳
);

-- 充值表在当天的相关数据
-- 这里求得的是，一个完整同期值对应的数据，其他数据要过滤掉。
INSERT INTO tmp_behavior_cohort_recharge(source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, recharge_order, recharge_amount, reg_dates, reg_gaid, reg_ts)
SELECT 'tb_recharge' AS source_tb, ft.id AS source_id, uid, channel, ft.pn, 
  cohort, behavior_ts, 
  timestamp_date(behavior_ts, p.zone_id) AS bdates, 
  `order` AS recharge_order,
  goods_amt AS recharge_amount,
  ft.reg_dates, reg_gaid, reg_ts
FROM (
  SELECT ur.id, ur.uid, ur.channel, ur.mtime AS behavior_ts, 
    ur.`order`, ur.goods_amt, 
    tup.ctime AS reg_ts, tup.gaid AS reg_gaid, tup.pn, tup.reg_dates,
    generate_cohort(ur.mtime, tup.ctime) AS cohort
  FROM doris_tb_recharge ur 
  INNER JOIN tb_user_plus tup ON ur.uid = tup.uid AND ur.channel = tup.channel 
  WHERE ur.`day` IN (@target_dates, @next_dates)
) ft
INNER JOIN project p ON ft.pn = p.pn
-- 同期值对应日期的开始时间戳必须落在统计日期当天的24 小时内
WHERE reg_ts + cohort * 86400 >= midnight_timestamp(@target_dates, p.zone_id) AND reg_ts + cohort * 86400 < midnight_timestamp(@next_dates, p.zone_id);


-- DROP TABLE IF EXISTS behavior_cohort_recharge;
CREATE TABLE IF NOT EXISTS behavior_cohort_recharge
(
  source_tb VARCHAR(16),
  source_id BIGINT,
  uid VARCHAR(50),
  channel VARCHAR(50),
  pn VARCHAR(10),
  cohort INT,
  behavior_ts BIGINT, -- 行为时间戳
  bdates INT, -- 行为日期：当前这条数据实际来源于哪一天
  recharge_order VARCHAR(64), 
  recharge_amount DECIMAL(18, 2),
  reg_dates INT, -- 注册日期
  reg_gaid VARCHAR(50),
  reg_ts BIGINT   -- 注册时间戳
);

INSERT INTO behavior_cohort_recharge(source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, recharge_order, recharge_amount, reg_dates, reg_gaid, reg_ts)
SELECT source_tb, source_id, uid, channel, pn, cohort, behavior_ts, bdates, recharge_order, recharge_amount, reg_dates, reg_gaid, reg_ts
FROM tmp_behavior_cohort_recharge tr
WHERE NOT EXISTS(SELECT 1 FROM behavior_cohort_recharge br WHERE tr.uid = br.uid AND tr.channel = br.channel);





