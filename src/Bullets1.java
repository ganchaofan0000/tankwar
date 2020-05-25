import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullets1 {
	public static  int XSPEED = 10;
	public static  int YSPEED = 10; // 子弹的全局静态速度

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction dir;

	private boolean good;
	private boolean live = true;

	private TankClient1 tc1;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>(); // 定义Map键值对，是不同方向对应不同的弹头

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

	public Bullets1(int x, int y, Direction dir) { // 构造函数1，传递位置和方向
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	// 构造函数2，接受另外两个参数
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

		move(); // 调用子弹move()函数
	}

	public boolean isLive() { // 判读是否还活着
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x + 32, y + 32, width, length);
	}

	public boolean hitTanks(List<Tank1> tanks) {// 当子弹打到坦克时
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) { // 对每一个坦克，调用hitTank
				return true;
			}
		}
		return false;
	}

	public boolean hitTank(Tank1 t) { // 当子弹打到坦克上

		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc1);
			tc1.bombTanks.add(e);
		
				t.setLife(t.getLife() - 50); // 受一粒子弹寿命减少50，接受4枪就死,总生命值200
				if (t.getLife() <= 0)
					t.setLive(false); // 当寿命为0时，设置寿命为死亡状态
		

			this.live = false;

			return true; // 射击成功，返回true
		}
		return false; // 否则返回false
	}

	public boolean hitWall(CommonWall w) { // 子弹打到CommonWall上
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc1.otherWall.remove(w); // 子弹打到CommonWall墙上时则移除此击中墙
			this.tc1.homeWall.remove(w);
			return true;
		}
		return false;
	}

	public boolean hitWall(MetalWall w) { // 子弹打到金属墙上
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			//this.tc.metalWall.remove(w); //子弹不能穿越金属墙
			return true;
		}
		return false;
	}


	public boolean isGood() {
		return good;
	}

	public int getX() {
		// TODO 自动生成的方法存根
		return x;
	}

	public int getY() {
		// TODO 自动生成的方法存根
		return y;
	}

}
