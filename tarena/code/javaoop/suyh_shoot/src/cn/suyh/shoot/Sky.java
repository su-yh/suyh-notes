package cn.suyh.shoot;

public class Sky extends FlyingObject {
	private static BufferedImage imgae;
	
	static {
		image = loadImage("background.png");
	}

	private int step;
	private int y1;
	
	public void step() {
		y += step;
		y1 += step;
		
		if (y >= this.height) {	// ����������ı߽�
			y = -this.height;	// �ص�������ı߽�
		}
		if (y1 >= this.height) {
			y1 = -this.height;
		}
	}
}
