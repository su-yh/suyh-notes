package cn.tedu.demo;

import java.util.ArrayList;
import java.util.Collection;

public class Demo01 {
	public static void main(String[] args) {
		demoAdd();
		demoContains();
		demoStudentTest();
	}

	public static void demoAdd() {
		// �������϶���
		Collection c = new ArrayList();
		// �򼯺������Ԫ��
		c.add(2);
		c.add("abc");
		System.out.println(c);
	}

	public static void demoContains() {
		Collection c = new ArrayList();
		c.add("java");
		c.add("php");
		c.add("c++");
		c.add(".net");
		
		String str = "java";
		boolean f = c.contains(str);
		System.out.println(f);
	}

	public static void demoStudentTest() {
		Collection c = new ArrayList();
		Student stu1 = new Student(1, "����", 23);
		Student stu2 = new Student(1, "����", 23);
		
		c.add(stu1);
		boolean bFlag = c.contains(stu2);
		System.out.println(bFlag);
		System.out.println(c);
	}
	
	public static void demo04() {
		// ���󣬼����в���������������ͣ�ֻ����������������
		// collection<int> col = ArrayList<int>();
		// ����
		Collection<String> col = new ArrayList<String>();
		
		col.add("1");
	}
}
