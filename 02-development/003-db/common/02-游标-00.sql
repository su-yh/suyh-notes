
-- SQL 中的游标，我的理解它就是一个暂时存放查询结果集

-- 游标的使用步骤：
-- 1. 声明游标
    -- 静态游标
        DECLARE cursor_name CURSOR FOR --定义游标
            SELECT TOP 10 UserId,UserName FROM UserInfo
            ORDER BY UserId DESC
-- 2. 打开游标
    OPEN cursor_name;
-- 3. 读取游标中的数据，使用 FETCH
    -- 抓取下一行数据
        FETCH NEXT FROM cursor_name INTO  @UserId,@username
    -- 
        EXIT WHEN C_INTF%NOTFOUND;
    
-- 4. 关闭游标
    CLOSE cursor_name --关闭游标
-- 5. 释放游标
    DEALLOCATE cursor_name;











