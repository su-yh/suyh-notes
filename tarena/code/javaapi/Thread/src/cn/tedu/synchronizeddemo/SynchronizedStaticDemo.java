package cn.tedu.synchronizeddemo;

/**
 * synchronized �����������ξ�̬��������ʱ�����̬�����ĵ���Ҳ��ͬ���ġ�
 * ��� �����Ķ����ǵ�ǰ��.class - Class
 * @author suyh
 *
 */
public class SynchronizedStaticDemo {
	public static void main(String[] args) {
		// ���������̶߳���ֱ�ִ����������
		Thread thread = new Thread() {
			public void run() {
				StaticDemo.doSomething();
			}
		};
		
		Thread thread2 = new Thread() {
			public void run() {
				StaticDemo.doSomething();
			}
		};
		
		thread.start();
		thread2.start();
	}
}

class StaticDemo {
	public static synchronized void doSomething() {
		System.out.println(Thread.currentThread().getName() + "��ʼִ������");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "����ִ����ϣ�");
	}
}



