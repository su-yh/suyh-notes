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
	
	
	




	
	