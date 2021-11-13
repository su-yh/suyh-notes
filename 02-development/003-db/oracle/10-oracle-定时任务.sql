

-- 参考博客
    -- https://blog.csdn.net/www1056481167/article/details/84637674

-- 创建语法
    DECLARE 
        jobno NUMBER;
    BEGIN
        dbms_job.submit ( 　　 
            jobno, --定时器ID，系统自动获得
                'PRC_INSERT;', --what执行的过程名
    　　             SYSDATE, --next_date,定时器开始执行的时间，这样写表示立即执行
    　　            'sysdate + 15/1440' --interval,设置定时器执行的频率，这样写每隔15分钟执行一次
        );
        COMMIT;
    END;

    说明：总共有4个参数依照顺序为
    declare后面的jobno是一个名字，主要的用途是给定时器起一个名字，下面的job引用此jobno。
     job：输出变量，是此任务在任务队列中的编号；
    what：执行的任务的名称及其输入参数；
    next_date：任务执行的时间；
    interval：任务执行的时间间隔。

-- 示例
    DECLARE
    p_project_statis_job number;
    BEGIN
        sys.dbms_job.submit(
                job => p_project_statis_job,
                what => 'P_PROJECT_STATIS;',
                -- 初次执行时间-sysdate立即执行。此处第一次执行次任务的时间为2018-11-29 02:30:00
                next_date =>TO_DATE('2018-11-29 2:30:00','yyyy-mm-dd hh24:mi:ss'),
                -- 执行周期为每天的--每天2:30()执行
                interval => 'TRUNC(SYSDATE + 1) + (2*60+30)/(24*60)'
            );
        commit;
    END;
 
    说明：该定时任务的名字叫p_project_statis_job,
    1、what指定给那个存储过程设值的定时任务为（P_PROJECT_STATIS）后面要带";"。
    2、next_date指定第一次执行该任务的时间的时间为2018-11-29 2:30:00（此处的时间可以随便,一般都是你当天创建的日期即可）。
    3、interval指定该定时任务的执行频率，此处为每一天的（'TRUNC(SYSDATE + 1) + (2*60+30)/(24*60)'）执行，即凌晨2:30分执行


-- 常用命令：
    -- 查看调度任务
    select * from user_jobs;
    -- 查看正在执行的调度任务
    select * from dba_jobs_running;
    -- 查看执行完的调度任务
    select * from dba_jobs;
    
    -- 1、删除定时任务的存储过程 
    BEGIN
    /*210为job的id,此id不是随便填写的,而是执行select * from user_jobs;查询到定时任务名称对应的id*/
    dbms_job.remove(210); 
    commit;
    END;
    -- 2、也可以使用以下删除方式
    exec dbms_job.remove(83);--删除一个定时器
    -- 停止一个定时器
    exec DBMS_JOB.BROKEN(83,SYS.DIUTIL.INT_TO_BOOL(1));

-- 常见的Interval 设置
    -- 描述                                INTERVAL参数值 
    -- 每天午夜12点                        TRUNC(SYSDATE + 1) 
    -- 每天早上8点30分                     TRUNC(SYSDATE + 1) + （8*60+30）/(24*60) 
    -- 每星期二中午12点                    NEXT_DAY(TRUNC(SYSDATE ), ''TUESDAY'' ) + 12/24 
    -- 每个月第一天的午夜12点              TRUNC(LAST_DAY(SYSDATE ) + 1) 
    -- 每个季度最后一天的晚上11点          TRUNC(ADD_MONTHS(SYSDATE + 2/24, 3 ), 'Q' ) -1/24 
    -- 每星期六和日早上6点10分             TRUNC(LEAST(NEXT_DAY(SYSDATE, ''SATURDAY"), NEXT_DAY(SYSDATE, "SUNDAY"))) + （6×60+10）/（24×60）
    每秒钟执行次
    Interval => sysdate + 1/(24 * 60 * 60)
    如果改成sysdate + 10/(24 * 60 * 60)就是10秒钟执行次
    每分钟执行 
    Interval => TRUNC(sysdate,'mi') + 1/ (24*60)
    如果改成TRUNC(sysdate,'mi') + 10/ (24*60) 就是每10分钟执行次
    每天定时执行 
    例如：每天的凌晨1点执行 
    Interval => TRUNC(sysdate) + 1 +1/ (24)
    每周定时执行 
    例如：每周一凌晨1点执行 
    Interval => TRUNC(next_day(sysdate,'星期一'))+1/24
    每月定时执行 
    例如：每月1日凌晨1点执行 
    Interval =>TRUNC(LAST_DAY(SYSDATE))+1+1/24
    每季度定时执行 
    例如每季度的第一天凌晨1点执行 
    Interval => TRUNC(ADD_MONTHS(SYSDATE,3),'Q') + 1/24
    每半年定时执行 
    例如：每年7月1日和1月1日凌晨1点 
    Interval => ADD_MONTHS(trunc(sysdate,'yyyy'),6)+1/24
    每年定时执行 
    例如：每年1月1日凌晨1点执行 
    Interval =>ADD_MONTHS(trunc(sysdate,'yyyy'),12)+1/24



-- ===============================================================
-- 带参存储过程的定时任务
定时任务：

----功能说明：每日凌晨0点定时执行存储过程 
-- 输出参数

    declare  job_use_daily number;    
    begin    
        dbms_job.submit(
            job_use_daily,
            'declare retCode  number; retMsg varchar2(200); begin  pro_test (retCode,retMsg); end;',
            sysdate,
            'trunc(sysdate)+1'
        );
        commit;    
    end;

-- =============================
-- 输入参数
    DECLARE
        X NUMBER;
    BEGIN
        SYS.DBMS_JOB.SUBMIT (
            job       => X ,
            what      => 'SP_MAKE_AP030
            (sysdate-3/* 输入参数1 DATE */ ,
            sysdate/*输入参数2 DATE */ ,
            0 /* 输入参数3 NUMBER */ ,
            ''SYSTEM''/* 输入参数4 VARCHAR2 */  );',
            next_date => to_date('22/12/2009 06:00:00','dd/mm/yyyy hh24:mi:ss'),
            interval  => 'trunc(SYSDATE+1)+1/4',
            no_parse  => FALSE
        );
        SYS.DBMS_OUTPUT.PUT_LINE('Job Number is: ' || to_char(x));
        COMMIT;
    END;








