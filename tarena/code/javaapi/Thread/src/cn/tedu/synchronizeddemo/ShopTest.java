package cn.tedu.synchronizeddemo;

public class ShopTest {
	public static void main(String[] args) {
		Shop shop = new Shop();	// ͬһ�����������̶߳���ͬһ��shop ���󣬷���ﲻ��ͬ����Ч�� ����Shop s1; Shop s2;
		
		
		Thread thread = new Thread(shop, "����");
		Thread thread2 = new Thread(shop, "����");
		
		thread.start();
		thread2.start();
	}
}
