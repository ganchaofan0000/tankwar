import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullets1 {
	public static  int XSPEED = 10;
	public static  int YSPEED = 10; // �ӵ���ȫ�־�̬�ٶ�

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction dir;

	private boolean good;
	private boolean live = true;

	private TankClient1 tc1;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>(); // ����Map��ֵ�ԣ��ǲ�ͬ�����Ӧ��ͬ�ĵ�ͷ

	static {
		bulletImages = new Image[] {
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletL.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletR.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletD.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletU.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletLU.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletLD.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletRU.png")),
			tk.getImage(Tank1.class.getClassLoader().getResource("Images/BulletRD.png"))
		};
		
		
		imgs.put("L",bulletImages[0]);
		imgs.put("R",bulletImages[1]);
		imgs.put("D",bulletImages[2]);
		imgs.put("U",bulletImages[3]);
		imgs.put("LU",bulletImages[4]);
		imgs.put("LD",bulletImages[5]);
		imgs.put("RU",bulletImages[6]);
		imgs.put("RD",bulletImages[7]);
		
	}

	public Bullets1(int x, int y, Direction dir) { // ���캯��1������λ�úͷ���
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	// ���캯��2������������������
	public Bullets1(int x, int y, boolean good, Direction dir, TankClient1 tc1) {
		this(x, y, dir);
		this.good = good;
		this.tc1 = tc1;
	}

	private void move() {

		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		}
		if (x < 0 || y < 0 || x > TankClient1.Fram_width
				|| y > TankClient1.Fram_length) {
			live = false;
		}
	}

	public void draw(Graphics g) {
		if (!live) {
			tc1.bullets.remove(this);
			return;
		}

		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"),x,y,null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"),x,y,null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"),x,y,null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"),x,y,null);
			break;
		}

		move(); // �����ӵ�move()����
	}

	public boolean isLive() { // �ж��Ƿ񻹻���
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x + 32, y + 32, width, length);
	}

	public boolean hitTanks(List<Tank1> tanks) {// ���ӵ���̹��ʱ
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) { // ��ÿһ��̹�ˣ�����hitTank
				return true;
			}
		}
		return false;
	}

	public boolean hitTank(Tank1 t) { // ���ӵ���̹����

		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc1);
			tc1.bombTanks.add(e);
		
				t.setLife(t.getLife() - 50); // ��һ���ӵ���������50������4ǹ����,������ֵ200
				if (t.getLife() <= 0)
					t.setLive(false); // ������Ϊ0ʱ����������Ϊ����״̬
		

			this.live = false;

			return true; // ����ɹ�������true
		}
		return false; // ���򷵻�false
	}

	public boolean hitWall(CommonWall w) { // �ӵ���CommonWall��
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc1.otherWall.remove(w); // �ӵ���CommonWallǽ��ʱ���Ƴ��˻���ǽ
			this.tc1.homeWall.remove(w);
			return true;
		}
		return false;
	}

	public boolean hitWall(MetalWall w) { // �ӵ��򵽽���ǽ��
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			//this.tc.metalWall.remove(w); //�ӵ����ܴ�Խ����ǽ
			return true;
		}
		return false;
	}


	public boolean isGood() {
		return good;
	}

	public int getX() {
		// TODO �Զ����ɵķ������
		return x;
	}

	public int getY() {
		// TODO �Զ����ɵķ������
		return y;
	}

}
