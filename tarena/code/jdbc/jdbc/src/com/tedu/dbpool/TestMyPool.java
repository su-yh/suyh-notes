package com.tedu.dbpool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tedu.jdbc.JdbcUtils;

public class TestMyPool {
    public static void main(String[] args) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;

        MyPool pool = new MyPool();

        try {
            // �����ӳ��л�ȡ����
            conn = pool.getConnection();
            state = conn.createStatement();
            String sql = "select * from account where id = 3";
            rs = state.executeQuery(sql);
            if (rs.next()) {
                System.out.println(rs.getInt("id") + ": " + rs.getString("name") + ": " + rs.getDouble("money"));
            }

        } catch (Exception e) {

        } finally {
            // ǧ���ס������ �������겻Ҫ�رգ�����Ҫ�������ӳ�
//            pool.returnConn(conn);
            // ���Ӳ��ܹأ�����������Ҫ�ر�
            JdbcUtils.close(conn, state, rs);
        }
    }
}
