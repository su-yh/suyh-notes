package cn.tedu.pool;

public class Task implements Runnable {
	private int nIndex = 0;
	Task(int i) {
		nIndex = i;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ", [" + nIndex + "]��ʼִ��...");
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
//			e.printStackTrace();
			System.out.println("�̱߳��жϣ�[" + nIndex + "]");
		}
		
		System.out.println(Thread.currentThread().getName() + ", [" + nIndex + "]ִ�н���������");
	}
}
