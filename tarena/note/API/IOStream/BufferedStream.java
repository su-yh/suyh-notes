
�ֽڻ�����
	���������: BufferedOutputStream 

		����
		BufferedOutputStream(OutputStream out);
			ʾ��: 
				FileOutputStream fos = new FileOutputStream("bos.txt");
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write('a');
				bos.flush();
				bos.close();
				fos.close();

	������������BufferedInputStream
		����
		BufferedInputStream(InputStream in);
			ʾ��: 
				FileInputStream fis = new FileInputStream("fis.txt");
				BufferedInputStream bis = new BufferedInputStream(fis);
				...
				bis.close();
				fis.close();


##############################################################################################################################

�ַ�������
	��������� BufferedWriter PrintWriter
		PrintWriter Ҳ�ǻ����ַ�����������ڲ��������� BufferedWriter, ����֮�� PW ���ṩ ���Զ���ˢ�¹��ܡ����Ը����á�
		PrintWriter �ṩ��ֱ�Ӷ��ļ����в����Ĺ��췽��
			PrintWriter(File file) ʹ��ָ���ļ������������Զ���ˢ�µ��� PrintWriter��
			PrintWriter(File file, String csn /* => charSetName*/) ��������ָ���ļ����ַ����Ҳ����Զ�ˢ���µ��� PrintWriter��
			PrintWriter(String fileName) ��������ָ���ļ������Ҳ����Զ���ˢ�µ��� PrintWriter��
			PrintWriter(String fileName, String csn /* => charSetName*/) ��������ָ���ļ����ƺ��ַ����Ҳ����Զ���ˢ�µ��� PrintWriter��

		�ص㣺���Զ�ˢ��
			��PrintWriter �Ĺ��췽����һ������Ϊ��(�ֽ������ַ�������)ʱ����ô֧��һ�����صĹ��췽�����Դ���һ��boolean ֵ����ֵ��Ϊtrue, ��ǰ PrintWriter �����Զ���ˢ�¹��ܣ�����ÿ������ println ����д��һ�����ַ�������Զ�����flush ������д������Ҫע�⣬����print �����ǲ���flush ��

		˼������� PrintWriter ����ʱ���ɹ�ʹ�õĲ��������������󣬿ɲ�����ָ���ַ���?
			��PrintWriter �ṩ�Ĺ��췽����û���ṩ������ʱ����ָ���ַ����ġ����ǲ����������ǿ���ָ���ַ�����ġ�
			PrintWriter pw = new PrintWriter(
				new OutputStreamWriter(
					new FileOutputStream("fileName"), "UTF-8"));

	���������� BufferedReader
		readLine() ��������Ƕ�ȡһ���ı��������ı������һ����־�Ż��е��ַ������ᱻ���������������ᱻ������






















