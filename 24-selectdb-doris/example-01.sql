
-- 删除物化视图
DROP MATERIALIZED VIEW mv_dates_record_count;


-- 创建物化视图
CREATE MATERIALIZED VIEW `mv_dates_record_count`
-- BUILD DEFERRED   -- 延迟刷新
BUILD IMMEDIATE     -- IMMEDIATE：立即刷新，默认方式。
REFRESH AUTO ON SCHEDULE EVERY 1 minute -- AUTO：尽量增量刷新，只刷新自上次物化刷新后数据变化的分区，如果不能感知数据变化的分区，只能退化成全量刷新，刷新所有分区。
DUPLICATE KEY (dates)
COMMENT '按dates字段统计记录数的异步物化视图'
-- PARTITION BY (dates) -- 原表需要有该字段的分区才能使用分区，否则无法使用分区
AS
SELECT
  `dates`,
  SUM(IF(status = 3, 1, 0)) AS failed_count,
  SUM(IF(status = 2, 1, 0)) AS success_count,
  SUM(IF(status IN (1, 2, 4), 1, 0)) AS valid_count,
  SUM(IF(status != 8, 1, 0)) AS cashier_count,
  SUM(IF(status IN (2, 4), 1, 0)) AS cashier_valid_count,
  COUNT(DISTINCT CASE WHEN status IS NOT NULL AND status != 8 THEN receiver_uid END) AS uv_user_count, 
  COUNT(*) AS record_count 
FROM `scheduling_transfer_record`
GROUP BY `dates`; 





select * from mv_dates_record_count order by dates desc limit 10;


