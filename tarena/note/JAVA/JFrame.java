// ������

	JFrame frame = new JFrame("�ɻ���ս");
	World world = new World();
	
	frame.add(world);	// ��ĳ��������ӵ�������
	
	// �趨����Ĭ�Ϲر���Ϊ - ����������������ô�رմ��ں󣬳��򲻻������
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// ���ô��ڴ�С
	frame.setSize(400, 700);
	frame.setLocationRelativeTo(null);
	// �ô�����ʾ��Ĭ��JFrame �������� �ǲ���ʾ�����ģ���Ҫ��ʾ���á�
	frame.setVisible(true);
		