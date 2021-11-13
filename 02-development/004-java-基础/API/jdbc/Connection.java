

数据库连接 java.sql.Connection 而非 com.mysql.jdbc.Connection
Connection 连接对象
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jt_db", "suyh", "suyunfei");



传输器
Statement state = conn.createStatement();
Statement 返回能够向数据库发送SQL 的传送
preparseStatement 用于返回能够发送预编译SQL 的传输器对象。

executeQuery() - 执行查询类的SQL 语句
executeUpdate() - 执行增加、删除、更新的SQL 语句

preparseStatement 传输器
代码块：
		preparseStatement ps = null;
		try {
			String sql = "select * from user where username=? and password=?";
			ps = conn.prepareStatement(sql);
			// 设置参数的值
			ps.setString(1, "zhangsan");	// SQL 语句中参数下标位置，从1 开始
			ps.setString(2, "123");
			
			// 执行
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

结果集：
ResultSet
rs.next() - 获取下一条记录
rs.preview() - 获取上一条记录

批处理：
	batch
	1. 使用statement 对象批处理
		Statement state = null;
		state.addBatch(sql);
	
	2. 使用 PreparseStatement 对象批处理
		// MYSQL 默认开启事务，默认SQL 语句执行一条就提交一次
	    Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = JdbcUtils.getConnection();
        String sql = "insert into batch values(null, ?)";
        ps = conn.prepareStatement(sql);
        // 设置参数(使用批处理)
        for (int i = 0; i < 100; ++i) {
            ps.setString(1, "zhangsz" + (i + 1));
            ps.addBatch();  // 将SQL 添加到批处理中
        }
        
        ps.executeBatch();
		
		特点：
			1). 防止 SQL 注入攻击
			2). 效率更高
			3). 当执行的SQL 语句骨架相同时，SQL 语句只需写一次即可！同时骨架也不可以变更，除非重新写骨架SQL

	3. mysql 效率
		// 为了提高效率我们应该将自动提交给关闭，手动进行提交
		conn.setAutoCommit(false);
		// ... 
		conn.commit();

sql> show variables like 'char%';
-- mysql 不支持 '-' 所以没有 'UTF-8' 只有 'UTF8'
-- 将数据库的字符编码设置为GBK
sql> alter database jt_db charet=GBK;
-- 告诉服务器以gbk 编码接收数据并进行处理
-- 设置MYSQL 客户端，连接的处理编码
sql> set names GBK;








