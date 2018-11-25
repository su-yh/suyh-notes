package com.tedu.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.jdbc.JdbcUtils;

// ͨ��PreparedStatement ������ʵ��������
public class PsBatch {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            long begin = System.currentTimeMillis();
            
            conn = JdbcUtils.getConnection();
            System.out.println("�Ƿ��Զ��ύ��" + conn.getAutoCommit());
            // ���Զ��ύ����ر�
            conn.setAutoCommit(false);
            
            String sql = "insert into batch values(null, ?)";
            ps = conn.prepareStatement(sql);
            // ���ò���(ʹ��������)
            for (int i = 0; i < 100; ++i) {
                ps.setString(1, "zhangsz" + (i + 1));
                ps.addBatch();  // ��SQL ��ӵ���������
            }
            
            ps.executeBatch();
            
            // ������SQL ִ����ɺ���һ���ύ
            conn.commit();
            
            System.out.println("ִ�����");
            
            long end = System.currentTimeMillis();
            System.out.println("����ʱ��: " + (end - begin));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, ps, rs);
        }

    }
}
