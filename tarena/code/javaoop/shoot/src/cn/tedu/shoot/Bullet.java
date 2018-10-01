package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/** �ӵ�: �Ƿ����� */
public class Bullet extends FlyingObject {
	private static BufferedImage image; //ͼƬ
	static{
		image = loadImage("bullet.png");
	}
	
	private int speed; //�ƶ��ٶ�
	/** ���췽��  x,y:��Ӣ�ۻ���λ�ö���ͬ*/
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	
	/** �ӵ��ƶ� */
	public void step(){
		y-=speed; //y-(����)
	}
	
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage(){
		if(isLife()){ //���������
			return image; //����imageͼƬ
		}else if(isDead()){ //������˵�
			state = REMOVE; //������״̬�޸�Ϊ����ɾ����
		}
		return null;
	}
	
	/** ��дoutOfBounds()�ж��Ƿ�Խ�� */
	public boolean outOfBounds(){
		return this.y<=-this.height; //�ӵ���y<=�����ӵ��ĸߣ���ΪԽ����
	}
	
}
















