package cn.tedu.exception;

public class Person {
	private int id;
	private String name;
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("��Person ������ص���Դ�ͷš�");
	}
}
