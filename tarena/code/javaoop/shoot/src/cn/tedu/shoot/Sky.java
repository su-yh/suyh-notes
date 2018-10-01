package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** ���: �Ƿ����� */
public class Sky extends FlyingObject {
	private static BufferedImage image; //ͼƬ
	static{
		image = loadImage("background.png");
	}
	
	private int speed;	//�ƶ��ٶ�
	private int y1;		//y1����(ͼƬ�ֻ�)
	/** ���췽�� */
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -height; //y1:���Ĵ��ڵĸ�
	}
	
	/** ����ƶ� */
	public void step(){
		y+=speed;  //y+(����)
		y1+=speed; //y1+(����)
		if(y>=this.height){ //��y>=��յĸ�(��������)
			y=-this.height; //������yΪ���ĸ�(��������)
		}
		if(y1>=this.height){ //��y1>=��յĸ�(��������)
			y1=-this.height; //������y1Ϊ���ĸ�(��������)
		}
	}
	
	/** ��дgetImage()��ȡͼƬ */
	public BufferedImage getImage(){
		return image;
	}
	
	/** ������ g:���� */
	public void paintObject(Graphics g){
		g.drawImage(getImage(),x,y,null);  //�����1
		g.drawImage(getImage(),x,y1,null); //�����2
	}
	
	/** ��дoutOfBounds()�ж��Ƿ�Խ�� */
	public boolean outOfBounds(){
		return false; //����Խ��
	}
	
	// ��ͼƬ�ķ���
	@Override
	public void paint(Graphics g) {
		g.drawImage(getImage(), x, y, null);
		g.drawImage(getImage(), x, y1, null);
	}
	
}











