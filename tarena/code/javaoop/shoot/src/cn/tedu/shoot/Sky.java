package cn.tedu.shoot;

/**
 * ���
 * @author suyh
 *
 */
public class Sky extends FlyingObject {
	int step;
	int y1;	// �ڶ���ͼƬ��y ����
	public Sky() {
		width = 400;
		height = 700;
		x = 0;
		y = 0;
		y1 = -height;
		step = 1;
	}
	
	// �ƶ���Ϊ
//	public void step() {
//		System.out.println("sky �ƶ���" + step + " ������.");
//	}
	
}
