package cn.tedu.shoot;

/**
 * С�۷�
 * @author suyh
 *
 */
public class Bee extends FlyingObject {
	int xstep;
	int ystep;
	public Bee() {
		width = 60;
		height = 50;
		x = (int)(Math.random() * (400  - width));
		y = -height;
		xstep = 1;
		ystep = 2;
	}
	
//	public void step() {
//		System.out.println("С�۷�����ƶ���" + xstep + "������.");
//		System.out.println("С�۷������ƶ���" + ystep + "������.");
//	}
	
}
