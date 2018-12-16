package com.tedu.response;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// response ʵ�������ض���
// response ʵ�ֶ�ʱˢ��
public class ResponseDemo2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("ResponseDemo2.doGet()");
	    
	    // location(request, response);
	    refresh(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void refresh(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
         * response ʵ�ֶ�ʱˢ��
         */
	    response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write("3��֮����ת����ҳ.");
	    // 3 ��֮����ת
	    response.setHeader("refresh", "3;url=" + request.getContextPath() + "/index.html");
	}
	protected void location(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /*
         * response ʵ�������ض���
         */
        // ����302 ״̬��
        // response.setStatus(302);
        // ����location ��Ӧͷ
        // response.setHeader("location", request.getContextPath() + "/index.html");
        
        response.sendRedirect(request.getContextPath() + "/index.html");
           
	}
}
