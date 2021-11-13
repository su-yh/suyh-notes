## 线程的特点
- > JAVA 中每个线程都有一个自己的名字，可以使用 getName() 方法来获取

## 创建线程：
- ###  继承自 Thread 类
	- > 自定义类并继承自Thread 同时重写 `run` 方法
		> - 启动时调用 `start` 方法而非 `run`
		> - 示例代码
		>>		public class MyThread extends Thread{//继承Thread类
		>>		　　public void run(){
		>>		　　//重写run方法
		>>		　　}
		>>		}
		>>		public class Main {
		>>		　　public static void main(String[] args){
		>>		　　　　new MyThread().start();//创建并启动线程
		>>		　　}
		>>		}
	
- ### 实现 Runnable 接口
	- > 自定义类，实现 `Runnable` 接口 并实现`run` 方法
		> - 将此自定义类实例对象作为`Thread` 的参数
		> - 示例代码
		>>		public class MyThread2 implements Runnable {//实现Runnable接口
		>>		　　public void run() {
		>>			　　// 重写run方法
		>>		　　}
		>>		}
		>>		
		>>		public class Main {
		>>		　　public static void main(String[] args){
		>>		　　　　//创建并启动线程
		>>		　　　　MyThread2 myThread=new MyThread2();
		>>		　　　　Thread thread=new Thread(myThread);
		>>		　　　　thread().start();
		>>		　　}
		>>		}

- ### 使用Callable和Future创建线程
	- > Callable 
		> - 和Runnable接口不一样，Callable接口提供了一个call（）方法作为线程执行体
		>> - call()方法可以有返回值
		>> - call()方法可以声明抛出异常
	- > 代码示例
		> - 示例
		>>		public class Main {
		>>		　　public static void main(String[] args){
		>>		　　　MyThread3 th=new MyThread3();
		>>		　　　//使用Lambda表达式创建Callable对象
		>>		　　   //使用FutureTask类来包装Callable对象
		>>		　　　FutureTask<Integer> future=new FutureTask<Integer>(
		>>		　　　　(Callable<Integer>)()->{
		>>		　　　　　　return 5;
		>>		　　　　}
		>>		　　  );
		>>			 //实质上还是以Callable对象来创建并启动线程
		>>		　　　new Thread(task,"有返回值的线程").start();
		>>		　　  try{
		>>				//get()方法会阻塞，直到子线程执行结束才返回
		>>		　　　　System.out.println("子线程的返回值："+future.get());
		>>		 　　 }catch(Exception e){
		>>		　　　　ex.printStackTrace();
		>>		　　　}
		>>		　　}
		>>		}

## 三种创建线程方法对比

- ### 对比
	- > 实现Runnable和实现Callable接口的方式基本相同，不过是后者执行call()方法有返回值，后者线程执行体run()方法无返回值，因此可以把这两种方式归为一种这种方式与继承Thread类的方法之间的差别如下：
		> - 1、线程只是实现Runnable或实现Callable接口，还可以继承其他类。
		> - 2、这种方式下，多个线程可以共享一个target对象，非常适合多线程处理同一份资源的情形。
		> - 3、但是编程稍微复杂，如果需要访问当前线程，必须调用Thread.currentThread()方法。
		> - 4、继承Thread类的线程类不能再继承其他父类（Java单继承决定）。
	- > <mark>注：一般推荐采用实现接口的方式来创建多线程</mark>





### 线程操作API
- > 线程操作API
	>>		static Thread.currentThread() - Thread 获取当前线程对象
	>>		static Thread.getName() - String 获取当前线程名称
	>>		static Thread.sleep(long ms) - void 
	>>
	>>		static yield() - void 礼让
	>>			调用 yield() 方法，此时让当前线程并没有进入阻塞状态而是直接进入就绪状态，此时当前线程有可能会再次抢到时间片。
	>>
	>>		给线程起名字
	>>			setName(String name) - void
	>>			Thread(Runnable, String name);	构造方法
	>>
	>>		获取线程相关信息方法：
	>>			long getId(): 线程标识符
	>>			String getName();
	>>			Boolean isAlive();
	>>			int getPriority(); 	优先级
	>>			boolean isDaemon(); 守护线程
	>>			boolean isInterrupted(); 是否已中断


