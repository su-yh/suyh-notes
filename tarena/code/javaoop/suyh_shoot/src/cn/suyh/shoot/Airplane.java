package cn.suyh.shoot;

public class Airplane extends FlyingObject {
	public Airplane() {
		super(49, 36);	// ���ó���Ĺ��췽��
		speed = 2;
	}
	
	public void step() {
		y += step;
	}
}
