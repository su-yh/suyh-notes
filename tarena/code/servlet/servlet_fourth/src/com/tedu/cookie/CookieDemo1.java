package com.tedu.cookie;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieDemo1
 */
public class CookieDemo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // ��ȡ���η���ʱ��
	    String strCurDate = new Date().toString();
	    
	    // ͨ��Set-Cookie ��Ӧͷ��ʱ�䷢�͸����������
	    response.setHeader("Set-Cookie", "time = " + strCurDate);
	    
	    // ͨ��Cookie ����ͷ��ȡ�ϴη���ʱ��
	    String strPreDate = request.getHeader("Cookie");
	    // ��ʱ����Ϊ��Ӧ���ݷ��͸������������ʾ�û��ϴη��ʵ�ʱ��
	    response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write("���ϴη��ʵ�ʱ��: " + strPreDate);
	    
	    request.getRemoteAddr();   // ��ȡ�������IP ��ַ
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
