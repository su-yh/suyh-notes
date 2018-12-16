package com.tedu.cookie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieDemo3
 */
public class CookieDemo3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
	     * ɾ��Cookie
	     */
	    // ����Cookie, ָ��Cookie ������
	    Cookie cookie = new Cookie("time", "");
	    
	    // ����Cookie ��path(domain �������ã�Ĭ��һ��)
	    cookie.setPath(request.getContextPath() + "/");
	    
	    // ����Cookie �������ʱ��
	    cookie.setMaxAge(0);
	    
	    // 
	    response.getWriter().write("cookie delete success...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
