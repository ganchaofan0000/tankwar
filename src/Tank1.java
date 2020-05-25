import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank1 {
	public static  int XSPEED = 5, YSPEED =5; // 静态全局变量速度
	public static int count = 0;
	public static final int width = 34, length = 35; // 坦克的全局大小，具有不可改变性
	private Direction Dir = Direction.STOP; // 初始化状态为静止
	private Direction ptDir = Direction.U; // 初始化方向为向上
	TankClient1 tc;

	private boolean good;
	private int x, y,z;
	private int oldX, oldY;
	private boolean live = true; // 初始化为活着
	private int life = 200; // 初始生命值
	private int sf_count = 0;
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ; // 产生一个随机数,随机模拟坦克的移动路径

	private boolean dir_Left = false, dir_Right = false, dir_Up = false, dir_Down = false;
	

	private static Toolkit tk = Toolkit.getDefaultToolkit();// 控制面板
	private static Image[] tankImages = null; // 存储全局静态
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImages = new Image[] {
				tk.getImage(Tank1.class.getResource("Images/tankD.png")),
				tk.getImage(Tank1.class.getResource("Images/tankU.png")),
				tk.getImage(Tank1.class.getResource("Images/tankL.png")),
				tk.getImage(Tank1.class.getResource("Images/tankR.png")),
				tk.getImage(Tank1.class.getResource("Images/tankRU.png")),
				tk.getImage(Tank1.class.getResource("Images/tankRD.png")),
				tk.getImage(Tank1.class.getResource("Images/tankLU.png")),
				tk.getImage(Tank1.class.getResource("Images/tankLD.png")),};
		
		imgs.put("D",tankImages[0]);
		imgs.put("U",tankImages[1]);
		imgs.put("L",tankImages[2]);
		imgs.put("R",tankImages[3]);
		imgs.put("RU",tankImages[4]);
		imgs.put("RD",tankImages[5]);
		imgs.put("LU",tankImages[6]);
		imgs.put("LD",tankImages[7]);

	}

	public Tank1(int x, int y, boolean good ,int z){// Tank的构造函数1
		this.x = x;
		this.y = y;
		this.z = z;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank1(int x, int y, boolean good, int z, Direction dir, TankClient1 tc) {// Tank的构造函数2
		this(x, y, good,z);
		this.Dir = dir;
		this.tc = tc;
	}


	public void draw(Graphics g) {
		if (!live) {
			return;
		}
/*
			new DrawBloodbBar().draw(g); // 创造一个血包
*/
		switch(ptDir) {
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

		move();   //调用move函数

	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch(Dir) {
		case L:
			if(this.collideRiver(this.tc.theRiver))
				x -= 1;
			else
				x -= XSPEED;
			break;
		case R:
			if(this.collideRiver(this.tc.theRiver))
				x += 1;
			else
				x += XSPEED;
			break;
		case U:
			if(this.collideRiver(this.tc.theRiver))
				y -= 1;
			else
				y -= YSPEED;
			break;
		case D:
			if(this.collideRiver(this.tc.theRiver))
				y += 1;
			else
				y += YSPEED;
			break;
		case LU:
			if(this.collideRiver(this.tc.theRiver)) {
				x -= 1;
				y -= 1;
			}
			else {
				x -= XSPEED;
				y -= YSPEED;
			}	
			break;
		case LD:
			if(this.collideRiver(this.tc.theRiver)) {
				x -= 1;
				y += 1;
			}
			else {
				x -= XSPEED;
				y += YSPEED;
			}	
			break;
		case RD:
			if(this.collideRiver(this.tc.theRiver)) {
				x += 1;
				y += 1;
			}
			else {
				x += XSPEED;
				y += YSPEED;
			}	
			break;
		case RU:
			if(this.collideRiver(this.tc.theRiver)) {
				x += 1;
				y -= 1;
			}
			else {
				x += XSPEED;
				y -= YSPEED;
			}	
			break;
		}
		if(this.Dir != Direction.STOP) {
			this.ptDir = this.Dir;
		}

		if (x < 0)
			x = 0;
		if (y < 40)      //防止走出规定区域
			y = 40;
		if (x + Tank1.width > TankClient1.Fram_width)  //超过区域则恢复到边界
			x = TankClient1.Fram_width - Tank1.width;
		if (y + Tank1.length > TankClient1.Fram_length)
			y = TankClient1.Fram_length - Tank1.length;

		/*
		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  //产生随机路径
				int rn = r.nextInt(directons.length);
				Dir = directons[rn];      //产生随机方向
			}
			step--;

			if (r.nextInt(40) > 38)//产生随机数，控制敌人开火,可调节该项加大难度
				this.fire();
		}
		*/
	}

	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {  //接受键盘事件
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  //当按下R时，重新开始游戏 
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			tc.homeTank2.setLive(false);
			/*
			if (tc.tanks.size() == 0) {   //当在区域中没有坦克时，就出来坦克      
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              //设置坦克出现的位置
						tc.tanks.add(new Tank(150 + 70 * i,40, false,Direction.R, tc));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i -6), false,Direction.D, tc));
					else
						tc.tanks.add(new Tank(10,  50 * (i - 12), false, Direction.L, tc));
				}
			}
			*/
			
			tc.homeTank = new Tank1(750, 550, true, 1,Direction.STOP, tc);//设置自己出现的位置
			tc.homeTank2 = new Tank1(50, 550, false, 2,Direction.STOP, tc);
			
			/*
			if (!tc.home.isLive())  //将home重置生命
				tc.home.setLive(true);
			*/
			new TankClient1(); //重新创建面板
			break;
		case KeyEvent.VK_RIGHT: //监听向右键
			if(this.z==1)dir_Right = true;
			break;
			
		case KeyEvent.VK_LEFT://监听向左键
			if(this.z==1)dir_Left = true;
			break;
		
		case KeyEvent.VK_UP:  //监听向上键
			if(this.z==1)dir_Up = true;
			break;
		
		case KeyEvent.VK_DOWN://监听向下键
			if(this.z==1)dir_Down = true;
			break;
		case KeyEvent.VK_D: //监听向右键
			if(this.z==2)dir_Right = true;
			break;
			
		case KeyEvent.VK_A://监听向左键
			if(this.z==2)dir_Left = true;
			break;
		
		case KeyEvent.VK_W:  //监听向上键
			if(this.z==2)dir_Up = true;
			break;
		
		case KeyEvent.VK_S://监听向下键
			if(this.z==2)dir_Down = true;
			break;
		}
		locateDirection();//调用函数确定移动方向
	}

	public void locateDirection() {
		if( dir_Left&&!dir_Right&&!dir_Down&&!dir_Up) Dir = Direction.L;
		else if(!dir_Left&& dir_Right&&!dir_Down&&!dir_Up) Dir = Direction.R;
		else if(!dir_Left&&!dir_Right&& dir_Down&&!dir_Up) Dir = Direction.D;
		else if(!dir_Left&&!dir_Right&&!dir_Down&& dir_Up) Dir = Direction.U;
		else if( dir_Left&&!dir_Right&& dir_Down&&!dir_Up) Dir = Direction.LD;
		else if( dir_Left&&!dir_Right&&!dir_Down&& dir_Up) Dir = Direction.LU;
		else if(!dir_Left&& dir_Right&& dir_Down&&!dir_Up) Dir = Direction.RD;
		else if(!dir_Left&& dir_Right&&!dir_Down&& dir_Up) Dir = Direction.RU;
		else if(!dir_Left&&!dir_Right&&!dir_Down&&!dir_Up) Dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {  //键盘释放监听
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_CONTROL:
			if(this.z==1)fire();
			break;
			
		case KeyEvent.VK_SPACE:
			if(this.sf_count > 0 && this.z==1) {superFire(); sf_count--;}
			break;	
			
		case KeyEvent.VK_RIGHT:
			if(this.z==1)dir_Right = false;
			break;
		
		case KeyEvent.VK_LEFT:
			if(this.z==1)dir_Left = false;
			break;
		
		case KeyEvent.VK_UP:
			if(this.z==1)dir_Up = false;
			break;
		
		case KeyEvent.VK_DOWN:
			if(this.z==1)dir_Down = false;
			break;
			
		case KeyEvent.VK_F:
			if(this.z==2)fire();
			break;
			
		case KeyEvent.VK_G:
			if(this.sf_count > 0 && this.z==2) {superFire(); sf_count--;}
			break;	
			
		case KeyEvent.VK_D:
			if(this.z==2)dir_Right = false;
			break;
		
		case KeyEvent.VK_A:
			if(this.z==2)dir_Left = false;
			break;
		
		case KeyEvent.VK_W:
			if(this.z==2)dir_Up = false;
			break;
		
		case KeyEvent.VK_S:
			if(this.z==2)dir_Down = false;
			break;
		}
		locateDirection();  //释放键盘后确定移动方向
	}

	public Bullets1 fire() {  //开火方法
		if (!live)
			return null;
		int x = this.x + Tank1.width / 2 - 37;  //开火位置
		int y = this.y + Tank1.length / 2 - 37;
		Bullets1 m = new Bullets1(x+2, y+2, good, ptDir, this.tc);  //没有给定方向时，向原来的方向发火
		Flame f = null;
		switch(ptDir) {
		case L:
			f  = new Flame(x -10, y + 22, ptDir, this.tc);
			break;
		case R:
			f  = new Flame(x + 3*Tank1.width/2+10, y + 22, ptDir, this.tc);
			break;
		case U:
			f  = new Flame(x+22 , y - Tank1.length/2+8, ptDir, this.tc);
			break;
		case D:
			f  = new Flame(x + 22, y + Tank1.length+25, ptDir, this.tc);
			break;
		case LU:
			f  = new Flame(x , y , ptDir, this.tc);
			break;
		case LD:
			f  = new Flame(x , y +50, ptDir, this.tc);
			break;
		case RD:
			f  = new Flame(x + Tank1.width+10, y + Tank1.length+10, ptDir, this.tc);
			break;
		case RU:
			f  = new Flame(x + Tank1.width+10, y + 10, ptDir, this.tc);
			break;
		}
		
		tc.bullets.add(m);   
		tc.flames.add(f);
		return m;
	}
	
	public Bullets1 fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank1.width / 2 - 37;  //开火位置
		int y = this.y + Tank1.length / 2 - 37;
		Bullets1 m = new Bullets1(x,y, good, dir,this.tc);
		tc.bullets.add(m);
		
		return m;
	}
	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
	}


	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(CommonWall w) {  //碰撞到普通墙时
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    //转换到原来的方向上去
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {  //撞到金属墙
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	public boolean collideRiver(List<River> theRiver) {    //撞到河流的时候
		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
				if (this.live && this.getRect().intersects(r.getRect())) {
					//this.changToOldDir();
					return true;
				}
		}		
			return false;
	}

	public boolean collideHome(Home1 h) {   //撞到家的时候
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	/*
	public boolean collideWithTanks(java.util.List<Tank> tanks) {//撞到坦克时
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}
     */
	public boolean collideWithTank(Tank1 t) {//撞到坦克时
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		return false;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/*
	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}
	*/

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      //每吃一个，增加100生命点
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}
	public boolean eat_sf(GetSF sf) {
		if (this.live && sf.isLive() && this.getRect().intersects(sf.getRect())) {
			this.sf_count += 3;
			sf.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}