package cn.tedu.shoot;

/**
 * ���������ж����������������
 * @author suyh
 *
 */
public class World {
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
	}
	
	public static void main(String[] args) {
		World world = new World();
		world.action();
	}
}
