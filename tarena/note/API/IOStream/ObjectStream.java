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
				private static final long serialVersionUID = -5229031435203202100L;
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
