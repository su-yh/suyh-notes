Calendar
	��һ�������࣬�����������Բ�ͬ���ҵ�����ϵͳ������Ӧ����㷺����GregorianCalendar(�������������ͨ�õ�����), ��Ӧ�����Ͼ����������/����ʹ�õı�׼����ϵͳ��
	
	���ڰ���java.util
	
�õ�Calendar ����
	1): Calendar calendar = new GregorianCalendar();
	2):
		���þ�̬���� getInstance()
			getInstance �����ϵͳ��ǰʱ���������������(�п��ܻᴴ����ʱ����������)������һ�㴴���Ķ��� GregorianCalendar ����

Calendar ���ж�����һЩ�����ֶ�(�ֶ�ժҪ)
	ʹ��Calendar �ṩ�� get ������һЩ�������Ի�ȡ���ڼ�ʱ�����
	static int YEAR  ָʾ��ݵ��ֶ�����
	static int MONTH �·� : �� 0 ��ʼ
	static int DATE һ�����еĵڼ���
	static int DAY_OF_WEEK һ���ǵ�ĳ�죬 �� 1 ��ʼ

����: 
	get(int field);	// ������ֶξ�������� YEAR MONTH DATE DAY_OF_WEEK ��һ�ྲ̬�ֶ�
	getTime();	// ����һ��Date ����
	getTimeInMills();	// ��Date ���е�getTime ��һ����
	getActualMaximum(int field);

	set(int field, int value);
	setTime(Date date);

	add(int field, int amount);	// �Ը��������ֶ�ֵ���мӼ�������amount ������ֵ�����ɸ�


	