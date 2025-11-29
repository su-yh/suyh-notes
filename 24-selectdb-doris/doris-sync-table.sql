USE suyh_cdap_doris;

-- 同步表数据
DROP TABLE IF EXISTS doris_tb_user;
CREATE TABLE doris_tb_user LIKE tb_user;
DROP TABLE IF EXISTS doris_tb_user_login;
CREATE TABLE doris_tb_user_login LIKE tb_user_login;
DROP TABLE IF EXISTS doris_tb_recharge;
CREATE TABLE doris_tb_recharge LIKE tb_recharge;
DROP TABLE IF EXISTS doris_tb_withdrawal;
CREATE TABLE doris_tb_withdrawal LIKE tb_withdrawal;

INSERT INTO doris_tb_user(id, uid, channel, ctime, gaid, origin_channel, vungo_user_id, day, cts, pn)
SELECT id, IF(uid IS NULL, '', uid), IF(channel IS NULL, '', channel), IF(ctime IS NULL, 0, ctime), 
    IF(gaid IS NULL, '', gaid), origin_channel, vungo_user_id, day, cts, IF(pn IS NULL, 'hy', pn) 
FROM tb_user;
INSERT INTO doris_tb_user_login(id, uid, src, channel, ctime, gaid, origin_channel, vungo_user_login_id, day, cts, pn)
SELECT id, IF(uid IS NULL, '', uid), src, IF(channel IS NULL, '', channel), IF(ctime IS NULL, 0, ctime), 
    IF(gaid IS NULL, '', gaid), origin_channel, vungo_user_login_id, day, cts, IF(pn IS NULL, 'hy', pn) 
FROM tb_user_login;
INSERT INTO doris_tb_recharge(id, uid, ctime, goods_amt, channel, chips, vungo_recharge_id, gaid, origin_channel, day, `order`, cts, pn, mtime, login_channel, register_channel)
SELECT id, IF(uid IS NULL, '', uid), IF(ctime IS NULL, 0, ctime), IF(goods_amt IS NULL, 0.00, goods_amt), IF(channel IS NULL, '', channel), chips, vungo_recharge_id, 
    IF(gaid IS NULL, '', gaid), origin_channel, day, IF(`order` IS NULL, '', `order`), cts, IF(pn IS NULL, 'hy', pn), IF(mtime IS NULL, 0, mtime), login_channel, register_channel 
FROM tb_recharge;
INSERT INTO doris_tb_withdrawal(id, uid, ctime, amount, channel, vungo_withdrawal_id, origin_channel, gaid, day, `order`, cts, pn, mtime, login_channel, register_channel)
SELECT id, IF(uid IS NULL, '', uid), IF(ctime IS NULL, 0, ctime), amount, IF(channel IS NULL, '', channel), vungo_withdrawal_id, origin_channel, 
    IF(gaid IS NULL, '', gaid), day, IF(`order` IS NULL, '', `order`), cts, IF(pn IS NULL, 'hy', pn), IF(mtime IS NULL, 0, mtime), login_channel, register_channel 
FROM tb_withdrawal;



