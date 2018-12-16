package com.tedu.cookie;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieDemo2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String strCurDate = new Date().toString();
	    
	    Cookie[] cookies = request.getCookies();
	    String strPreDate = "û��";
	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            if (c.getName().equals("time")) {
	                strPreDate = c.getValue();
	                break;
	            }
	        }
	    }
	    
	    Cookie cookie = new Cookie("time", strCurDate);
	    cookie.setMaxAge(3600);    // 1Сʱ = 3600
	    cookie.setPath(request.getContextPath() + "/");    // ����Cookie ����������·��
	    response.setContentType("text/html;charset=utf-8");
	    
	    response.addCookie(cookie);    // ***** ���cookie �����������ݣ���ô���ı��������ô������أ�
	    response.getWriter().write("���ϴε�¼��ʱ��: " + strPreDate);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
