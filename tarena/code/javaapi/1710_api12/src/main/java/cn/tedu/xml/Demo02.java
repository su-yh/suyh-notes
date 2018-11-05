package cn.tedu.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Demo02 {
    public static void main(String[] args) throws IOException {
        // ����XML DOC ���󣬲�д���ļ���
        Document doc = DocumentHelper.createDocument();

        // ��Ӹ�Ԫ��
        Element root = doc.addElement("products");
        // addElement �ڵ�ǰԪ���������Ԫ��
        // "product" ����Ԫ������������Ԫ�ض���
        Element p1 = root.addElement("product");
        Element p2 = root.addElement("product");
        // ΪԪ���������
        p1.addAttribute("id", "p1");
        // ΪԪ������ı�
        p1.addElement("name").addText("������");

        // ��doc ����д���ļ���
        FileOutputStream out = new FileOutputStream("products.xml");
        // Dom4j �ṩ �ˡ�Ư���ġ���ʽ ������ 
        OutputFormat fmt = OutputFormat.createPrettyPrint();
        
        XMLWriter writer = new XMLWriter(out, fmt);
        writer.write(doc);

        System.out.println(doc.asXML());
        
        writer.close();
        out.close();
    }
}
