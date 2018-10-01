package cn.suyh.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	private static BufferedImage[] images;	// ͼƬ����
	
	static {
		images = new BufferedImage[6];
		for (int i = 0; i < images.length; i++) {
			images[i] = loadImage("hero" + i + ".png");
		}
	}
	
	private int life; 	// ��
	private int doubleFire;	// ����ֵ
	
	public Hero() {
		super(97, 124, 140, 400);
		this.life = 3;	// Ĭ��3 ����
		this.doubleFire = 0;
	}
	
	public void moveTo(int x, int y) {
		this.x = x - this.width / 2;
	}
	
	public void step() {
		
	}
	
	
}
