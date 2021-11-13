# dom4j 
- dom4j 是用来解析XML 文件的
- dom4j 的使用
	1. 需要导入两个jar 包
		> `dom4j-1.6.jar`<br>
		> `xml-apis-1.0.b2.jar`<br>
	2. 创建 SAXReader 对象，通过该对象解析XML文件

			import org.dom4j.Document;
			import org.dom4j.DocumentException;
			import org.dom4j.Element;
			import org.dom4j.io.SAXReader;

			SAXReader sr = new SAXReader();

			Document doc = null;
			try {
				// 解析XML 文件
				doc = sr.read(in);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			// 获取根元素
			Element root = doc.getRootElement();
			// 获取子元素
			List<Element> le = root.elements("bean");
			// 遍历元素
			for (Element e : le) {
				// 获取元素属性
				String strId = e.attributeValue("id");
				String strClass = e.attributeValue("class");
			}


