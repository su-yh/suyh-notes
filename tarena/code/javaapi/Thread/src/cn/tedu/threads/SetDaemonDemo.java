package cn.tedu.threads;
/**
 * ģ���̨�߳�
 * @author suyh
 *
 */
public class SetDaemonDemo {
	public static void main(String[] args) {
		// ���� rose ǰ̨�߳�
		Thread rose = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; ++i){
					System.out.println("jack, help me!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("��ͨ��");
			};
		};
		
		// �������̨�̣߳�jack
		Thread jack = new Thread() {
			@Override
			public void run() {
				while (true) {
					System.out.println("you jump, I jump!");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}		
				}
			};
		};
		
		jack.setDaemon(true);
		
		System.out.println("rose: " + rose.isDaemon());
		System.out.println("jack: " + jack.isDaemon());
		
		rose.start();
		jack.start();
	}
}
