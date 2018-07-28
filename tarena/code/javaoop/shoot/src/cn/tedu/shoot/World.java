package cn.tedu.shoot;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * ���������ж����������������
 * @author suyh
 *
 */
public class World extends JPanel {
	public void action() {
		// ���������Լ�ʵ���ö����ƶ�
		Sky sky = new Sky();
		Hero hero = new Hero();
		Airplane airplane = new Airplane();
		BigAirplane bigAirplane = new BigAirplane();
		Bee bee = new Bee();
		Bullet bullet = new Bullet(100, 200);
		
		// �ö���ʵ��һ���ƶ�
		sky.step();
		airplane.step();
		bigAirplane.step();
		bee.step();
		hero.move(200, 300);
		bullet.step();
		
		// ���з���������һ��FlyingObject ������
		FlyingObject[] enemies = new FlyingObject[9];
		for (int i = 0; i < 3; i++) {
			enemies[3 * i + 0] = new Airplane();
			enemies[3 * i + 1] = new Bee();
			enemies[3 * i + 2] = new BigAirplane();
		}
		
		Bullet[] bullets = new Bullet[3];
		bullets[0] = new Bullet(100, 210);
		bullets[1] = new Bullet(100, 240);
		bullets[2] = new Bullet(100, 270);
		
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("�ɻ���ս");
		World world = new World();
		
		frame.add(world);
		
		// �趨����Ĭ�Ϲر���Ϊ - ����������������ô�رմ��ں󣬳��򲻻������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڴ�С
		frame.setSize(400, 700);
		frame.setLocationRelativeTo(null);
		// �ô�����ʾ��Ĭ��JFrame �������� �ǲ���ʾ�����ģ���Ҫ��ʾ���á�
		frame.setVisible(true);
		
		world.action();
	}
}
