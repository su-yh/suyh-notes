package cn.tedu.shoot;

/**
 * ��л�
 * @author suyh
 *
 */
public class BigAirplane extends FlyingObject {
	int step;
	
	public BigAirplane() {
		width = 69;
		height = 99;
		x = (int)(Math.random() * (400  - width));
		y = -height;
		step = 2;
	}
	
//	// �ƶ���Ϊ
//	public void step() {
//		System.out.println("��л��ƶ���: " + step + "������");
//	}
}
