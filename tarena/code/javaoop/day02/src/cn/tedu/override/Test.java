package cn.tedu.override;

public class Test {
	public static void main(String[] args) {
		// ����Bee, airplane ���󣬲�ʵ���ƶ�����
		FlyingObject bee = new Bee();
		bee.step();
		
		FlyingObject airplane = new Airplane();
		airplane.step();
	}
}






