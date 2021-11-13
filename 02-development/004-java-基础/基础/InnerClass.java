内部类
	InnerClass 
	将一个类定义 在另一个类的内部，此时这个类就是一个内部类，是OutClass 的一个成员。
	
	内部类可以访问外部类的所有成员(包括私有成员)
	
	-- 普通内部类:
		Thread thd1 = new Thread() {
			@Override
			public void run() {
				System.out.println("匿名创建Thread 对象1。");
			};
		};
		thd1.start();
	-- 匿名内部类: 
		在一个方法中，如果参数列表公交车的参数类型是一个接口或抽象类，那么此时调用该方法传递参数时就用到了匿名内部类。
		Thread thd2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("接口创建Thread 对象2。");
			}
		});
		thd2.start();
		
	-- 实例内部类：
		class Outer {
			class WorkThread extends Thread {
				@Override
				public void run() {
					Sysout.out.println(Outer.this);
					Sysout.out.println(this);
					while (true) {}
				}
			}
		}
		-- 实例内部类拥有外部类的this 指针，所以构建实例内部类的时候，需要有一个外部类的实例对象。
		
		使用: 
		Outer o = new Outer();
		o.new WorkThread();
		
		
	-- 静态内部类: 
		class Outer {
			static class WorkThread extends Thread {
				@Override
				public void run() {
					Sysout.out.println(Outer.this);
					Sysout.out.println(this);
					while (true) {}
				}
			}
		}
		
		使用: 
		new Outer.WorkThread();



