package cn.tedu.reflact;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflactDemo01 {
	public static void main(String[] args) {
		test("aa");
//		test(1);
	}

	public static void test(Object obj) {
		System.out.println(obj);
		/*
		 * ���������жϳ�obj �������ʵ����
		 */
		Class cls = obj.getClass();
		System.out.println(cls);
		
		// ��ȡ��ĳ�Ա����
//		Field[] fields = cls.getDeclaredFields();
//		for (Field f : fields) {
//			System.out.println(f);
//		}
		
		// ��ȡ��ķ���
		Method[] methods = cls.getDeclaredMethods();
		for (Method m : methods) {
			System.out.println(m);
		}
	}
}
