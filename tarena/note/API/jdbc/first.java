// ��ʾ��


package com.tedu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class JdbcDemo01 {
	public static void main(String[] args) throws SQLException {
		// 1. ע�����ݿ�����
		DriverManager.registerDriver(new Driver());
		
		// 2. ��ȡ���ݿ����� java.sql.Connection ���� com.mysql.jdbc.Connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jt_db", "suyh", "suyunfei");
		
		// 3. ��ȡ������ java.sql.Statement ����com.mysql.jdbc.Statement
		Statement state = conn.createStatement();
		
		// 4. ���ô���������SQL �����ݿ�ִ��
		String sql = "select * from account";
		ResultSet rs = state.executeQuery(sql);
		
		// 5. ��ӡ���
		while (rs.next()) {
			// ��ȡһ������
			// ���л�ȡ����ʱ���±��1 ��ʼ
//			int id = rs.getInt(1);
//			String name = rs.getString(2);
//			int age = rs.getInt(3);

			// ������ֶ�����ȡ����
			int id = rs.getInt("id");
			String name = rs.getString("name");
			double money = rs.getDouble("money");
			
			System.out.println("id: " + id + ", name: " + name + ", age: " + money);
		}
		
		// 6. �ͷ���Դ
		rs.close();
		state.close();
		conn.close();
	}
}



