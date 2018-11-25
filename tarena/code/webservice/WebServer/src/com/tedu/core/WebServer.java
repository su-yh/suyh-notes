package com.tedu.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tedu.common.ServerContext;

// ����HTTP Э���WEB ����˳���
public class WebServer {
	public static void main(String[] args) {
		WebServer svr = new WebServer();
		svr.start();
	}
	
	
	
	private ServerSocket server;
	private ExecutorService threadPool;
	
	public WebServer() {
		try {
			// ��ʼ��������8080 �˿�
			server = new ServerSocket(ServerContext.port);
			threadPool = Executors.newFixedThreadPool(ServerContext.maxThread);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����������ʧ��...");
		}
	}
	
	//��������˽��տͻ��˵����󲢴���
	private void start() {
		try {
			while (true) {
				System.out.println("�ȴ��ͻ�������");
				Socket socket = server.accept();
				
				// �����̴߳���ͻ��� ������
				threadPool.execute(new ClientHandler(socket));
				
				
				// ��������(��ͻ�����Ӧһ���ַ���)
//				OutputStream out = socket.getOutputStream();
//				out.write("hello 1790".getBytes());
//				out.flush();
//				System.out.println("hello 1790");
				
				
//				
//				String data = "hello 1709...";
//				// PrintStream ���ڰ�װ������������ṩ�����ݵĹ���
//				OutputStream out = socket.getOutputStream();
//				PrintStream ps = new PrintStream(out);
//				ps.println("HTTP/1.1 200 OK");
//				ps.println("Content-Type:text/html");
//				ps.println("Content-Length:" + data.length());
//				ps.println(""); // ����
//				ps.write(data.getBytes());
//				ps.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


