�ļ����� - File
	java.io.File ���ڱ�ʾ�ļ�(Ŀ¼)��Ҳ����˵����Ա����ͨ��File ���ڳ����в���Ӳ���ϵ��ļ���Ŀ¼��
	
	����File ����
	���췽��: 
		1. File(String pathName);
			·����Ӧ����ʹ�����·��������Ŀ¼�Ĳ㼶�ָ�����Ҫֱ��д"/"��"\" ��Ӧʹ��File.separator ���������ʾ���Ա��ⲻͬϵͳ�����Ĳ��졣
		2. File(File parent, String child);
		3. File(String parent, String child);

	ע��㣺
		����File ������ָ��ĳ��Ŀ¼�����ļ�����Ŀ¼�����ļ���û������������������

	����: 
		createNewFile();	�����ļ�������ļ��Ѵ����򴴽�ʧ�ܣ�����false��
			�����ļ�ʱ��Ծ�����ecplise �е����·��������Ե�ǰ��ĿĿ¼�µġ�
		isFile() - boolean;	�жϵ�ǰ�����Ƿ��ʾһ���ļ�
		isDirectory() - boolean; �жϵ�ǰ�����Ƿ��ʾһ��Ŀ¼
		length();	�ļ��ĳ���(ʵ���ֽ���)
		exists();	�ж�File �����ʾ���ļ�����Ŀ¼�Ƿ���� 
		delete();	��File �����ʾ���ļ�����Ŀ¼ɾ��
			���Ҫɾ��һ��Ŀ¼����ô��Ŀ¼�±���û�����ݲſ���ɾ�����������Ŀ¼�������ļ������޷�ɾ����
			������ֱ��ɾ���༶Ŀ¼���������ײ㿪ʼ��ɾ����

		mkdir();	����Ŀ¼
		mkdirs();	�����༶Ŀ¼
		
		list() - String[];	 ����һ���ַ������飬��Щ�ַ���ָ���˳���·������ʾ��Ŀ¼�е��ļ���Ŀ¼��
		listFiles() - File[]; 	����һ������·�������飬��Щ·������ʾ�˳���·������ʾ��Ŀ¼�е��ļ���
		listFiles(FileFilter filter); �ļ����ˣ�ͨ��ָ���������ӿڶ���
			private static void demo05() {
				File file = new File("D:" + File.separator);
				
				File[] files = file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File file) {
						// ��.txt ��β���ļ�
						boolean flag1 = file.isFile();
						boolean flag2 = file.getName().endsWith(".txt");
						return flag1 && flag2;
					}
				});
				
				if (files != null)
				{
					for (File f : files)
					{
						System.out.println(f);
					}
				}
			}
		listFiles(FilenameFilter filter): �ļ�������
			private static void demo06() {
				File file = new File("D:" + File.separator);
				File[] files = file.listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// dir: ��Ŀ¼��name: ���������ļ���
						File dest = new File(dir, name);
						// ��.txt ��β���ļ�
						return dest.isFile() && name.endsWith(".txt");
					}
				});
				
				if (files != null)
				{
					for (File f : files)
					{
						System.out.println(f);
					}
				}
			}


		getName(); ��ȡ�ļ���
		getPath(); ��ȡ��Ե�ǰ��������·��
		getAbsolutePath(); ��ȡ����·��
		


	·�������� - FileFilter
		����ĳ���������� ".jpg" ��β���ļ�

	��ʱ�ļ�: 
		static File createTempFile(String prefix, String suffix);
		static File createTempFile(String prefix, String suffix, File directory);
			����һ����ʱ�ļ���ָ���ļ���ǰ׺�Լ���׺
			File.createTempFile("test", ".tmp");
 

���룺
	�����������ļ����ǰ���byte(8λ��)�������ݴ���ģ�
	�ַ������ڻ�����(�ļ�)����ʱ�������Ϊbyte���д���
		- ���ַ����ݲ��Ϊbyte ���ݵĹ��̳�Ϊ"����"
		- ��byte �������ºϲ�Ϊ�ַ����ݹ��̳�Ϊ"����"
	��������
		- UTF16-BE: ���ַ�char ���м������Σ�����byte, ֻ��֧��65535 ���ַ���Ӣ���˷ѿռ�
		- GBK �й���׼��1~2�ֽڱ䳤���룬֧���ַ�2��+
		- UTF-8 ���ñ䳤����(1~4�ֽ�)��֧��100��+�ַ��������ַ���ֵ�Ĵ�С���б��룬Ӣ�Ĳ���1�ֽڱ��롣
			UTF-8 ���������Ϊ3 ���ֽ�
		> ���ʻ��ؼ�Ӧ�ò���UTF-8����

