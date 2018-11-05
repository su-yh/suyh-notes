package cn.tedu.reflact;

import java.util.Scanner;

public class ReflactDemo03 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// ����Foo �� -- �������û��ӿ���̨����
		Scanner sc = new Scanner(System.in);
		System.out.println("����������: ");
		String className = sc.nextLine();
		
		Class cls = Class.forName(className);
		System.out.println(cls);
		
		// ����Foo ����
		Object obj = cls.newInstance();
		
		System.out.println(obj);
	}
}

class Foo {
	public void test() {
		System.out.println("Foo ���еĲ��Է���");
	}
}
