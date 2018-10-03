IO��
	������(InputStream) - �� �������(OutputStream) - д
	�ֽ������ַ���
	�ڵ�����������

	������ࣺ
		InputStream
		OutputStream

	�ļ�����
		0��ע���: 
			1��ʹ�ý������ر���. close();
			2��FileOutputStream û���ļ�ָ�룬�޷�����λ��ƫ��

		�����
			0��ע���: 
				3��������׷�ӷ�ʽ�����ļ�����ԭ�����ݽ��ᱻ�ض�(ɾ��)

			���죺
				���ļ��������򴴽��ļ�
				FileOutputStream(File file);
				FileOutputStream(File file, boolean append);
				FileOutputStream(String name);
				FileOutputStream(String name, boolean append);

		������
			0��ע��㣺
				3���������ڣ���ʧ��
			���죺
				FileInputStream(File file);
				...

	�ֽڻ�����
		���������: BOS: BufferedOutputStream 

			����
			BufferedOutputStream(OutputStream out);
				ʾ��: 
					FileOutputStream fos = new FileOutputStream("bos.txt");
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bos.write('a');
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

	��������л�
		Serializable �ӿ�: (û���κ�����) ������־�ö����ǿ����л���
			���һ��������ʵ�����л�����ô����������һ��Ҫʵ�ִ˽ӿڣ�����Ҫ��д�κεĳ��󷽷�������ӿڱȽ����⣬�ڲ�û���κγ��󷽷�����������־�ö����ǿ����л��ġ�

		���������л���д���ļ��е�����Ϊ����������ͣ������Ŀ��Ϣ������Ϣ������Ϣ����Ա���ݣ�������Ϣ��
		class Person implements Serializable
		{
			private int id;
			private String name;
			public Person(int id, String name) {
				super();
				this.id = id;
				this.name = name;
			}
		}


	������
		��д�������: 
			ObjectOutputStream 	- д
			ObjectInputStream	- ��

		����: 
			ObjectOutputStream(OutputStream out);
			e.g. 
			
			д����
				// Ҫ���ô˷������������󣬱��� implements Serializable�ӿڣ���������д�κγ��󷽷�
				protected  void writeObjectOverride(Object obj); // ����������дĬ�� writeObject �����ķ�����


				public static void demo01() throws IOException
				{
					Person person = new Person(1, "aa");
					
					FileOutputStream fos = new FileOutputStream("person.txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					
					oos.writeObject(person);
					
					oos.close();
					fos.close();
				}

				// ���� implements Serializable ��������д�κγ��󷽷�
				class Person implements Serializable
				{
					private int id;
					private String name;
					public Person(int id, String name) {
						super();
						this.id = id;
						this.name = name;
					}
				}

		����: 
			ObjectInputStream(InputStream in);
				protected  Object readObjectOverride() 

				public static void demo02() throws IOException, ClassNotFoundException {
					FileInputStream fis = new FileInputStream("person.txt");
					ObjectInputStream ois = new ObjectInputStream(fis);
					
					Person person = (Person)ois.readObject();
					System.out.println(person);
					
					ois.close();
					fis.close();
				}




























































