package cn.tedu.shoot;

/**
 * Ӣ�ۻ�
 * @author suyh
 *
 */
public class Hero {
	int width;
	int height;
	int x;
	int y;
	int life;	// ����ֵ
	int doubleFire;	// ����ֵ
	
	public Hero() {
		// �����ݽ��г�ʼ��
		width = 97;
		height = 124;
		x = 140;
		y = 400;
		life = 3;
		doubleFire = 0;
	}
	
	// �ƶ���Ϊ
	public void move(int x, int y) {
		System.out.println("Ӣ�ۻ��ƶ�����: " + x + ", " + y);
	}
}
