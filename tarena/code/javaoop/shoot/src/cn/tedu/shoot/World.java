package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//��������
public class World extends JPanel {
	public static final int WIDTH = 400;  //���ڵĿ�
	public static final int HEIGHT = 700; //���ڵĸ�
	
	public static final int START = 0;     //����״̬
	public static final int RUNNING = 1;   //����״̬
	public static final int PAUSE = 2;     //��ͣ״̬
	public static final int GAME_OVER = 3; //��Ϸ����״̬
	private int state = START; //��ǰ״̬(Ĭ��Ϊ����״̬)
	
	private static BufferedImage start;    //����ͼ
	private static BufferedImage pause;    //��ͣͼ
	private static BufferedImage gameover; //��Ϸ����ͼ
	static{
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.png");		
	}
	
	private Sky sky = new Sky(); //���
	private Hero hero = new Hero(); //Ӣ�ۻ�
	private FlyingObject[] enemies = {}; //��������(С�л�+��л�+С�۷�)
	private Bullet[] bullets = {}; //�ӵ�����
	
	/** ��������(С�л�+��л�+С�۷�)���� */
	public FlyingObject nextOne(){
		Random rand = new Random(); //�������������
		int type = rand.nextInt(20); //0��19֮�ڵ������
		if(type<7){
			return new Airplane();
		}else if(type<14){
			return new BigAirplane();
		}else{
			return new Bee();
		}
	}
	
	int enterIndex = 0; //�����볡����
	/** ����(С�л�+��л�+С�۷�)�볡 */
	public void enterAction(){ //10������һ��
		enterIndex++; //ÿ10������1
		if(enterIndex%40==0){ //ÿ400(10*40)������һ��
			FlyingObject obj = nextOne(); //��ȡ���˶���
			enemies = Arrays.copyOf(enemies,enemies.length+1); //����
			enemies[enemies.length-1] = obj; //�����˶���ֵ��enemies�����һ��Ԫ��
		}
	}
	
	/** �������ƶ� */
	public void stepAction(){ //10������һ��
		sky.step(); //����ƶ�
		for(int i=0;i<enemies.length;i++){ //�������е���
			enemies[i].step(); //�����ƶ�
		}
		for(int i=0;i<bullets.length;i++){ //����
			bullets[i].step(); //�ӵ��ƶ�
		}
	}
	
	int shootIndex = 0; //�����ӵ�����
	/** �ӵ��볡(Ӣ�ۻ������ӵ�) */
	public void shootAction(){ //10������һ��
		shootIndex++; //ÿ10������1
		if(shootIndex%30==0){ //ÿ300(10*30)������һ��
			Bullet[] bs = hero.shoot(); //��ȡӢ�ۻ�������ӵ�����
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //����(bs�м��������󼸸�����)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //�����׷��
		}
	}
	
