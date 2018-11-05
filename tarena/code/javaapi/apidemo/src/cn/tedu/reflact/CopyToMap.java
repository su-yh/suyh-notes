package cn.tedu.reflact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CopyToMap {
	public static void main(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Student student = new Student(1, 11, "aa");
		Map<String, Object> map = new HashMap<>();

		copy(student, map);
		System.out.println(map);
	}

	public static void copy(Object obj, Map<String, Object> map)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * ʵ�ִ�obj �и������ݵ�map ��
		 */
		Class cls = obj.getClass();
		// ���Ҹ���������get ����
		Method[] methods = cls.getDeclaredMethods();
		// �ж��Ƿ���get ����
		for (Method m : methods) {
			String name = m.getName();
			if (name.startsWith("get")) {
				// ��ȡname �õ�key
				name = name.substring(3);
				String first = name.substring(0, 1).toLowerCase();
				String last = name.substring(1);
				name = first + last;
				// ִ�з����õ�val
				Object val = m.invoke(obj);
				map.put(name, val);
			}
		}
	}
}
