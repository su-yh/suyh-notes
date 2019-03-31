package cn.tedu.project.msg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.project.msg.entity.Message;
import cn.tedu.project.msg.service.MessageService;
import cn.tedu.project.msg.service.impl.MessageServiceImpl;

// ���Ʋ����
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MessageService ms = new MessageServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MessageController.doPost()");
		// 1. ��ȡ�����е�����
		String content = req.getParameter("content");
		// 2. �����ݽ���ת����װ
		Message msg = new Message();
		msg.setContent(content);
		// 3. �����ݴ���ҵ��㴦��
		ms.saveObject(msg);
		// 4. ���ؿͻ���һ����Ӧ
		req.getRequestDispatcher("/msg.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MessageController.doGet()");
		doPost(req,resp);
	}
}
