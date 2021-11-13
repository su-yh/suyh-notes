-- 存储过程是没有返回值的(而函数是可以有返回值的)
-- 存储过程是属于某一个库的。它是保存在某一个数据库中的。并不是全局的。
-- 会话变量
	-- 一次会话过程中，可以设置一些变量保存数据
	-- set @name='张三' 
		-- @ 符号指会话变量
		-- @@ 表示全局变量，不能任意设置，指的是系统变量
		-- 全局变量也可以直接使用，不加@@ 也是一样的效果
-- 显示变量的值
	-- select @name;




-- 存储过程是属于数据库的，所以这个需要首先指定数据库。在指定的数据库中创建存储过程

-- 创建存储过程，使用输入/输出参数，声明变量，使用循环。
	-- 将语句的结束符号从分号(;)临时改为两个$$(可以是自定义)
	-- 一定要记得将结束符号还原为之前的分号(;)
	DELIMITER  $$

	CREATE PROCEDURE procSuyhTest(IN conf_no INT, OUT result INT)
	BEGIN
			-- 存储过程中声明变量只能在开始的地方，不能在中间进行变量的声明
			DECLARE i INT default 0;
			DECLARE num INT default 0;

			-- 变量的赋值需要使用 SET 关键字以及使用 := 这样一进行赋值
			SET num := 1;
			IF conf_no = num THEN
					INSERT INTO conf(note) VALUES ('conf_01');
			END IF;

			-- 存储过程中的循环
			WHILE i < 100 DO
				SET i := i + 1;
			END WHILE;

			-- 存储过程中也可以直接使用查询会有结果的。但是不知道通过API 的方式是否也可以得到这个结果
			SELECT i;

			-- 这里给OUT 参数赋值
			SET result := 1;
	END $$

	DELIMITER ;

	-- 调用这个存储过程
	SET @res = 0;
	CALL procSuyhTest(1, @res);
	SELECT @res;





-- 删除存储过程
	DROP PROCEDURE IF EXISTS procSuyhTest; -- 没有括号() 

-- 查看存储过程或者函数  (原始创建语句)
	查看存储过程：	SHOW   CREATE  PROCEDURE  procSuyhTest
	查看函数：			SHOW   CREATE  FUNCTION  procSuyhTest

-- 查看存储过程
	方法一：	select `name` from mysql.proc where db = 'your_db_name' and `type` = 'PROCEDURE';
	方法二：	show procedure status;



