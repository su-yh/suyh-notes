package cn.tedu.synchronizeddemo;

public class Test {
	public static void main(String[] args) {
		// ���������߳� ����
		MyThread myThread = new MyThread();
		
		Thread thread = new Thread(myThread, "�߳�A");
		Thread thread2 = new Thread(myThread, "�߳�B");
		
		thread.start();
		thread2.start();
	}
}
