package cn.tedu.synchronizeddemo;

public class Shop implements Runnable {

	@Override
	public void run() {
		run1();
//		run2();
	}

	// ��������������
	synchronized void run1() {
		System.out.println(Thread.currentThread().getName() + "����ѡ�·�");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "��ѡ���·�");

		System.out.println(Thread.currentThread().getName() + "�������·�");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "���½���");
	}

	// ���ĳһ��������
	void run2() {
		System.out.println(Thread.currentThread().getName() + "����ѡ�·�");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "��ѡ���·�");

		// // ���ĳһ��������
		synchronized (this) {
			System.out.println(Thread.currentThread().getName() + "�������·�");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "���½���");
		}
	}
}
