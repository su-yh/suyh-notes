package cn.tedu.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Demo01 {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        /**
         * ����Dom4j API ��ȡXML �ļ�
         */
        // books.xml λ������Ŀ�ļ�����
        String file = "books.xml";
        FileInputStream in = new FileInputStream(file);
        
        SAXReader reader = new SAXReader();
        // read ���������ж�ȡbyte, ���ҽ���Ϊ
        // Document ����(��Ϊ�ļ�����dom)
        Document doc = reader.read(in);
        // ����ĵ�����doc ������
//        System.out.println(doc.asXML());
        
        // �ҵ�Ψһ�ĸ�Ԫ��books
        Element root = doc.getRootElement();
//        System.out.println(root.asXML());
        
        // �ҵ�ȫ����books ��Ԫ��
        List<Element> list = root.elements("book");  // ����ȫ��book
//        root.elements(); // ��������book + owner
        for (Element e : list) {
//            System.out.println(e.asXML());
            Element ele = e.element("name");
            String test = ele.getText();
            System.out.println(test);
            
            // ��ȡbook Ԫ�ص�����
            String id = e.attributeValue("id");
            System.out.println(id);
        }
    }
}
