package cn.tedu.shoot;

/**
 * �ӵ�
 * @author suyh
 *
 */
public class Bullet extends FlyingObject {
	int step;
	public Bullet(int x, int y) {
		width = 8;
		height = 14;
		this.x = x;
		this.y = y;
		step = 3;
	}
	
//	// �ƶ� Ϊ
//	public void step() {
//		System.out.println("�ӵ��ƶ� ��" + step + "������.");
//	}
	
	
	
}
