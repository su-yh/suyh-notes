package cn.tedu.shoot;

/**
 * С�л�
 * @author suyh
 *
 */
public class Airplane {
	int width;
	int height;
	int x; 
	int y;
	int step;	// �ƶ���λ
	
	
	public Airplane() {
		width = 49;
		height = 36;
		x = (int)(Math.random() * (400  - width));
		y = -height;
		step = 2;
	}
	
	// �ƶ���Ϊ
	public void step() {
		System.out.println("С�л��ƶ���: " + step + "������");
	}
	
	
}
