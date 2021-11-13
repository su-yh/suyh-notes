
-- IF 语句
    -- 单个
        if a=...  then
        .........
        end if;

    -- 分支

        if a=... then
            ......
        else
            ....
        end if;

    -- 多个分支

        if a=..  then
            ......
        elsif a=..  then
            ....
        end if;     

-- decode函数

    DECODE的语法：

    DECODE(value,if1,then1,if2,then2,if3,then3,...,else)
    表示如果value等于if1时，DECODE函数的结果返回then1,...,如果不等于任何一个if值，则返回else。


-- case when 类比switch case
    case when a='1'then 'xxxx'
        when a='2' then 'ssss'
    else
    　　'zzzzz'
    end case