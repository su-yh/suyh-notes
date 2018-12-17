package com.tedu.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ��������������󣬽���Ʒ���빺�ﳵ(Session)�С�
 * 
 * @author suyh
 *
 */
public class BuyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. ��ȡ��Ʒ��Ϣ
        String prod = request.getParameter("prod");
        // prod = new String(prod.getBytes("iso8859-1"), "utf-8")); // GET
        // ������������

        // 2. ��ȡSession ����
        HttpSession session = request.getSession();
        // >> ����Cookie ���������ʱ��
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath(request.getContextPath() + "/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        // 3. ����Ʒ���빺�ﳵ(session ����)
        session.setAttribute("prod", prod);

        // 4. ��ʾ�û�
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("���ɹ���" + prod + " �ӵ����ﳵ");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
