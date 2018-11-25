package com.tedu.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * �����洢�ͷ�������ص�һЩ ������Ϣ
 */
public class ServerContext {
	// ��ʾʹ�õ�Э�鼰�汾
	public static String protocol;
	// ��ʾ�����������Ķ˿�
	public static int port;
	// ��ʾ�̳߳صĴ�С
	public static int maxThread;
	// ��ʾ��������Ŷ��������Դ��Ŀ¼
	public static String webRoot;
	
	/* �����洢�����������Դ�ĺ�׺���Ͷ�Ӧ��
	 * ��Ӧ���ݵ�����
	 */
	public static Map<String, String> types;
	
	// ��ʾ404 ����ҳ��
	public static String notFoundPage;

	/**
	 * ��̬����飬�ڳ�������ʱ����server.xml �ļ��е����ݣ���ȡ���е�������Ϣ���� ����ı������г�ʼ��
	 */
	static {
		init();
	}

	private static void init() {
		try {
			/*
			 * ����server.xml �ļ�
			 */
			SAXReader reader = new SAXReader();
			// ���ý���������XML �ļ�������document ����
			Document dom = reader.read("config/server.xml");
			Element rootElement = dom.getRootElement();
			Element serviceElement = rootElement.element("service");
			Element connectorElement = serviceElement.element("connector");
			protocol = connectorElement.attributeValue("protocol");
			port = Integer.parseInt(connectorElement.attributeValue("port"));
			maxThread = Integer.parseInt(connectorElement.attributeValue("maxThread"));
			// �����������Դ��Ŀ¼
			// webRoot = serviceElement.element("webroot").getText(); //
			// ����������һ����һ����Ч��
			webRoot = serviceElement.elementText("webroot");
			
			// ��types ���г�ʼ��
			types = new HashMap<String, String>();
			List<Element> list = rootElement.element("type-mappings").elements();
			for (Element e : list) {
				// e ==> <type-mapping ext="ico" type="image/ico"/>
				types.put(e.attributeValue("ext"), e.attributeValue("type"));
			}
			
			// ����404ҳ��
			notFoundPage = rootElement.element("service").elementText("not-found-page");

			System.out.println("protocol: " + protocol + ", port: " + port + ", maxThread: " + maxThread + ", webRoot: "
					+ webRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
