package com.tedu.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.ws.ResponseWrapper;

import com.tedu.common.HttpContext;
import com.tedu.common.ServerContext;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.jdbc.JdbcUtils;

public class ClientHandler implements Runnable {
	private Socket socket;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			/*
			 * ����HttpRequest ����
			 *  ���ڷ����������͵�������Ϣ
			 */
			HttpRequest request = new HttpRequest(in);
						
			// ����������Ϣ�еĵ�һ��(��������Ϣ)
			// GET /index.html HTTP/1.1
			if (request.getUri() != null) {
				
				/*
				 * ����HttpResponse �������ڷ�װ
				 *  Http ��Ӧ��Ϣ
				 */
				HttpResponse response = new HttpResponse(out);
	
				/* ���uri ����/Regist ��ͷ��������ע������
				 * ���uri ����/Login ��ͷ�������ǵ�¼����
				 */
				if (request.getUri().startsWith("/Regist") ||
						request.getUri().startsWith("/Login")) {// ע����¼����
					service(request, response);
					return;
				}
				
				
				File file = new File(ServerContext.webRoot
						+ request.getUri());
				
				int statusCode = HttpContext.STATUS_CODE_OK;
				/*
				 * ���������������Դ�Ƿ���ڣ����������
				 *  ��Ӧ404 ҳ�棬��ʾ��Դ�Ҳ�����
				 */
				if (!file.exists()) { // ��Ӧ404 ҳ��
					file = new File(ServerContext.webRoot + "/" + ServerContext.notFoundPage);
					// ����״̬��Ϊ404
					statusCode = HttpContext.STATUS_CODE_NOT_FOUND;
				}
				
			
				// ��response �����е����Խ��г�ʼ��
				response.setProtocol(
						request.getProtocol());// Э��
				response.setStatus(statusCode);// ״̬��
				response.setContentType(getContentTypeByFile(file));// ��������
				response.setContentLength(
						(int)file.length());// ���ݳ���
				
				responseFile(response, file);
			}

			socket.close();
			System.out.println("��Ӧ�ͻ������");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ע����ߵ�¼����
	 * @param request
	 * @param response
	 */
	private void service(HttpRequest request, HttpResponse response) {
		if (request.getUri().startsWith("/Regist")) {
			System.out.println("����ע������...");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			System.out.println("username: " + username);
			System.out.println("password: " + password);
			
			// ͨ��JDBC �������û��������뱣�浽���ݿ���
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				conn = JdbcUtils.getConnection();
				String sql = "insert into user values(null, ?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.executeUpdate();
				
				// ��Ӧ�����(��ʾ�û�ע��ɹ�)
				File file = new File(
						ServerContext.webRoot + "/reg_success.html");
				response.setProtocol(ServerContext.protocol);
				response.setStatus(HttpContext.STATUS_CODE_OK);
				response.setContentType(getContentTypeByFile(file));
				response.setContentLength((int)file.length());
				responseFile(response, file);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.close(conn, ps, rs);
			}
		} else {
			// TODO: �����¼����
			System.out.println("�����¼����...");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			System.out.println("username: " + username);
			System.out.println("password: " + password);
			
			// ͨ���û����������ѯ�Ƿ����
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				conn = JdbcUtils.getConnection();
				String sql = "select * from user where username=? and password=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				rs = ps.executeQuery();
				if (rs.next()) {// ��ʾ��¼�ɹ�
					// ��Ӧ�����(��ʾ�û�ע��ɹ�)
					File file = new File(
							ServerContext.webRoot + "/log_success.html");
					response.setProtocol(ServerContext.protocol);
					response.setStatus(HttpContext.STATUS_CODE_OK);
					response.setContentType(getContentTypeByFile(file));
					response.setContentLength((int)file.length());
					responseFile(response, file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.close(conn, ps, rs);
			}
		}
	}

	/**
	 * �����ļ��ĺ�׺����ȡ��Ӧ����Ӧ��������
	 * @param file
	 * @return
	 */
	public String getContentTypeByFile(File file) {
		/* ˼·: 
		 * 1. ��ȡ�ļ������֣���: index.html
		 * 2. �����ļ������н�ȡ����ȡ��׺�� ��: html
		 * 3. ���ݺ�׺����types �л�ȡ ��Ӧ���ݵ����Ͳ�����
		 */
		
		// 1. ��ȡ�ļ�������
		String fileName = file.getName();
		// 2. �����ļ������н�ȡ����ȡ��׺��
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 3. ���ݺ�׺����types �л�ȡ��Ӧ��������
		String type = ServerContext.types.get(ext);
		System.out.println(ext + ": " + type);
		return type;
	}
	
	/**
	 * ��ָ���ļ���Ӧ���ͻ���
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public void responseFile(HttpResponse response, File file) throws IOException {
		PrintStream ps = new PrintStream(response.getOutputStream());

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] buff = new byte[(int) file.length()];
		bis.read(buff);
		ps.write(buff);
	}
}
