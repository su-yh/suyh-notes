package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
/** С�۷�: �Ƿ����� */
public class Bee extends FlyingObject implements Award {
	private static BufferedImage[] images; //ͼƬ����
	static{
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++){
			images[i] = loadImage("bee"+i+".png");
		}
	}
	
	private int xSpeed;    //x�����ƶ��ٶ�
	private int ySpeed;	   //y�����ƶ��ٶ�
	private int awardType; //��������(0��1)
	/** ���췽�� */
	public Bee(){
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2); //0��1֮�ڵ������
	}
	
	/** С�۷��ƶ� */
	public void step(){
		x+=xSpeed; //x+(���������)
		y+=ySpeed; //y+(����)
		if(x<=0 || x>=World.WIDTH-this.width){ //��x<=0����x>=(���ڿ�-�۷��)��˵���������ˣ����޸�x�ƶ��ķ���
			xSpeed*=-1; //���为��������
		}
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
		return this.y>=World.HEIGHT; //С�۷��y>=���ڵĸߣ���ΪԽ����
	}
	
	/** ��дgetType()��ȡ�������� */
	public int getType(){
		return awardType; //���ؽ�������(0��1)
	}
	
}












