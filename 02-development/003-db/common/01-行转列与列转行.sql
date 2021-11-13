CREATE  TABLE StudentScores
(
   UserName         NVARCHAR(20) COMMENT "学生姓名",
    SUBJECT          NVARCHAR(30) COMMENT "科目",
    Score            FLOAT COMMENT "成绩"
);

-- Hive
CREATE EXTERNAL TABLE StudentScores(
    UserName String COMMENT '学生姓名', 
    Subject String COMMENT '科目', 
    Score INT COMMENT '成绩'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/user/root/StudentScore'


INSERT INTO StudentScores VALUES 
('Nick', '语文', 80), 
('Nick', '数学', 90), 
('Nick', '英语', 70), 
('Nick', '生物', 85), 
('Kent', '语文', 80), 
('Kent', '数学', 90), 
('Kent', '英语', 70), 
('Kent', '生物', 85);

UserName,SUBJECT,Score
Nick,语文,80
Nick,数学,90
Nick,英语,70
Nick,生物,85
Kent,语文,80
Kent,数学,90
Kent,英语,70
Kent,生物,85

-- 行转列

    -- 普通SQL
    SELECT UserName, 
        MAX(CASE SUBJECT WHEN '语文' THEN Score ELSE 0 END) AS '语文',
        MAX(CASE SUBJECT WHEN '数学' THEN Score ELSE 0 END) AS '数学',
        MAX(CASE SUBJECT WHEN '英语' THEN Score ELSE 0 END) AS '英语',
        MAX(CASE SUBJECT WHEN '生物' THEN Score ELSE 0 END) AS '生物'
    FROM StudentScores GROUP BY UserName;
    -- 结果集
    UserName	语文	数学	英语	生物
    Kent	80	90	70	85
    Nick	80	90	70	85

-- MYSQL 要到8 版本以上才支持
-- PIVOT
    SELECT * FROM StudentScores
    PIVOT
    (
        MAX (Score)
        FOR SUBJECT in([语文], [数学], [英语], [生物])
    ) 
-- UNPIVOT 


-- MYSQL 列转行博客：
-- https://blog.csdn.net/zhengjl369/article/details/80853310







