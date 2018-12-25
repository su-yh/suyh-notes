package com.tedu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {

    /**
     * �÷���������ServletContext ��������ʱ����
     * ServletContext �����ڷ������Ƴ�WEB Ӧ��ʱ����
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("MyServletContextListener.contextDestroyed()   �������������...");
    }

    /**
     * �÷���������ServletContext ���󴴽�ʱ����
     * ServletContext �����ڷ���������WEB Ӧ��֮�󴴽�
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("MyServletContextListener.contextInitialized()   �����������...");
    }

}