RandomAccessFile �ļ�����
	Java �ṩ ��һ�����Զ��ļ�������ʵĲ��������ʰ�������д�������� ����ΪRandomAccessFile������Ķ�д�ǻ���ָ��Ĳ�����
	ע��㣺
		ʹ�ý�������Ҫ�ر����� close();
		д�ļ���ʱ�����Ǵ��ļ���ʼλ�ã�����ļ�ԭ�Ѿ����ˣ�����滻������ԭ�е����ݲ��ᱻɾ����
	
	��������
		RandomAccess �ڶ��ļ�����������ʲ���ʱ������ ģʽ���ֱ�Ϊֻ��ģʽ(ֻ���ļ�����)���Ͷ�дģʽ(���ļ����ݽ��ж�д)��
		���췽��:
			RandomAccessFile(File file, String mode);
			RandomAccessFile(String filename, String mode);
				mode ����ָ�����Դ��ļ��ķ���ģʽ�������ֵ���京��Ϊ�� 

				ֵ				 ����
				 
				"r" 		��ֻ����ʽ�򿪡����ý��������κ� write �������������׳� IOException��  
				"rw" 		���Ա��ȡ��д�롣������ļ��в����ڣ����Դ������ļ���  
				"rws" 		���Ա��ȡ��д�룬���� "rw"����Ҫ����ļ������ݻ�Ԫ���ݵ�ÿ�����¶�ͬ��д�뵽�ײ�洢�豸��  
				"rwd"   	���Ա��ȡ��д�룬���� "rw"����Ҫ����ļ����ݵ�ÿ�����¶�ͬ��д�뵽�ײ�洢�豸��  

				"rws" �� "rwd" ģʽ�Ĺ�����ʽ�������� FileChannel ��� force(boolean) �������ֱ𴫵� true �� false ��������������ʼ��Ӧ����ÿ�� I/O �����������ͨ����Ϊ��Ч��������ļ�λ�ڱ��ش洢�豸�ϣ���ô�����ش����һ�������ĵ���ʱ�����Ա�֤�ɸõ��öԴ��ļ����������и��ľ���д����豸�����ȷ����ϵͳ����ʱ���ᶪʧ��Ҫ��Ϣ�ر����á�������ļ����ڱ����豸�ϣ����޷��ṩ�����ı�֤�� 
				"rwd" ģʽ�����ڼ���ִ�е� I/O ����������ʹ�� "rwd" ��Ҫ�����Ҫд��洢���ļ������ݣ�ʹ�� "rws" Ҫ�����Ҫд����ļ����ݼ���Ԫ���ݣ���ͨ��Ҫ������һ�����ϵĵͼ��� I/O ������ 

				������ڰ�ȫ����������ʹ�� file ������·������Ϊ������������� checkRead �������Բ鿴�Ƿ�����Ը��ļ����ж�ȡ���ʡ������ģʽ����д�룬��ô��ʹ�ø�·���������øð�ȫ�������� checkWrite �������Բ鿴�Ƿ�����Ը��ļ�����д����ʡ� 


		��д������
			д����:
				write(int b);
				write(byte[] bys);
				write(byte[] bys, int off, int len);

					RandomAccessFile raf = new RandomAccessFile("a.txt", "rw");
					raf.write(97);
					raf.write("hello world!".getBytes());
					raf.close();

			������: 
				read() - int
				read(byte[] b) - int

					private static void demo01() throws IOException {
						RandomAccessFile raf = new RandomAccessFile("a.txt", "rw");
						byte[] btRead = new byte[100];
						int nLen = raf.read(btRead);
						System.out.println(nLen);
						System.out.println(new String(btRead));

						raf.close();
					}

		�������: 
			getFilePointer() - long ���ش��ļ��еĵ�ǰƫ������
			seek(long pos) - void �����ļ���ǰƫ������





















































































































