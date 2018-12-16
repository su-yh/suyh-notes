package com.tedu.response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDemo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 useOutputStream(request, response);
	    
//	    useWriter(request, response);
	}
	
	private void useOutputStream(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    // response.getWriter().append("Served at: ").append(request.getContextPath());
        // 1. ʹ���ֽ�����ͻ��˷�������   getOutputStream
        // 1). ����Ӣ������
        response.getOutputStream().write("hello..response.<br />".getBytes());
        // 2). ������������
        // >> ��ȷָ����������ʱ�ı���ΪUTF-8
        response.getOutputStream().write("���..response".getBytes("utf-8"));
        // >> ��ȷָ�������Ҳʹ��UTF-8�������շ���������
        // response.setHeader("Content-Type", "text/html;charset=utf-8");
        response.setContentType("text/html;charset=utf-8");
	}
	
	private void useWriter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    // 2. ʹ���ַ�����ͻ��� ��������   getWriter
	    // >> ֪ͨ������ʹ�� UTF-8 ����������
	    // response.setCharacterEncoding("utf-8");
        // 1). ����Ӣ������
	     response.getWriter().write("hello..response<br />");
        // 2). ������������
	    
	    
	    
	    
	    response.getWriter().write("���..response");
	    response.setContentType("text/html;charset=utf-8");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
