package com.tedu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ڱ�ʾHTTP ������Ϣ
 */
public class HttpRequest {
	private String method;	 // ����ʽ
	private String uri;	// ������Դ·��
	private String protocol;	// Э�鼰�汾
	
	/*
	 * ������װ�����е����в�����Ϣ
	 * GET /Login?username=zzz&password=111 HTTP/1.1
	 */
	private Map<String, String> parameters;
	
	/*
	 * ���캯�������ڶ�method��uri��protocol ���г�ʼ��
	 */
	public HttpRequest(InputStream in) {
		try {
			BufferedReader br
				= new BufferedReader(
					new InputStreamReader(in)
				);	// ��װΪ�ַ���
			/*
			 * ��ȡ��������Ϣ
			 * (GET /index.html HTTP/1.1)
			 */
			String line = br.readLine();
			System.out.println(line);
			if (line != null 
					&& line.length() > 0) {
				String[] data = line.split("\\s");
				method = data[0];
				uri = data[1];
				if (uri.equals("/")) {//����Ĭ����ҳ
					uri = "/index.html";
				}
				protocol = data[2];
			}
			
			/*
			 * ��parameters ���г�ʼ��
			 * 1. �ж�uri �Ƿ�Ϊnull�����ж��Ƿ���� '?'
			 * 2. ͨ��'?' ����uri �����и��ȡ�������������ɵ��ַ���
			 * 3. ͨ��'&' �����и�: username=zzz&password=111
			 * 4. ͨ��'=' �����и�: username zzz, password 111
			 * GET /Login?username=zzz&password=111 HTTP/1.1
			 */
			parameters = new HashMap<String, String>();
			if (uri != null && uri.contains("?")) {
				String[] params = uri.split("\\?")[1].split("\\&");
				for(String param : params) {
					parameters.put(param.split("=")[0], param.split("=")[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getParameter(String name) {
		// username=zhangefei
		if (parameters == null) {
			return null;
		}
		
		return parameters.get(name);
	}
	
	public String getMethod() {
		return method;
	}
	public String getUri() {
		return uri;
	}
	public String getProtocol() {
		return protocol;
	}
	
	
}
