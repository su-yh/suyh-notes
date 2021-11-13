日期处理：

当前日期：
	SELECT sysdate  FROM dual; 
日期加减：
	前一天: SELECT TO_CHAR(sysdate - 1, 'yyyy-MM-dd') FROM dual;
与当前表字段的处理:
	SELECT * FROM t WHERE col_time > trunc(sysdate); 


oracle日期与字符串的互相转换SQL语句
日期转字符串：
    SELECT TO_CHAR(sysdate,'yyyy-MM-dd HH24:mi:ss') FROM dual;
字符串转日期
    SELECT TO_DATE('2005-01-01 13:14:20','yyyy-MM-dd HH24:mi:ss') FROM dual;