	/** ɾ��Խ��ķ�����(���˺��ӵ�) */
	public void outOfBoundsAction(){ //10������һ��
		int index = 0; //1)��Խ����������±�  2)��Խ����˸���
		FlyingObject[] enemyLives = new FlyingObject[enemies.length]; //��Խ���������
		for(int i=0;i<enemies.length;i++){ //�������е���
			FlyingObject f = enemies[i]; //��ȡÿһ������
			if(!f.outOfBounds() && !f.isRemove()){ //��Խ��
				enemyLives[index] = f; //����Խ����˶�����ӵ���Խ�����������
				index++; //1)��Խ����������±���һ 2)��Խ����˸�����һ
			}
		}
		enemies = Arrays.copyOf(enemyLives,index); //����Խ��������鸴�Ƶ�enemies�У�index�м�������enemies�ĳ��Ⱦ�Ϊ��
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length]; //��Խ���ӵ�����
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			if(!b.outOfBounds() && !b.isRemove()){ //��Խ��
				bulletLives[index] = b; //����Խ���ӵ�������ӵ���Խ���ӵ�������
				index++; //1)��Խ���ӵ������±���һ 2)��Խ���ӵ�������һ
			}
		}
		bullets = Arrays.copyOf(bulletLives,index); //����Խ���ӵ����鸴�Ƶ�bullets�У�index�м�������bullets�ĳ��Ⱦ�Ϊ��
		
	}
	
	int score = 0; //��ҵ÷�
	/** �ӵ�����˵���ײ */
	public void bulletBangAction(){ //10������һ��
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			for(int j=0;j<enemies.length;j++){ //�������е���
				FlyingObject f = enemies[j]; //��ȡÿһ������
				if(b.isLife() && f.isLife() && f.hit(b)){ //ײ����
					b.goDead(); //�ӵ�ȥ��
					f.goDead(); //����ȥ��
					if(f instanceof Enemy){ //����ײ�����ǵ���
						Enemy e = (Enemy)f; //����ײ����ǿתΪ����
						score += e.getScore(); //��ҵ÷�
					}
					if(f instanceof Award){ //����ײ�����ǽ���
						Award a = (Award)f; //����ײ����ǿתΪ����
						int type = a.getType(); //��ȡ��������
						switch(type){ //���ݽ����Ĳ�ͬ��Ӣ�ۻ���ȡ��ͬ�Ľ���
						case Award.DOUBLE_FIRE: //����������Ϊ����
							hero.addDoubleFire(); //Ӣ�ۻ�������
							break;
						case Award.LIFE: //����������Ϊ��
							hero.addLife(); //Ӣ�ۻ�����
							break;
						}
					}
				}
			}
			
		}
	}
	
	/** Ӣ�ۻ�����˵���ײ */
	public void heroBangAction(){ //10������һ��
		for(int i=0;i<enemies.length;i++){ //�������е���
			FlyingObject f = enemies[i]; //��ȡÿһ������
			if(f.isLife() && f.hit(hero)){ //ײ����
				f.goDead(); //����ȥ��
				hero.subtractLife(); //Ӣ�ۻ�����
				hero.clearDoubleFire(); //Ӣ�ۻ���ջ���ֵ
			}
		}
	}
	
	/** �����Ϸ���� */
	public void checkGameOverAction(){ //10������һ��
		if(hero.getLife()<=0){ //��Ϸ������
			state=GAME_OVER;   //�޸ĵ�ǰ״̬Ϊ��Ϸ����״̬
		}
	}
	
	/** ��������ִ�� */
	public void action(){
		//��������������
		MouseAdapter l = new MouseAdapter(){
			/** ��дmouseMoved()����ƶ��¼� */
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){ //����״̬ʱִ��
					int x = e.getX(); //��ȡ����x����
					int y = e.getY(); //��ȡ����y����
					hero.moveTo(x, y); //Ӣ�ۻ���������ƶ�
				}
			}
			/** ��дmouseClicked()������¼� */
			public void mouseClicked(MouseEvent e){
				switch(state){ //���ݵ�ǰ״̬����ͬ����
				case START:        //����״̬ʱ
					state=RUNNING; //�޸�Ϊ����״̬
					break;
				case GAME_OVER:  //��Ϸ����״̬ʱ
					score = 0;   //�����ֳ�
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state=START; //�޸�Ϊ����״̬
					break;
				}
			}
			/** ��дmouseExited()����Ƴ��¼� */
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){ //����״̬ʱ
					state=PAUSE;    //�޸�Ϊ��ͣ״̬
				}
			}
			/** ��дmouseEntered()��������¼� */
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){  //��ͣ״̬ʱ
					state=RUNNING; //�޸�Ϊ����״̬
				}
			}
		};
		this.addMouseListener(l); //�����������¼�
		this.addMouseMotionListener(l); //������껬���¼�
		
		Timer timer = new Timer(); //������ʱ������
		timer.schedule(new TimerTask(){
			public void run(){ //ÿ10��������һ��--��ʱ�ɵ��Ǹ���
				if(state==RUNNING){ //����״̬ʱִ��
					enterAction(); //����(С�л�+��л�+С�۷�)�볡
					stepAction();  //�������ƶ�
					shootAction(); //�ӵ��볡(Ӣ�ۻ������ӵ�)
					outOfBoundsAction(); //ɾ��Խ��ķ�����
					bulletBangAction(); //�ӵ�����˵���ײ
					heroBangAction();   //Ӣ�ۻ�����˵���ײ
					checkGameOverAction(); //�����Ϸ����
				}
				repaint();     //�ػ�(���µ���paint()����)
			}
		},10,10); //��ʱ�ƻ�
	}
	
	/** ��дpaint()�� */
	public void paint(Graphics g){ //10������һ��
		sky.paintObject(g);  //����ն���
		hero.paintObject(g); //��Ӣ�ۻ�����
		for(int i=0;i<enemies.length;i++){ //�������е���
			enemies[i].paintObject(g); //�����˶���
		}
		for(int i=0;i<bullets.length;i++){ //�����ӵ�����
			bullets[i].paintObject(g); //���ӵ�����
		}
		g.drawString("SCORE:"+score,10,25); //����
		g.drawString("LIFE:"+hero.getLife(),10,45); //����
		
		switch(state){ //��ͬ״̬�»���ͬ��ͼ
		case START: //����״̬ʱ������ͼ
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //��ͣ״̬ʱ����ͣͼ
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER: //��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(); //����һ�����ڶ���
		World world = new World(); //����һ��������
		frame.add(world); //�������ӵ�������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //���ùرմ���ʱ�˳�����
		frame.setSize(WIDTH,HEIGHT); //���ô��ڵĴ�С
		frame.setLocationRelativeTo(null); //���ô��ھ�����ʾ 
		frame.setVisible(true); //1)���ô��ڿɼ�  2)�������paint()
		
		world.action(); //���������ִ��
	}
	
}
















