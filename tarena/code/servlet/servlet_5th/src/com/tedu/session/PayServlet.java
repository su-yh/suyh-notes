package com.tedu.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ������֧������
 * @author suyh
 *
 */
public class PayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. ��ȡSession ����
        HttpSession session = request.getSession();
        
        // 2. ͨ��Session ��ȡ��Ҫ֧������Ʒ
        String prod = (String)session.getAttribute("prod");
        
        // 3. ����Ʒ���н���
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("��ϲ���ɹ�Ϊ" + prod + "��Ʒ֧��100$...");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
