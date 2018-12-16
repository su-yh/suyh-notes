package com.tedu.request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestDemo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. getRequestURL();
		StringBuffer url = request.getRequestURL();
		String strUrl = url.toString();
		System.out.println("url: " + strUrl);	// http://localhost/first/RequestDemo1
		System.out.println("uri: " + request.getRequestURI());	// /first/RequestDemo1
		System.out.println("RequestDemo1.doGet()");	// syst   "alt+/  ��ݼ�"
		
		// 2. getMethod ��ȡ����ʽ
		String method = request.getMethod();
		System.out.println("method: " + method);
		
		// 3. getContextPath ��ȡWEB Ӧ�ö�����ʵ�����·��
		String strContextPath = request.getContextPath();
		System.out.println("context path: " + strContextPath);	//  /first
		
		// �����ض���
		// response.sendRedirect(strContextPath + "/index.html");
		
		// 4. getHeader() -- ��ȡ����ͷ
		String strHostHeader = request.getHeader("Host");
		System.out.println("host: " + strHostHeader);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
