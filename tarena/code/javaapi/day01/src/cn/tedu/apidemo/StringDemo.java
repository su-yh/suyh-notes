package cn.tedu.apidemo;

public class StringDemo {
	public static void main(String[] args) {
//		String s1 = "hello";
//		String s2 = "hello";
//		
//		/**
//		 * == 
//		 * ������߷ŵ�����ֵ���ͣ��Ƚϵ�����ֵ��ֵ�Ƿ����
//		 * ������߷ŵ����������ͣ���ʱ�Ƚϵ������������Ƿ�ָ��ͬһ������
//		 * 
//		 */
//		System.out.println(s1 == s2);
//		
//		String s3 = "world";
//		String s4 = "helloworld";
//		String s5 = "hello" + "world";
//		// s4 ��s5 �Ƿ�ָ��ͬһ������
//		System.out.println(s4 == s5);
//		
//		String s6 = s2 + s3;
//		System.out.println(s4 == s6);
		
		
		String str = "hhhhllo";
		int nIndex = str.lastIndexOf('l', 9);
		System.out.println(nIndex);
		nIndex = str.indexOf('l');
		System.out.println(nIndex);
	}
}
