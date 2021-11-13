

-- NO_DATA_FOUND 异常判断与处理
    Begin
        --可能出现异常
       SELECT pid INTO temp_pid FROM t_person WHERE ID_CARD_NUMBER='1112233xxxxxxxx2323';
    Exception when no_data_found then
        --处理逻辑
        DBMS.OUTPUT_PUTLINE("没有找到该身份证对应的用户！")；
    End;

-- 在循环中的异常处理
    Begin
        --可能出现异常
        for  rec_person_id in（select pid from t_person）loop
            --处理逻辑（可能出现no_data_found异常）
        end loop;
    Exception when no_data_found then
        --处理逻辑
        DBMS.OUTPUT_PUTLINE("没有数据")；
    End;



