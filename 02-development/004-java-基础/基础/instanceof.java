instanceof

引用变量 instanceof  type
判断某一个引用变量所指的对象是否为后面那个type 的实例

Person p = new Person();

p instanceof Person - boolean

	String a = "string";
	if (a instanceof String) {
		System.out.println("a is String type.");
	} else {
		System.out.println("a is not String type.");
	}

Person p = new Student();
bool b1 = p instanceof Student;	// true




