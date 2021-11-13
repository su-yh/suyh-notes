// 简单示例


package com.tedu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class JdbcDemo01 {
	public static void main(String[] args) throws SQLException {
		// 1. 注册数据库驱动
		DriverManager.registerDriver(new Driver());
		
		// 2. 获取数据库连接 java.sql.Connection 而非 com.mysql.jdbc.Connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jt_db", "suyh", "suyunfei");
		
		// 3. 获取传输器 java.sql.Statement 而非com.mysql.jdbc.Statement
		Statement state = conn.createStatement();
		
		// 4. 利用传输器发送SQL 到数据库执行
		String sql = "select * from account";
		ResultSet rs = state.executeQuery(sql);
		
		// 5. 打印结果
		while (rs.next()) {
			// 获取一行数据
			// 按列获取数据时，下标从1 开始
//			int id = rs.getInt(1);
//			String name = rs.getString(2);
//			int age = rs.getInt(3);

			// 按表的字段名获取数据
			int id = rs.getInt("id");
			String name = rs.getString("name");
			double money = rs.getDouble("money");
			
			System.out.println("id: " + id + ", name: " + name + ", age: " + money);
		}
		
		// 6. 释放资源
		rs.close();
		state.close();
		conn.close();
	}
}



