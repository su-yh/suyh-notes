package com.tedu.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
    private JdbcUtils() {
    }

    public static Connection getConnection() {
        try {
            // ע������
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(prop.getProperty("jdbcUrl"), prop.getProperty("user"),
                    prop.getProperty("password"));

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Properties prop;

    static {
        // ��ȡconf.properties �ļ��е�������Ϣ
        prop = new Properties();
        String path = "conf.properties";
        try {
            prop.load(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete() {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;

        try {
            // ͨ��JdbcUtils �������ȡ����
            conn = JdbcUtils.getConnection();

            state = conn.createStatement();

            String sql = "delete from account where name='john'";
            int row;
            row = state.executeUpdate(sql);
            System.out.println("ɾ��Ӱ����" + row + "��");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, state, rs);
        }
    }

    public static void close(Connection conn, Statement state, ResultSet rs) {
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
