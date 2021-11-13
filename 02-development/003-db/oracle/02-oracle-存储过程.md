


- ## 学习博客
    - > 网址
        > - `https://blog.csdn.net/uniqueweimeijun/article/details/82668835`

- ## 权限
    - > oracle 中创建和使用存储过程需要授权，如果没有授权则会报错
        - > `ORA-0131: Insufficient privileges`

- ## PL/SQL 工具中执行存储过程
    - > `EXECUTE PACKAGE_NAME.PROC_NAME(PARAM1, PARAM2, ...)`
    - > 还可以使用`BEGIN END `块

- ## 存储过程中动态执行一个SQL 字符串
    - > 执行一条查询，并将结果存到一个变量中
        > - `EXECUTE IMMEDIATE ls_sql INTO ln_cnt;`
    - > 执行一条SQL，并为其传参
        > - `EXECUTE IMMEDIATE str_sql USING IN in_param1, IN in_param2, OUT out_param1, ...`
    - > 同时要传参，还要获取结果的。`INTO` 语句要在 `USING` 后面
        >>      EXECUTE IMMEDIATE 'select dname, loc from dept where deptno = :1'
        >>          INTO l_nam, l_loc
        >>          USING l_dept ;


- ## 动态执行SQL，并为其传参






 PROCEDURE PRC_COLL_PROCE_LOG(
                                P_OBJECT     VARCHAR2,
                                P_TABLE      VARCHAR2,
                                P_STEP       VARCHAR2,
                                P_SYS_CODE   VARCHAR2,
                                P_ERROR_LINE VARCHAR2,
                                P_ERROR_CODE VARCHAR2,
                                P_STATUS     NUMBER,
                                P_TYPE       VARCHAR2,
                                P_ERROR_INFO CLOB,
                                P_START      TIMESTAMP,
                                P_END        TIMESTAMP
                             ) AS
        V_SQL VARCHAR2(4000);
        V_COUNT NUMBER;
        --自治事务
        PRAGMA AUTONOMOUS_TRANSACTION;
    BEGIN
        V_SQL := 'SELECT COUNT(0) FROM COLL_PROCE_LOG T WHERE T.COLL_OBJECT ='''||P_OBJECT||'''  AND T.COLL_STEP = '''||P_STEP||''' AND T.COLL_SYS_CODE ='''||P_SYS_CODE||''' AND T.COLL_TYPE = '''||P_TYPE||''' AND T.COLL_NUM = TO_NUMBER(TO_CHAR(SYSDATE, ''YYYYMMDD''))';
        EXECUTE IMMEDIATE V_SQL INTO V_COUNT;
        IF V_COUNT > 0 THEN
            UPDATE COLL_PROCE_LOG
               SET COLL_DATE       = SYSTIMESTAMP,
                   COLL_TABLE      = P_TABLE,
                   COLL_ERROR_LINE = P_ERROR_LINE,
                   COLL_ERROR_CODE = P_ERROR_CODE,
                   COLL_STATUS     = P_STATUS,
                   COLL_TYPE       = P_TYPE,
                   COLL_ERROR_INFO = P_ERROR_INFO,
                   COLL_START      = P_START,
                   COLL_END        = P_END,
                   UPDATED_DATE    = SYSDATE
             WHERE COLL_OBJECT = P_OBJECT
               AND COLL_STEP = P_STEP
               AND COLL_SYS_CODE = P_SYS_CODE
               AND COLL_TYPE = P_TYPE
               AND COLL_NUM = TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD'));
        ELSE
            INSERT INTO /*+ APPEND */
              COLL_PROCE_LOG NOLOGGING
                (ID,
                 COLL_DATE,
                 COLL_OBJECT,
                 COLL_TABLE,
                 COLL_STEP,
                 COLL_SYS_CODE,
                 COLL_ERROR_LINE,
                 COLL_ERROR_CODE,
                 COLL_STATUS,
                 COLL_TYPE,
                 COLL_ERROR_INFO,
                 COLL_START,
                 COLL_END,
                 COLL_NUM,
                 CREATED_BY,
                 CREATED_DATE,
                 UPDATED_BY,
                 UPDATED_DATE)
                SELECT
                   SYS_GUID(),
                   SYSTIMESTAMP,
                   P_OBJECT,
                   P_TABLE,
                   P_STEP,
                   P_SYS_CODE,
                   P_ERROR_LINE,
                   P_ERROR_CODE,
                   P_STATUS,
                   P_TYPE,
                   P_ERROR_INFO,
                   P_START,
                   P_END,
                   TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD')),
                   'system',
                   sysdate,
                   'system',
                   sysdate
                  FROM DUAL;
        END IF;
        COMMIT;
 END PRC_COLL_PROCE_LOG;
