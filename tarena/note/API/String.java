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


���룺
	getBytes() - ʹ��Ĭ���ַ���(�������õ��ַ���)����ת��Ϊ�ֽ���

	String --> byte[] ����String::getBytes(Charset charset)����
		getBytes(Charset charset) - ʹ��ָ���ַ������ַ���ת��Ϊ�ֽ���
			e.g: "ʵ��׷��д�빦�ܣ�".getBytes("UTF-8");

	byte[] --> String ����String �Ĺ��췽��
		String(byte[] bytes, int offset, int length, Charset charset); ָ��ת���ַ���
			e.g: String str = new String(bys, 0, bys.Length, "UTF-8");

			public static void demo01() throws IOException
			{
				Person person = new Person(1, "aa");
				
				FileOutputStream fos = new FileOutputStream("person.txt");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(person);
				
				oos.close();
				fos.close();
			}












































