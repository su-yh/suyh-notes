package cn.tedu.synchronizeddemo;

public class SynchronizedMutexDemo {
	public static void main(String[] args) {
		// �������� �̶߳���һ��ִ��methodA һ��ִ��methodB ����
		// �߳�ͬʱ����
		TestMutex test = new TestMutex();
		
		Thread thread1 = new Thread(){
			public void run() {
				test.syncA();
			}
		};
		
		Thread thread2 = new Thread() {
			public void run() {
				test.syncB();
			}
		};
		
		thread1.start();
		thread2.start();
	}
}

class TestMutex {
	public synchronized void syncA() {
		methodA();
	}
	public synchronized void syncB() {
		methodB();
	}
	
	public void methodA() {
		System.out.println("A������ʼִ��...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("A����ִ�н�����");
	}
	
	public void methodB() {
		System.out.println("B������ʼִ��...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("B����ִ�н�����");
	}
}
