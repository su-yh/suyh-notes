package com.tedu.response;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // ֪ͨ�������Ҫ���浱ǰ��Ӧ����Դ
//	    response.setDateHeader("Expires", -1); // 1970-01-01 + (-1)
//	    response.setHeader("Cache-Control", "no-cache");
	    
	    response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 3600 * 24);
	    response.setHeader("Cache-Control", "max-age=5");  // ���ȼ�����
	    
	    // ��Ӧ��ǰʱ���ַ����������
	    response.getWriter().write(new Date().toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
