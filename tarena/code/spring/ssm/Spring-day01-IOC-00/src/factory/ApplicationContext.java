package factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

// ���󣺹���ApplicationContext ����ʱ����ȡָ���ļ�(xml)���������ô�������
// ����������Ϣ�洢��map, ��Ҫ����ʱ����map ��ȡ
public class ApplicationContext {

	private static Map<String, Object> beanMap = new HashMap<String, Object>();
	
	public ApplicationContext(String file) {
		init(file);
	}

	private void init(String file) {
		// 
		InputStream in = getClass().getClassLoader().getResourceAsStream(file);
		SAXReader sr = new SAXReader();
		
		Document doc = null;
		try {
			doc = sr.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> le = root.elements("bean");
		for (Element e : le) {
			String strId = e.attributeValue("id");
			String strClass = e.attributeValue("class");
			Object obj = null;
			try {
				obj = Class.forName(strClass).newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			beanMap.put(strId, obj);
		}
		
		Set<Entry<String, Object>> s = beanMap.entrySet();
		for (Entry<String, Object> e : s) {
			System.out.println(e.getKey() + " - " + e.getValue());
		}
	}
	
	public Object getBean(String key) {
		return beanMap.get(key);
	}
	
	public <T> T getBean(String key, Class<T> cls) {
		return (T)beanMap.get(key);
	}
	
	
	public void close() {
		beanMap.clear();
		beanMap = null;
	}
}
