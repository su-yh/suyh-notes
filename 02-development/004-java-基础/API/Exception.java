异常处理：Throwable 
	Throwable 类是 Java 语言中所有错误或异常的超类。只有当对象是此类（或其子类之一）的实例时，才能通过 Java 虚拟机或者 Java throw 语句抛出。类似地，只有此类或其子类之一才可以是 catch 子句中的参数类型。
	两个子类的实例，Error 和 Exception，通常用于指示发生了异常情况。通常，这些实例是在异常情况的上下文中新近创建的，因此包含了相关的信息（比如堆栈跟踪数据）。 
	其中 Exception 表示由于网络故障、文件损坏、设备错误、用户输入非法等情况导致的异常，而 Error 表示 Java 运行时环境出现的错误，例如：JVM 内存资源耗尽等。
	
异常的分类:
	Exception 分类
	Java 异常可分为可检测异常，非检测异常：
		- 可检测异常：可检测异常经编译器验证，对于声明抛出异常的任何方法，编译器将强制执行处理或者声明规则，不捕捉这个异常，编译器就通不过，不允许编译
		- 非检测异常：在运行期出现的异常(RuntimeException)。非检测异常不遵循处理或者声明规则。在产生此类异常时，不一定非要采取任何适当操作，编译器不会检查是否已经解决了这样一个异常。
		
		RuntimeException 类属于非检测异常
		IllegalArgumentException 抛出的异常表明向方法传递了一个不合法或不正确的参数
		NullPointerException 
		ArrayIndexOutOfBoundsException
		ClassCastException
		NumberFormatException 当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。

异常处理的两种方式
	注意点：
		如果一个方法内部抛出异常，程序执行到异常代码时会从方法体跳出。后面的代码不会执行。

	throws 向上抛出异常
	如果在定义某个方式 时，在方法声明的后面 throws 异常类型;

	try ... catch 当场捕获异常


派生类的异常处理
	* 如果父类中的某个方法抛出了某个异常，那么子类重写该方法时，可以不抛出任何异常，或者抛出父类方法中存在的部分异常。
	* 但是如果子类要抛出异常，那么所抛出的异常不能是父类中该方法抛出异常的任何父类，只能与该异常相同或者是其的子类。
	* 子类重写父类方法时，不可以抛出父类没有的异常。

finally 块: 
	finally 语句为异常处理提供一个统一的出口，使得在控制流程转到程序其它部分以前，能够对程序的状态作统一管理。
	无论 try 所指定的程序块中是否抛出异常, finally 所指定的代码都要被执行
	通常在 finally 语句中可以进行资源的释放工作，如关闭打开的文件、删除临时文件等。
	finally 块里面的代码会在 return 之前执行。

同时抛出多个异常
	int test(int a, int b) throws ArithmeticException, NullPointerException;

自定义异常<运行时异常>: 
	实现: 
		1、自定义类:  UserNotFoundException extends Exception / RuntimeException
		2、在自定义类中生成 serialVersionUID
		3、在自定义类中写出所有的构造方法

		try {

		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}




对象没有引用时被自动调用，用秋资源释放
Object::finalize();
	protected void finalize() throws Throwable;
	是GC 回收时会调用 的一个方法，是Object 类中的一个方法，通常会被子类重写。
		Person person = new Person();
		person = null;
		
		System.gc();	// 调用gc 将主动回收该对象的资源。即调用finalize() 方法

























































































