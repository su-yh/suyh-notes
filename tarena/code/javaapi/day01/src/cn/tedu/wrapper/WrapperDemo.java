package cn.tedu.wrapper;

public class WrapperDemo {
	public static void main(String[] args) {
		// Integer ���ǵĳ��÷���
		
		// intValue(); - int
		Integer integer = new Integer(30);
		int i  = integer.intValue();
		System.out.println(i);
		 
		System.out.println(integer.getClass());
		
		String str = "23";
		System.out.println(str.getClass());
		Integer dest = Integer.parseInt(str);
		System.out.println(dest.getClass());
		System.out.println(dest);
	}
}
