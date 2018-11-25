package com.tedu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class JdbcDemo01 {
	public static void main(String[] args) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;

		try {
			// 1. ע�����ݿ�����
			// ����ʵ����������һ�㲻��ͨ�����ַ�ʽע��������
			// ��Ϊ���ַ�ʽ���������⣺1. ���ܻᵼ������ע�����Σ�2. �ᵼ�³���;�������ݿ�����������һ��
			// �������һ��ͨ��������ƣ�Class.forName("com.mysql.jdbc.Driver");
			// ���ַ�ʽ����ֻ��ע��һ�Σ���Ϊ��Դ���п��Կ�����Driver ����һ����̬����飬�����������ע��������
			// DriverManager.registerDriver(new Driver());
			Class.forName("com.mysql.jdbc.Driver");

			// 2. ��ȡ���ݿ����� java.sql.Connection ���� com.mysql.jdbc.Connection
			// ���ݿ��URL ����ָ��������һ��λ���ϵ����ݿ�������Լ����ݿ�����һ�����ݿ�
			// Э������jdbc:mysql
			// �������˿�: localhost:3306
			// ���ݿ���: jt_db
			// ��д��ʽ��jdbc:mysql:///jt_db
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jt_db", "suyh", "suyunfei");

			// 3. ��ȡ������ java.sql.Statement ����com.mysql.jdbc.Statement
			state = conn.createStatement();

			// 4. ���ô���������SQL �����ݿ�ִ��
			String sql = "select * from account";
			rs = state.executeQuery(sql);

			// 5. ��ӡ���
			while (rs.next()) {
				// ��ȡһ������
				// ���л�ȡ����ʱ���±��1 ��ʼ
				// int id = rs.getInt(1);
				// String name = rs.getString(2);
				// int age = rs.getInt(3);
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double money = rs.getDouble("money");

				System.out.println("id: " + id + ", name: " + name + ", age: " + money);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. �ͷ���Դ
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rs = null;
				}
			}
			if (state != null) {
				try {
					state.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					state = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					conn = null;
				}
			}
		}
	}
}
