package com.tedu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��������������봦��
 * ������Ӧ�������봦��
 * @author suyh
 *
 */
public class ServletDemo3 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ��ȡ�������
        String username = request.getParameter("username");
        
        System.out.println("username: " + username);
        // ������Ӧ
        response.getWriter().write("���..Filter");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
