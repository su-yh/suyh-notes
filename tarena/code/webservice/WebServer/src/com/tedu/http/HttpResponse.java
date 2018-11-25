package com.tedu.http;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.tedu.common.HttpContext;

/**
 * ���ڱ�ʾHTTP ��Ӧ��Ϣ
 */
public class HttpResponse {
	private String protocol;// Э�鼰�汾 
	private int status;// ״̬��
	private String contentType;// ��Ӧ���ݵ�����
	private int contentLength;// ��Ӧ���ݵĳ���
	
	// �Ƿ��͹�״̬�к���Ӧͷfalse ��ʾû�з���
	private boolean hasPrintHeader = false;
	
	// �����洢���� ��״̬���������Ϣ
	Map<Integer, String> statusMap;
	
	// ָ��ͻ��˵��������������ͻ��� ��������
	private OutputStream out;
	/** ���캯�������ڽ���ָ��ͻ��˵������
	 */
	public HttpResponse(OutputStream out) {
		this.out = out;
		// ��statusMap ���г�ʼ��
		statusMap = new HashMap<Integer, String>();
		statusMap.put(HttpContext.STATUS_CODE_OK, HttpContext.STATUS_REASON_OK);
		statusMap.put(HttpContext.STATUS_CODE_NOT_FOUND, HttpContext.STATUS_REASON_NOT_FOUND);
		statusMap.put(HttpContext.STATUS_CODE_ERROR, HttpContext.STATUS_REASON_ERROR);
	}
	/** ���ڷ���ָ��ͻ��˵������
	 * @return OutputStream
	 */
	public OutputStream getOutputStream() {
		/* �ڻ�ȡָ��ͻ��������֮ǰ���Ƚ�
		 *  ״̬�к���Ӧͷ���͸������.
		 */
		if (!hasPrintHeader) {// �����û�з��͹�
			PrintStream ps = 
					new PrintStream(this.out);
			// ����״̬����Ϣ(HTTP/1.1 200 OK)
			ps.println(protocol + " " + status + " " + statusMap.get(status));
			// ������Ӧͷ��Ϣ(Content-Type/content-Length)
			ps.println("Content-Type:" + contentType);
			ps.println("Content-Length:" + contentLength);
			// ����һ������
			ps.println("");
			
			hasPrintHeader = true;//��ʾ�Ѿ����͹���
		}
		
		return out;
	}
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
}
