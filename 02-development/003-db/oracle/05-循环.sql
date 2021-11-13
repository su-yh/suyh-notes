
-- LOOP、EXIT 和 EXIT WHEN、END LOOP

-- 循环控制的三种类型：
    -- LOOP   -   基本循环
        LOOP
            sequence_of_statements
        END LOOP;
        -- 示例
        DECLARE
            v_count NUMBER(5):=1;
            v_num NUMBER(10):=100;
        BEGIN
            LOOP
                INSERT INTO T_TEST(T_TEST."id",T_TEST."num") VALUES(v_count,v_num);
                v_count:= v_count + 2;
                v_num:= v_num + 10;
                IF v_count > 10 THEN
                    EXIT; 
                END IF; 
            END LOOP;
                dbms_output.put_line('成功');
        END;

    -- WHILE  -  根据条件循环
        WHILE condition LOOP 
        sequence_of_statements
        END LOOP;
        -- 示例
        DECLARE
            v_count NUMBER(5):=11;
            v_num NUMBER(10):=200;
        BEGIN
            while v_count < 20
            LOOP
                INSERT INTO T_TEST(T_TEST."id",T_TEST."num")
                VALUES(v_count,v_num);
                v_count:= v_count + 3;
                v_num:= v_num + 10;
            END LOOP;
        END;

    -- FOR  - 固定次数的循环
        FOR  counter  IN [REVERSE] value1..value2   LOOP 
        sequence_of_statements
        END LOOP;
        -- 示例
        DECLARE
            v_count NUMBER(5):=20;
            v_num NUMBER(10):=300;
        BEGIN
            FOR v_count IN 20..30 LOOP
                INSERT INTO T_TEST(T_TEST."id",T_TEST."num")
                VALUES(v_count,v_num);
                v_num:= v_num + 10;
            END LOOP;
        END;
