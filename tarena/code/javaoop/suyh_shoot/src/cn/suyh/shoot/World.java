package cn.suyh.shoot;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class World extends JPanel {
	public static final int WIDTH = 400;	// ���ڿ�
	public static final int HEIGHT = 700; 	// ���ڸ�
	
	public static final int START = 0;	// ����״̬
	public static final int RUNNING = 1;	// ����״̬
	public static final int PAUSE = 2;	// ��ͣ״̬
	public static final int GAME_OVER = 3;	 // ��Ϸ����
	private int state = START;	// ��ǰ״̬(Ĭ��Ϊ����״̬)
	
	private static BufferedImage start;	// ����ͼ
	private static BufferedImage pause;	// ��ͣͼ
	private static BufferedImage gameover;	// ����ͼ
	
	static {
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("gameover.pgn");
	}

	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {};	// ��������(С�л� + ��л� + С�۷�)
	private Bullet[] bullets = {};	// �ӵ�����
	
	// ��������
	public FlyingObject nextOne() {
		Random rand = new Random();	// �������������
		int type = rand.nextInt(20);	// 1 ~ 19
		if (type < 7) {
			return new Airplane(); 
		} else if (type < 14) {
			return new BigAirPlance();
		} else {
			return new Bee();
		}
	}
	
	int enterIndex = 0;	// �����볡����
	// �����볡
	public void enterAction() {	// 10 ������һ��
		enterIndex++;
		if (enterIndex % 40 == 0) {	// ÿ400(40 * 10) ������һ��
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies, enemies.length + 1);	// ����
			enemies[enemies.length - 1] = obj;	// �����˶���ֵ ��enemies �����һ��λ��
		}
	}
	
	// �������ƶ�
	public void stepAction() {	// 10 ������һ��
		sky.step();	// ����ƶ�
		for(int i = 0; i < enemies.length; i++) {
			enemies[i].step();
		}
		for (int i = 0; i < bullets.length; ++i) {
			bullets[i].step();
		}
	}
	
	int shootIndex = 0;  // �����ӵ�����
	// �ӵ��볡
	public void shootAction() {
		shootIndex++;	// ÿ10 ���� ��1 
		if (shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();	// ��ȡӢ�ۻ�������ӵ�����
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);	// �����׷��
		}
	}
	
	public void outOfBoundsAction() {	// 10 ������һ��
		int index = 0;	// 1) ��Խ����������±� 2) ��Խ����˸���
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];	// ��Խ���������
		for (int i = 0; i < enemies.length; ++i) { // �������е���
			FlyingObject f = enemies[i];	// ��ȡÿһ������
			if (!f.outOfBounds() && !f.isRemove()) {
				enemyLives[index] = f;	// ����Խ��ĵ��˶�����ӵ���Խ�����������
				index++;	// 1) ��Խ����������±���1 2) ��Խ����˸�����1
			}
		}
		
		enemies = Arrays.copyOf(enemyLives, index);
		
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];// ��Խ���ӵ�����
		for (int i = 0; i < bullets.length; ++i) {
			Bullet b = bullets[i]; 	// ��ȡÿһ���ӵ�
			if (!b.outOfBounds() && !b.isRemove()) {
				bulletLives[index] = b;	// ����Խ���ӵ�������ӵ���Խ���ӵ�������
				index++;	// 1) ��Խ���ӵ������±���1 2) ��Խ���ӵ�������1
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);	// ����Խ���ӵ����鸴�Ƶ�bullets�У�index �м�������bullets �ĳ��Ⱦ�Ϊ����
	}
	
	int score = 0;	// ��ҵ÷�
	// �ӵ�����˵���ײ
	public void bulletBangAction() { // 10 ������һ��
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			for (int j = 0; j < enemies.length; j++) {
				FlyingObject f = enemies[j];	// ��ȡÿһ������
				if (b.isLife() && f.isLife() && f.hit(b)) {  // ײ����
					b.goDead();
					f.goDead();
					if (f instanceof Enemy) {	// ����ײ�����ǵ���
						Enemy e = (Enemy)f;	// ����ײ����ǿתΪ����
						score += e.getScore();	// ��ҵ÷�
					}
					if (f instanceof Award) { // ����ײ�����ǽ���
						Award a = (Award)f;
						int type = a.getType();
						Switch (type) {
							case Award.DOUBLE_FIRE:
								hero.addDoubleFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
						}
					}
				}
			}
		}
	}
	
	// Ӣ�ۻ�����˵���ײ
	public void heroBangAction() {
		for (int i = 0; i < enemies.length; ++i) {
			FlyingObject f = enemies[i];
			if (f.isLife() && f.hit(hero)) {
				f.goDead();
				hero.subtractLife();	// Ӣ�ۻ�����
				hero.clearDoubleFire();	// Ӣ�ۻ���ջ���ֵ
			}
		}
	}
	
	// �����Ϸ����
	public void checkGameOverAction() {	// 10 ������һ��
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}
	
	// ��������ִ��
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			// ��дmouseMoved() ����ƶ��¼�
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {
					int x = e.getX();// ��ȡ��� ��x ����
					int y = e.getY();	// ��ȡ��� ��y ����
					hero.moveTo(x, y);	// Ӣ�ۻ���������ƶ�
				}
			}
			// ��дmouseClicked() ������¼�
			public void mouseClicked(MouseEvent e) {
				switch (state) {	// ���ݵ�ǰ״̬����ͬ����
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;	// �޸�Ϊ����״̬
					break;
				}
			}
			// ��дmouseExisted() ����ƶ��¼�
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
			// ��дmouseEntered() ��������¼�
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);	// �����������¼�
		this.addMouseMotionListener(l);	// ������� �����¼�
		
		Timer timer = new Timer();	// ������ʱ������
		timer.schedule(new TimerTask() {
			public void run() { // ÿ10 ��������һ�� -- ��սʱ�ɵ��Ǹ���
				if (state == RUNNING) {
					enterAction();	// �����볡
					stepAction();	// �������ƶ�
					shootAction();	// �ӵ��볡(Ӣ�ۻ������ӵ�)
					outOfBoundsAction();	// ɾ��Խ��ķ�����
					bulletBangAction();	// �ӵ�����˵���ײ
					heroBangAction();	// Ӣ�ۻ�����˵���ײ
					checkGameOverAction();	// �����Ϸ����
				}
				repaint();	// �ػ�(���µ���paint() ����)
			}
		}, 10, 10);	// ��ʱ�ƻ�
	}
	
	// ��дpaint() ��ͼ
	public void paint(Graphics g) {
		sky.paintObject(g);
		hero.paintObject(g);
		for (int i = 0; i < enemies.length; ++i) {
			enemies[i].paintObject(g);
		}
		for (int i = 0; i < bullets.length; ++i) {
			bullets[i].paintObject(g);
		}
		g.drawString("SCORE:" + score, 10, 25);	 // ����
		g.drawString("LIFE: " + hero.getLife(), 10, 45);	// ����
		
		switch (state) {	// ��ͬ״̬ �»���ͬ��ͼ
		case START:	//����״̬
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	publict static void main(String[] args) {
		JFrame frame = new JFrame();	// ����һ�����ڶ���
		World world = new World();
		frame.add(world);	// ��������ӵ�������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// ���ùرմ�ʱ�˳�����
		frame.setSize(WIDTH, HEIGHT);	// ���ô��ڵĴ�С
		frame.setLocationRelativeTo(null);	// ���ô��ھ�����ʾ
		frame.setVisible(true);	// 1) ���ô��ڿɼ���2) ������� paint()
		
		world.action();// �������� ��ִ��
	}
}
