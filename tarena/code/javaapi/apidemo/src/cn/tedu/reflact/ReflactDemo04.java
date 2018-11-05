package cn.tedu.reflact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflactDemo04 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// ������
		Class cls = Class.forName("cn.tedu.reflact.Student");
		// ��������
		Object stu = cls.newInstance();
		Method method = cls.getDeclaredMethod("demo");
		
		// ���÷���
		method.setAccessible(true);
		Object val = method.invoke(stu);
		System.out.println(val);
	}
}
