package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** ��л�: �Ƿ����Ҳ�ǵ����ܵ÷� */
public class BigAirplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images; //ͼƬ����
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bigplane"+i+".png");
		}
	}
	
	private int speed;	//�ƶ��ٶ�
	/** ���췽�� */
	public BigAirplane(){
		super(69,99);
		speed = 2;
	}
	
	/** ��л��ƶ� */
	public void step(){
		y+=speed; //y+(����)
	}
	
	int deadIndex = 1; //���˵��±�
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage(){ //10������һ��
		if(isLife()){ //��������
			return images[0]; //���ص�1��ͼƬ
		}else if(isDead()){ //��������
			BufferedImage img = images[deadIndex++]; //�ӵ�2��ͼƬ��ʼ
			if(deadIndex==images.length){ //���±�Ϊ����ĳ���
				state = REMOVE; //���޸ĵ�ǰ״̬Ϊ����ɾ����
			}
			return img;
		}
		return null;
	}
	
	/** ��дoutOfBounds()�ж��Ƿ�Խ�� */
	public boolean outOfBounds(){
		return this.y>=World.HEIGHT; //��л���y>=���ڵĸߣ���ΪԽ����
	}
	
	/** ��дgetScore()�÷� */
	public int getScore(){
		return 3; //���һ����л���3��
	}
	
}











