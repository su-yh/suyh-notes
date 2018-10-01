package cn.suyh.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

// ������
public abstract class FlyingObject {
	public static final int LIFE = 0;	// ���ŵ�
	public static final int DEAD = 1;	// ����
	public static final int REMOVE = 2;	// ����ɾ��
	
	protected int state = LIFE;	// ��ǰ״̬(Ĭ��Ϊ���ŵ�)
	
	protected int width;	// ��
	protected int height;	// ��
	protected int x;
	protected int y;
	
	// Ĭ�Ϲ���
	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	// ר�Ÿ���������(Airplane��BigAriplane��Bee)
	public FlyingObject(int width, int height) {
		this.width = width;
		this.height = height;
		Random rand = new Random();	// ���������
		x = rand.nextInt(World.WIDTH - this.width);
		y = -this.height;	// y: ����С�л��ĸ�
	}
	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}
	
	// �������ƶ�
	public abstract void step();
	
	public abstract BufferedImage getImage();
	
	// ������ g:����
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}
	
	public boolean isLife() {
		return state == LIFE;
	}
	
	public boolean isDead() {
		return state == DEAD;
	}
	
	public boolean isRemove() {
		return state == REMOVE;
	}
	
	// �жϷ������Ƿ�Խ��
	public abstract boolean outOfBounds();
	
	// ���������ӵ���Ӣ�ۻ�����ײthis: ���� other: �ӵ���Ӣ�ۻ�
	public boolean hit(FlyingObject other) {
		int x1 = this.x - other.width;	// x1: ���˵�x - �ӵ��Ŀ�
		int x2 = this.x + this.width;	// x2: ���˵�x + ���˵Ŀ�
		int y1 = this.y - other.height;	// y1: ���˵�y - �ӵ��ĸ�
		int y2 = this.y + this.height;	// y2: ���˵�y + ���˵ĸ�
		int x = other.x;	// x: �ӵ���x
		int y = other.y;	// y: �ӵ���y
		
		// x ��x1 ��x2 ֮�䣬���ң�y ��y1 ��y2 ֮�䣬��Ϊײ����
		return x >= x1 && x <= x2 &&
				y >= y1 && y <= y2;
	}
	
	public void goDead() {
		state = DEAD;
	}
}
