String �ǲ��ɱ�Ķ��󣬱������ַ�����

String ����Ĵ��� - 2�ַ���
	����һ��
		String str = new String();
		String str = new String("abc");

	��������
		// "" ˫�����������Ľ��ַ���ֱ����������ֵ
		// ����Ĵ洢λ�ã��ַ���������
		// -- JDK 1.7 ֮ǰ�ڷ�������֮���ڶ���
		// �ַ��������ر����ַ���ֱ�Ӻ�
		// 	 �ص㣺�ַ����������б�������ַ���ֱ����������Ķ�����Ψһ���ڵ�
		//		����String ֱ��������ʱ��ȥ�����ֵ�Ƿ���ڣ��������ֱ��������ָ����������������򴴽�
		String str = "hello";	// ������һ��String ����
		
		String s1 = new String("abc");
		String s2 = "abc";
		assert(s1 != s2);

		// ���ܴ���2 ������
		//	1. "abc" �ַ�������
		// 	2. new String()
		String str = new String("abc");

matches(String regex): �Դ���ʼ�ͽ�βƥ�书��

split(String regex): 
	��ָ���ַ�������regex ���з���
	regex: �ָ���
	
	������
		��һ���ַ������зָ�ָ��Ϊ',' ���϶���ո�
		
	String str = "java, php,  c++,   end";
	�ָ����",\\s*"	���ź�������пհ��ַ�

replaceAll(String regex, String replacement)
	ʹ�ø����� replacement �滻���ַ�������ƥ�������������ʽ�����ַ�����
	
replaceFirst(String regex, String replacement)
	�滻��һ��
