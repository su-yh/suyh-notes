package cn.tedu.threads;

/**
 * �������ڲ��ഴ�������̶߳�������
 * 
 * @author suyh
 *
 */
public class NoNameThreadDemo {
	public static void main(String[] args) {
		// ���������̶߳���
		Thread thd1 = new Thread() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("��������Thread ����1��");
			};
		};


		Thread thd2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("�ӿڴ���Thread ����2��");
			}
		});
		
		thd1.setName("ThreadName");
		thd2.setName("ThreadName");
		thd1.start();
		thd2.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main �߳�����2�룬�Ѿ�������");

//		thd2.interrupt();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("main �߳̽���");
	}
}
