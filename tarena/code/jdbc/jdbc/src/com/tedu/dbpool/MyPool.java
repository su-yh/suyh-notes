package com.tedu.dbpool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.tedu.decorator.ConnDecorator;
import com.tedu.jdbc.JdbcUtils;

// 1. дһ����MyPool, ʵ��DataSource �ӿ�
public class MyPool implements DataSource {
    // 2. ����һ������(LinkedList), �������ӳأ����ڴ�����ݿ�����
    private static List<Connection> list = new LinkedList<>();

    // 3. �ṩһ����̬�飬�ڳ�������ʱ����ʼ��һ�����ӷ������ӳ��й���
    static {
        for (int i = 0; i < 5; ++i) {
            Connection conn = JdbcUtils.getConnection();
            list.add(conn);
        }
    }

    // 4. ʵ��getConnection ������������ȡ����
    @Override
    public Connection getConnection() throws SQLException {
        if (list.size() <= 0) {
            for (int i = 0; i < 5; ++i) {
                Connection conn = JdbcUtils.getConnection();
                list.add(conn);
            }
        }
        
        Connection conn = list.remove(0);
        
        Connection connDecorate = new ConnDecorator(conn, this);
        System.out.println("�ɹ���ȡһ�����ӣ����л�ʣ�����Ӹ�����" + list.size());
        return connDecorate;
    }

    // 5. �ṩһ��returnConn ���������������ӻ������ӳ���
    public void returnConn(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                list.add(conn);
                System.out.println("�ɹ�����һ�����ӣ����л�ʣ�����Ӹ�����" + list.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLoginTimeout(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
