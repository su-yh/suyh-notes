package cn.tedu.threads;

public class Demo {
	public static void main(String[] args) throws InterruptedException {
//		demo01();
		demo02();
	}

	public static void demo01() {
		MyThread thd = new MyThread();
		thd.start();
		// thd.run(); // ��Ӧ��ֱ�ӵ��� run() ��������Ӧ�õ���start() �������������������������̣߳�����ֻ�ǵ��÷�������
	}
	
	public static void demo02() {
		// �����̶߳���ִ��RunnableDemo �е�����
		RunnableDemo demo = new RunnableDemo();
		
		Thread thd = new Thread(demo);
		
		thd.start();
	}
}
