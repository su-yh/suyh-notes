

- ## 行转列
name        subject         score
zhangsan    yuwen           89
zhangsan    shuxue          90
zhangsan    yingyu          83
lisi        yuwen           89
lisi        shuxue          90
lisi        yingyu          83
wangwu      yuwen           89
wangwu      shuxue          90
wangwu      yingyu          83

name        yuwen   shuxue  yingyu
zhangsan    89      90      83
lisi        89      90      83
wamgwi      89      90      83

join:
    SELECT name, score FROM t WHERE t.subject='yuwen'; t1
    SELECT name, score FROM t WHERE t.subject='shuxue'; t2
    SELECT name, score FROM t WHERE t.subject='yingyu'; t3

    SELECT t1.name, t1.score, t2.score, t3.score from t1 join t2 on t1.name=t2.name join t3 on t1.name = t3.name;

union all:
decode:
case then:



--- 

- ## 统计单词个数
FROM (SELECT explode(split(line, " ")) word FROM wc) t
INSERT INTO wc_result
    SELECT t.word;

SELECT word, count(word) FROM wc_result GROUP BY word;







