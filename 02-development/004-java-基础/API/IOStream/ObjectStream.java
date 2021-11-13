对象流
	读写对象的流: 
		ObjectOutputStream 	- 写
		ObjectInputStream	- 读

	构造: 
		ObjectOutputStream(OutputStream out);
		e.g. 
		
		写对象：
			// 要调用此方法，参数对象，必须 implements Serializable接口，但不用重写任何抽象方法
			protected  void writeObjectOverride(Object obj); // 子类用于重写默认 writeObject 方法的方法。


			public static void demo01() throws IOException
			{
				Person person = new Person(1, "aa");
				
				FileOutputStream fos = new FileOutputStream("person.txt");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(person);
				
				oos.close();
				fos.close();
				
			}

			// 必须 implements Serializable 但不用重写任何抽象方法
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

	构造: 
		ObjectInputStream(InputStream in);
			protected  Object readObjectOverride() 

			public static void demo02() throws IOException, ClassNotFoundException {
				FileInputStream fis = new FileInputStream("person.txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				try {
					Person person = (Person)ois.readObject();
					System.out.println(person);
				} catch (EOFException e) {
					System.out.println("读到文件尾");
				} catch (Exception e) {
					System.out.println("其他异常");
				}
				
				
				ois.close();
				fis.close();
			}
