java �������
	�����ڼ�������Ϣ��������֡����ԡ�����
	����ĺô�����δ���ĳ��򽵵����
	
	java.lang.reflect.Field;
	
	Class cls = obj.getClass();
	// ��ȡ������г�Ա����
	Field[] fs = cls.getDeclaredFields();

	
����Ķ�ִ̬��
	��̬�����ൽ�ڴ淽����
		Class.forName() �����ൽ������  ��Ҫָ��������·��
		Class cls = Class.forName("cn.tedu.reflact.Foo");
	
	��̬��������
		newInstance() - Class
		Object obj = cls.newInstance();
		
	��̬���÷���
		Object obj = cls.newInstance();
		Method[] methods = cls.getDeclaredMethods();
		
		// �������з������жϷ����Ƿ���test ��ͷ���������ִ�д˷���
		for (Method m : methods) {
			if (m.getName().startsWith("test")) {
				// ִ�д˷���
				Object val = m.invoke(obj);	// ����һ���÷������ڵĶ���, �������������б� ���ط����ķ���ֵ
			}
		}
	
	��̬���ò��ɷ��ʷ���
		private ���ε�
		�������
		
		�ڵ��÷���֮ǰ�ȵ��÷��� setAccessible �����ɷ��ʵ��������Ա�Ϊȫ�ɷ���
		method.setAccessible(true);
	
	
ʾ��: 
	����: java.util.Date  
	����: getTime
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please input class: ");
		String pkgcls = sc.nextLine();
		System.out.print("Please input method: ");
		String methodName = sc.nextLine();
		
		// 1. ���������
		Class<?> c = Class.forName(pkgcls);
		// 2. ��ȡ���з�������
		Method m = c.getDeclaredMethod(methodName);
		// 3. ִ����Ķ���ķ���
		Object obj = c.newInstance();
		
		Object result = m.invoke(obj);
		System.out.println(result);
		
		sc.close();
	}
	




	
	