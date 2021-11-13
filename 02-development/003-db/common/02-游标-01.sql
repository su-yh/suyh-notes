T-SQL中的游标定义在MSDN中如下:

DECLARE cursor_name CURSOR [ LOCAL | GLOBAL ] 
     [ FORWARD_ONLY | SCROLL ] 
     [ STATIC | KEYSET | DYNAMIC | FAST_FORWARD ] 
     [ READ_ONLY | SCROLL_LOCKS | OPTIMISTIC ] 
     [ TYPE_WARNING ] 
     FOR select_statement 
     [ FOR UPDATE [ OF column_name [ ,...n ] ] ]
[;]

=============================================




    BEGIN
        V_OBJECT := 'PRC_DATA_PROCESS';
        V_TABLE := P_TABLE;
        --1、获取过程指定过程的参数信息
        V_STEP := '1、获取过程['||P_PROCESS||']指定过程的参数信息';
        BEGIN
           SELECT SOURCE_SYS_CODE,SYS_CODE,OP_TYPE,REAL_STATUS,STATUS_CODE,REAL_TABLE,DAY_TYPE INTO V_SOURCE_SYS_CODE,V_SYS_CODE,V_OP_TYPE,V_REAL_STATUS,V_STATUS_CODE,V_REAL_TABLE,V_DAY_TYPE FROM COLL_PROCE_PARAM WHERE PROCESS_CODE = P_PROCESS;
           EXCEPTION
             WHEN NO_DATA_FOUND THEN
                 V_STATUS := 3;
                 V_TABLE := 'COLL_PROCE_PARAM';
                 V_STEP := '获取存储过程['||P_PROCESS||']参数配置信息为空';
                 --记录日记
                 PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,'','','',V_STATUS,'','',NULL,NULL);
             RETURN;
        END;
        --2、根据上游系统编码遍历需要加工的日终批量、月末批量的表
        V_STEP := '2、根据上游系统编码['||V_SYS_CODE||']遍历需要加工的日终批量、月末批量的表';
        V_TABLE := 'COLL_DRAGON_TBL';
        IF LENGTH(TRIM(P_TABLE)) > 0 THEN
          V_SQL := 'SELECT DRAGON_TBL,INTF_TBL,LOAD_TYPE,CALENDAR_NUM,CLR_FLAG,IS_REALTIME,CLR_SQL,DRAGON_TBL_COL,INTF_TBL_COL FROM COLL_DRAGON_TBL WHERE SYS_CODE = '''||V_SYS_CODE||''' AND INTF_TBL =''' || P_TABLE || ''' AND IS_REALTIME IN('||V_REAL_TABLE||') ';
        ELSE
          V_SQL := 'SELECT DRAGON_TBL,INTF_TBL,LOAD_TYPE,CALENDAR_NUM,CLR_FLAG,IS_REALTIME,CLR_SQL,DRAGON_TBL_COL,INTF_TBL_COL FROM COLL_DRAGON_TBL WHERE SYS_CODE = '''||V_SYS_CODE||''' AND IS_REALTIME IN('||V_REAL_TABLE||') ORDER BY XH ASC';
        END IF;

        BEGIN
        --3、遍历加工接口表
        OPEN C_INTF FOR V_SQL;
          V_STEP := '3、根据上游系统编码['||V_SYS_CODE||']遍历对应加工的日终批量、月末批量的接口表';
          LOOP
            FETCH C_INTF INTO V_DRAGON_TBL,V_INTF_TBL,V_LOAD_TYPE,V_CALENDAR_NUM,V_CLR_FLAG,V_IS_REALTIME,V_CLR_SQL,V_DRAGON_TBL_COL,V_INTF_TBL_COL; --循环关系表游标
               EXIT WHEN C_INTF%NOTFOUND;
             BEGIN
               --4、判断dragon 是否将接口表数据推送过来
               V_STEP := '4、判断dragon 是否将接口表['||V_DRAGON_TBL||']数据推送过来';
               --采集日期是工作日
               IF TRIM(V_CLR_FLAG) = '1' THEN
                 --判断 是否为工作日
                 BEGIN
                   V_SQL := 'SELECT WORKING_DAY FROM CALENDAR WHERE THE_DATE = TO_DATE(TO_CHAR('||P_DATE||'), ''YYYY/MM/DD'') ';
                   EXECUTE IMMEDIATE V_SQL
                     INTO V_WORKING_DAY;
                   EXCEPTION
                      WHEN NO_DATA_FOUND THEN
                       V_STATUS := 3;
                       V_TABLE := 'CALENDAR';
                       V_STEP := '日历表日期['||P_DATE||']不存在';
                       --记录日记
                       PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_SYS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                 END;
                 --是工作日
                 IF V_WORKING_DAY = '1' THEN
                    V_QDATE := FUNC_SELT_CALENDAR(P_DATE,V_CALENDAR_NUM);--计算V_CALENDAR_NUM工作日
                    V_DATE := TO_NUMBER(TO_CHAR(V_QDATE, 'YYYYMMDD'));
                    dbms_output.put_line(P_DATE);
                    dbms_output.put_line(V_CALENDAR_NUM);
                    dbms_output.put_line(V_QDATE);
                    dbms_output.put_line(V_DATE);
                    dbms_output.put_line(V_SOURCE_SYS_CODE);
                    dbms_output.put_line(V_DRAGON_TBL);
                    BEGIN
                      -- 根据 T-1 工作日、采集源系统的编码、推送完成查询推送状态表记录
                      SELECT A.STATUS_CODE INTO V_PULL_STATUS FROM DBI_DATAPUSH_STATUS A WHERE A.BIZ_DATE = V_DATE AND A.SRC_ID = V_SOURCE_SYS_CODE AND A.TARGET_TABLE = V_DRAGON_TBL;
                        EXCEPTION
                          WHEN NO_DATA_FOUND THEN
                             V_STATUS := 3;
                             V_TABLE := 'DBI_DATAPUSH_STATUS';
                             V_STEP := 'dragon['||V_DATE||']未推送数据到接口表['||V_DRAGON_TBL||']';
                             --记录日记
                             PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_SYS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                    END;
                  END IF;
                  V_STEP := '5、dragon 将接口表['||V_DRAGON_TBL||']数据推送过来,采集记录[DBI_DATAPUSH_STATUS]存在业务日期['||V_DATE||']的数据';
                  --dragon 将表同步
                  IF V_PULL_STATUS = '2' THEN
                     --dragon 查询推送到biref dbi接口表是否有数据
                     V_SQL := 'SELECT COUNT(0) FROM '||V_DRAGON_TBL||'';
                     EXECUTE IMMEDIATE V_SQL INTO V_COUNT;
                     IF V_COUNT > 0 THEN
                       V_STEP := '6、dragon 将接口表['||V_DRAGON_TBL||']数据推送过来['||V_PULL_STATUS||']已完成,采集记录[DBI_DATAPUSH_STATUS]存在业务日期['||V_DATE||']数据量['||V_COUNT||']';
                       IF TRIM(V_LOAD_TYPE) = 'F' THEN
                         --调用全量的数据加工过程
                         PRC_TABLE_FULL_INSERT(V_DAY_TYPE,V_SYS_CODE,V_DRAGON_TBL,V_DRAGON_TBL_COL,'WHERE 1=1 ',V_INTF_TBL,V_INTF_TBL_COL);
                       END IF;
                        /*处理条件增量表*/
                       IF TRIM(V_LOAD_TYPE) = 'I' THEN
                          --条件增量表
                         PRC_TABLE_INCREMENT_INSERT(V_DAY_TYPE,V_SYS_CODE,V_DRAGON_TBL,V_DRAGON_TBL_COL,V_CLR_SQL,V_INTF_TBL,V_INTF_TBL_COL);
                       END IF;
                     ELSE
                       V_STATUS := 3;
                       V_TABLE := 'DBI_DATAPUSH_STATUS';
                       V_STEP := 'dragon['||V_DATE||']无数据同步到接口表['||V_DRAGON_TBL||'] 加工异常';
                       --记录日记
                       PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_STATUS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                     END IF;
                  ELSIF V_PULL_STATUS = '1' THEN
                    V_STATUS := 3;
                    V_TABLE := 'DBI_DATAPUSH_STATUS';
                    V_STEP := 'dragon['||V_DATE||']正在推送数据到接口表['||V_DRAGON_TBL||'] 已延迟加工异常';
                    --记录日记
                    PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_STATUS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                  END IF;
               --采集日期是自然日期
               ELSIF TRIM(V_CLR_FLAG) = '2' THEN
                   --计算取V_CALENDAR_NUM自然日  V_CALENDAR_NUM 小于0 取前V_CALENDAR_NUM自然日期，大于0 取后V_CALENDAR_NUM自然日期
                   V_DATE := P_DATE + V_CALENDAR_NUM;
                    dbms_output.put_line(P_DATE);
                    dbms_output.put_line(V_CALENDAR_NUM);
                    dbms_output.put_line(V_DATE);
                    dbms_output.put_line(V_SOURCE_SYS_CODE);
                    dbms_output.put_line(V_DRAGON_TBL);
                   BEGIN
                      -- 根据 T-1 自然日、采集源系统的编码、推送完成查询推送状态表记录
                      SELECT A.STATUS_CODE INTO V_PULL_STATUS FROM DBI_DATAPUSH_STATUS A WHERE A.BIZ_DATE = V_DATE AND A.SRC_ID = V_SOURCE_SYS_CODE AND A.TARGET_TABLE = V_DRAGON_TBL;
                        EXCEPTION
                          WHEN NO_DATA_FOUND THEN
                             V_STATUS := 3;
                             V_TABLE := 'DBI_DATAPUSH_STATUS';
                             V_STEP := 'dragon['||V_DATE||']未推送数据到接口表['||V_DRAGON_TBL||']';
                             --记录日记
                             PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_SYS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                   END;
                   V_STEP := '7、dragon 将接口表['||V_DRAGON_TBL||']数据推送过来,采集记录[DBI_DATAPUSH_STATUS]存在业务日期['||V_DATE||']的数据';
                   --dragon 将表同步
                   IF V_PULL_STATUS = '2' THEN
                     --dragon 查询推送到biref dbi接口表是否有数据
                     V_SQL := 'SELECT COUNT(0) FROM '||V_DRAGON_TBL||'';
                     EXECUTE IMMEDIATE V_SQL INTO V_COUNT;
                     IF V_COUNT > 0 THEN
                        V_STEP := '8、dragon 将接口表['||V_DRAGON_TBL||']数据推送过来['||V_PULL_STATUS||']已完成,采集记录[DBI_DATAPUSH_STATUS]存在业务日期['||V_DATE||']数据量['||V_COUNT||']';
                       IF TRIM(V_LOAD_TYPE) = 'F' THEN
                         --调用全量的数据加工过程
                         PRC_TABLE_FULL_INSERT(V_DAY_TYPE,V_SYS_CODE,V_DRAGON_TBL,V_DRAGON_TBL_COL, 'WHERE 1=1 ', V_INTF_TBL,V_INTF_TBL_COL);
                       END IF;
                        /*处理条件增量表*/
                       IF TRIM(V_LOAD_TYPE) = 'I' THEN
                          --条件增量表
                         PRC_TABLE_INCREMENT_INSERT(V_DAY_TYPE,V_SYS_CODE,V_DRAGON_TBL,V_DRAGON_TBL_COL, V_CLR_SQL, V_INTF_TBL,V_INTF_TBL_COL);
                       END IF;
                     ELSE
                       V_STATUS := 3;
                       V_TABLE := 'DBI_DATAPUSH_STATUS';
                       V_STEP := 'dragon['||V_DATE||']无数据同步到接口表['||V_DRAGON_TBL||'] 加工异常';
                       --记录日记
                       PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_STATUS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                     END IF;
                   ELSIF V_PULL_STATUS = '1' THEN
                     V_STATUS := 3;
                     V_TABLE := 'DBI_DATAPUSH_STATUS';
                     V_STEP := 'dragon['||V_DATE||']正在推送数据到接口表['||V_DRAGON_TBL||'] 已延迟加工异常';
                     --记录日记
                     PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_SYS_CODE,'','',V_STATUS,V_DAY_TYPE,'',NULL,NULL);
                   END IF;
               END IF;
             EXCEPTION
               WHEN OTHERS THEN
                 ROLLBACK;
                 V_STATUS := '4';
                 --V_STEP := SUBSTR(SQLERRM, 1, 1024); --返回遇到的ORACLE错误信息.
                 V_ERROR_LINE := DBMS_UTILITY.FORMAT_ERROR_BACKTRACE; --异常定位追踪;
                 V_ERROR_CODE := SQLCODE; --返回遇到的ORACLE错误号
                 V_ERROR_INFO := SQLERRM;
                 --记录日记
                 PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,V_SYS_CODE,V_ERROR_LINE,V_ERROR_CODE,V_STATUS,V_DAY_TYPE,V_ERROR_INFO,NULL,NULL);
            END;
          END LOOP;
        CLOSE C_INTF;
        END;
     EXCEPTION
         WHEN OTHERS THEN
           ROLLBACK;
           V_STATUS := '4';
           V_ERROR_LINE := DBMS_UTILITY.FORMAT_ERROR_BACKTRACE; --异常定位追踪;
           V_ERROR_CODE := SQLCODE; --返回遇到的ORACLE错误号
           V_ERROR_INFO := SQLERRM;
           --记录日记
           PRC_COLL_PROCE_LOG(V_OBJECT,V_TABLE,V_STEP,'',V_ERROR_LINE,V_ERROR_CODE,V_STATUS,'',V_ERROR_INFO,NULL,NULL);
     END;