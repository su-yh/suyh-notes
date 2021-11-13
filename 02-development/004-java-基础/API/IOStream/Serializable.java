对象的序列化
	Serializable 接口: (没有任何内容) 仅仅标志该对象是可序列化的
		如果一个对象想实现序列化，那么这个对象的类一定要实现此接口，不需要重写任何的抽象方法。这个接口比较特殊，内部没有任何抽象方法。它仅仅标志该对象是可序列化的。
		当一个类实现了 Serializable 接口后，编译器会提示应当添加一个常量: serialVersionUID, 这个常量 标识当前类的序列化版本号。若不指定，编译器在编译当前类时会根据当前类的结构生成，但是只要类的结构发生了变化，那么版本号就会改变。版本号决定着一个对象是否可以反序列化成功。所以需要给实现Serializable 的类生成一个版本号。
		当一个对象是可序列化时，如果内部的成员是别的对象，那么，作为成员一定也需要实现序列化接口。
		transient 关键字：
			当一个属性被transient 修饰后，该属性在进行对象序列化时其值会被忽略。

	将对象序列化后写到文件中的内容为：对象的类型，类的项目信息，包信息，类信息，成员数据，其他信息。
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