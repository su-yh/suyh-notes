package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** С�л�: �Ƿ����Ҳ�ǵ����ܵ÷� */
public class Airplane extends FlyingObject implements Enemy {
	private static BufferedImage[] images; //ͼƬ����
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("airplane"+i+".png");
		}
	}
	
	private int speed;  //�ƶ��ٶ�
	/** ���췽�� */
	public Airplane(){
		super(49,36); //���ó���Ĺ��췽��
		speed = 2;
	}
	
	/** С�л��ƶ� */
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
		return this.y>=World.HEIGHT; //С�л���y>=���ڵĸߣ���ΪԽ����
	}
	
	/** ��дgetScore()�÷� */
	public int getScore(){
		return 1; //���һ��С�л���1��
	}
}


















