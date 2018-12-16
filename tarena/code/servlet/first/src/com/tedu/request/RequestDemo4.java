package com.tedu.request;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Request ʵ������ת��
 * @author suyh
 *
 */
public class RequestDemo4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RequestDemo4.doGet()");
		
		response.getWriter().write("demo4...");
		// ����ڵ���forward ֮ǰ����write ������ǿ��ˢ�£���forward ����ʧ��
		// response.flushBuffer(); -- ǿ��ˢ����ᵼ�������forward ʧ�ܡ�

		// ������ת����RequestDemo5 ������ // ת��ǰ���Ƚ�response �������������ա���"demo4..." �����ᱣ����response �Ļ�������
		request.getRequestDispatcher("/RequestDemo5").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
