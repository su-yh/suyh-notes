package com.tedu.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tedu.jdbc.JdbcUtils;

public class StatementBatch {
    public static void main(String[] args) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            state = conn.createStatement();
            // ����SQL ���(����������)
            // >> ����ЩSQL ��ӵ�һ����������
            state.addBatch("create table batch(id int primary key auto_increment, name varchar(50))");
            state.addBatch("insert into batch values(null, 'test1')");
            state.addBatch("insert into batch values(null, 'test2')");
            state.addBatch("insert into batch values(null, 'test3')");
            state.addBatch("insert into batch values(null, 'test4')");
            state.addBatch("insert into batch values(null, 'test5')");

            // >> ��������һ���Է��͸�������ִ��
            state.executeBatch();
            System.out.println("ִ�����");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, state, rs);
        }
    }
}