局部内部类调用方法的局部变量的注意点：
	main() {
		// final int a = 3; // 1.8 版本之前要使用局部变量必须是final 修饰的
		int a = 3;	// 1.8 版本就没有此限制
		class A {
			public void test() {
				System.out.println(a);
			}
		}
	}

线程优先级：
	线程的切换是由线程调度控制的，我们无法通过代码来干涉，但是我们可以通过提高线程的优先级来最大程度的改善线程获取时间片的几率。
	线程的优先级被划分为10 级，值分别是1-10 ，其中1最低，10 最高。线程提供了3 个常量来表示最低，最高，以及默认优先级：
		Thread.MIN_PRIORITY,
		Thread.MAX_PRIORITY,
		Thread.NORM_PRIORITY,
	设置优先级的方法: 
		setPriority(int prority);

守护线程：
	守护线程与普通线程在表现上没有什么区别，我们只需要通过Thread 提供的方法来设定即可：
		void setDaemon(boolean)
	守护线程与普通线程唯一的区别是在结束的时机上。当一个进程中所有前台线程都结束时，进程结束，无论该进程中的守护线程是否还在运行都要强制将它们结束。
	GC 就是一个守护线程。







线程同步
	实现方式:
		关键字: synchronized: 同步锁
	锁机制:
		JAVA 提供了一种内置的锁机制来支持原子性：
		同步代码块(synchronized 关键字)，同步代码块包含两个部分：一个作为锁的对象的引用，一个作为由这个锁保护的代码块。
			synchronized 修饰方法:
			// 如果用于修饰方法，那么给调用这个方法的对象(方法所属对象 this)加锁。
			// 也就是说所有这个类的对象实例(都是同一个实例)访问这个方法都会被锁同步
			示例：
			public synchronized void test() {
				
			}
			synchronized (同步监视器--锁对象引用) {
				// 代码块
				// ...
			}
			// 一般都用this 作为监视器
			示例：
			synchronized (this) { }

		若方法所有代码都需要同步可以给方法直接加锁。
		每个JAVA 对象都可以胳膊做一个实现同步的锁，线程进入同步代码块之前会自动 获得锁，并且 在退出同步代码块时自动 释放锁，而且无论是通过正常途径退出还是通过抛异常退出都一样，获取内置锁的唯一途径就是进入由这个锁保护的同步代码块或者方法
		synchronized 修饰代码块
			有效缩小同步范围可以在保证并发安全的前提下提高并发执行效率。
			同步块，同步块可以更精确的控制需要同步的代码片段

		使用 synchronized 需要对一个对象上锁以保证线程同步。那么这个锁对象应当注意: 
			* 多个需要同步的线程在访问该同步块时，看到的应该是一个锁对象引用。否则达不到同步效果。
			* 通常我们会使用this 来作为锁对象。

		静态方法使用 synchronized 修饰后，该方法一定具有同步效果
			synchronized 修饰静态方法，添加锁的对象是当前类.class - Class 对象
			示例：
			public static synchronized void doSomething() { 
				// do something
			}
		
		synchronized 的互斥性
			synchronized 修饰多段代码，但是这些同步块的同步监视器对象是同一个时，那么这些代码间就是互斥的。调用这些多个线程不能同时执行，必须等待。
			class A {
				public synchronized void methodA() { }
				public synchronized void methodb() { }
			}
			以 A 为例，若线程1 调用 methodA 而此时线程2 调用 methodB 那么这两个线程也存在一个锁的竞争。


线程安全API
	StringBuilder 是非线程安全的， StringBuffer 是线程安全的
	对于集合而言，常用的实现类; ArrayList, LinkedList, HashSet 它们都不是线程安全的。
	Collections 可以将现有的集合转换为线程安全的
		- Collections.synchronizedList(List)
		- Collections.synchronizedSet(Set)
		- Collections.synchronizedMap(Map)

线程池
	ExecutorService(返回接口) Executors.newFixedThreadPool(int nThreads); - ExecutorService(接口)

	将任务提交给线程池
		execute(Runnable) - Executor

	关闭: 
		shutdown()
		shutDownNow()
























