
-- tb_user 表及不在tb_user 表的用户
-- DROP TABLE IF EXISTS tb_user_plus;
CREATE TABLE IF NOT EXISTS tb_user_plus 
(
  uid VARCHAR(100),
  channel VARCHAR(100),
  source_tb VARCHAR(16) NOT NULL COMMENT '来源表名',
  source_id BIGINT NOT NULL,
  ctime BIGINT,
  reg_dates INT, -- 使用 ctime 以及 pn 对应的时区计算得到的注册日期
  gaid VARCHAR(255),
  pn VARCHAR(20),
  history_active_ts BIGINT -- 该gaid 在ctime 时间之前最后一次（4 张表）出现活跃的时间戳
)
UNIQUE KEY(uid, channel)
DISTRIBUTED BY HASH(uid) BUCKETS AUTO;



-- 来源表：注册表
INSERT INTO tb_user_plus(source_tb, source_id, uid, channel, ctime, reg_dates, gaid, pn, history_active_ts)
SELECT 'tb_user' AS source_tb, tu.id AS source_id, tu.uid, tu.channel, tu.ctime, timestamp_date(tu.ctime, p.zone_id) AS reg_dates, tu.gaid, tu.pn, NULL
FROM doris_tb_user tu
LEFT JOIN project p ON tu.pn = p.pn 
WHERE NOT EXISTS (SELECT 1 FROM tb_user_plus tup WHERE tu.id = tup.source_id);
-- SELECT COUNT(1) FROM doris_tb_user;
-- SELECT COUNT(1) FROM tb_user_plus;

-- 每次都需要创建的临时表
-- 这张表来存放，不在注册表（tb_user）不在扩展注册表（tb_user_plus）中的用户行为数据，后续将要查询这些用户的注册信息
-- 这张临时表中允许 重复的用户记录，在后面会处理去重
DROP TABLE IF EXISTS tmp_user_other;
CREATE TABLE tmp_user_other 
(
  uid VARCHAR(100),
  channel VARCHAR(100),
  ctime BIGINT 
);

-- SELECT * FROM tmp_user_other;

-- 来源表：登录表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM doris_tb_user_login l 
-- 在扩展注册表中不存在记录
WHERE NOT EXISTS (SELECT 1 FROM tb_user_plus u WHERE u.uid = l.uid AND u.channel = l.channel);
-- 来源表：充值表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM doris_tb_recharge l
-- 在扩展注册表中不存在记录
WHERE NOT EXISTS (SELECT 1 FROM tb_user_plus u WHERE u.uid = l.uid AND u.channel = l.channel);
-- 来源表：提现表
INSERT INTO tmp_user_other(uid, channel, ctime)
SELECT uid, channel, ctime
FROM doris_tb_withdrawal l 
-- 在扩展注册表中不存在记录
WHERE NOT EXISTS (SELECT 1 FROM tb_user_plus u WHERE u.uid = l.uid AND u.channel = l.channel);

-- SELECT COUNT(1) FROM tmp_user_other;

-- 找到这些不在注册表中记录的注册信息并永久存储到表 tb_user_plus 中
INSERT INTO tb_user_plus(source_tb, source_id, uid, channel, ctime, reg_dates, gaid, pn, history_active_ts)
SELECT source_tb, source_id, uid, channel, ctime, timestamp_date(ctime, p.zone_id) AS reg_dates, gaid, fft.pn, NULL AS history_active_ts
FROM (
  SELECT 
    struct_element(reg_info, 'source_tb')     AS source_tb,
    struct_element(reg_info, 'id')            AS source_id,
    struct_element(reg_info, 'uid')           AS uid,
    struct_element(reg_info, 'channel')       AS channel,
    struct_element(reg_info, 'min_ctime')     AS ctime,
    struct_element(reg_info, 'gaid')          AS gaid,
    struct_element(reg_info, 'pn')            AS pn
  FROM (
    SELECT uid, channel, user_register_info(source_tb, id, uid, channel, ctime, gaid, pn, `day`) AS reg_info
    FROM (
      SELECT 'tb_user_login' AS source_tb, ul.id, ul.uid, ul.channel, ul.ctime AS ctime, ul.gaid AS gaid, ul.pn, ul.`day`
      FROM tmp_user_other tuo 
      LEFT JOIN doris_tb_user_login ul ON tuo.uid = ul.uid AND tuo.channel = ul.channel AND tuo.ctime >= ul.ctime 
      UNION ALL 
      SELECT 'tb_recharge' AS source_tb, rcg.id, rcg.uid, rcg.channel, rcg.ctime AS ctime, rcg.gaid AS gaid, rcg.pn, rcg.`day`
      FROM tmp_user_other tuo 
      LEFT JOIN doris_tb_recharge rcg ON tuo.uid = rcg.uid AND tuo.channel = rcg.channel AND tuo.ctime >= rcg.ctime 
      UNION ALL 
      SELECT 'tb_withdrawal' AS source_tb, wtd.id, wtd.uid, wtd.channel, wtd.ctime AS ctime, wtd.gaid AS gaid, wtd.pn, wtd.`day`
      FROM tmp_user_other tuo 
      LEFT JOIN doris_tb_withdrawal wtd ON tuo.uid = wtd.uid AND tuo.channel = wtd.channel AND tuo.ctime >= wtd.ctime 
    ) t 
    GROUP BY uid, channel 
  ) ft
) fft
LEFT JOIN project p ON fft.pn = p.pn
WHERE NOT EXISTS (SELECT 1 FROM tb_user_plus tup WHERE fft.uid = tup.uid AND fft.channel = tup.channel);

-- suyh - 到此，所有的用户注册信息都全部找到，要么在tb_user 中，要么在 tb_user_plus 中。



