import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank {
	public static  int XSPEED = 5, YSPEED =5; // ��̬ȫ�ֱ����ٶ�
	public static int count = 0;
	public static final int width = 34, length = 35; // ̹�˵�ȫ�ִ�С�����в��ɸı���
	public static int score = 0;
	private Direction Dir = Direction.STOP; // ��ʼ��״̬Ϊ��ֹ
	private Direction ptDir = Direction.U; // ��ʼ������Ϊ����
	START tc;

	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; // ��ʼ��Ϊ����
	private int life = 200; // ��ʼ����ֵ
	private int sf_count = 0;
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ; // ����һ�������,���ģ��̹�˵��ƶ�·��

	private boolean dir_Left = false, dir_Right = false, dir_Up = false, dir_Down = false;
	

	private static Toolkit tk = Toolkit.getDefaultToolkit();// �������
	private static Image[] tankImages = null; // �洢ȫ�־�̬
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImages = new Image[] {
				tk.getImage(Tank.class.getResource("Images/tankD.png")),
				tk.getImage(Tank.class.getResource("Images/tankU.png")),
				tk.getImage(Tank.class.getResource("Images/tankL.png")),
				tk.getImage(Tank.class.getResource("Images/tankR.png")),
				tk.getImage(Tank.class.getResource("Images/tankRU.png")),
				tk.getImage(Tank.class.getResource("Images/tankRD.png")),
				tk.getImage(Tank.class.getResource("Images/tankLU.png")),
				tk.getImage(Tank.class.getResource("Images/tankLD.png")),};
		
		imgs.put("D",tankImages[0]);
		imgs.put("U",tankImages[1]);
		imgs.put("L",tankImages[2]);
		imgs.put("R",tankImages[3]);
		imgs.put("RU",tankImages[4]);
		imgs.put("RD",tankImages[5]);
		imgs.put("LU",tankImages[6]);
		imgs.put("LD",tankImages[7]);

	}

	public Tank(int x, int y, boolean good) {// Tank�Ĺ��캯��1
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, START tc) {// Tank�Ĺ��캯��2
		this(x, y, good);
		this.Dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); // ɾ����Ч��
			}
			return;
		}

		if (good)
			new DrawBloodbBar().draw(g); // ����һ��Ѫ��

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

		move();   //����move����
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
		if (y < 40)      //��ֹ�߳��涨����
			y = 40;
		if (x + Tank.width > START.Fram_width)  //����������ָ����߽�
			x = START.Fram_width - Tank.width;
		if (y + Tank.length > START.Fram_length)
			y = START.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  //�������·��
				int rn = r.nextInt(directons.length);
				Dir = directons[rn];      //�����������
			}
			step--;

			if (r.nextInt(40) > 38)//��������������Ƶ��˿���,�ɵ��ڸ���Ӵ��Ѷ�
				this.fire();
		}
	}

	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {  //���ܼ����¼�
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  //������Rʱ�����¿�ʼ��Ϸ 
			tc.tanks.clear();  //����
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			if (tc.tanks.size() == 0) {   //����������û��̹��ʱ���ͳ���̹��      
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              //����̹�˳��ֵ�λ��
						tc.tanks.add(new Tank(150 + 70 * i,40, false, Direction.R, tc));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i -6), false, Direction.D, tc));
					else
						tc.tanks.add(new Tank(10,  50 * (i - 12), false, Direction.L, tc));
				}
			}
			
			tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc);//�����Լ����ֵ�λ��
			
			if (!tc.home.isLive())  //��home��������
				tc.home.setLive(true);
			new START(); //���´������
			break;
		case KeyEvent.VK_RIGHT: //�������Ҽ�
			dir_Right = true;
			break;
			
		case KeyEvent.VK_LEFT://���������
			dir_Left = true;
			break;
		
		case KeyEvent.VK_UP:  //�������ϼ�
			dir_Up = true;
			break;
		
		case KeyEvent.VK_DOWN://�������¼�
			dir_Down = true;
			break;
		}
		locateDirection();//���ú���ȷ���ƶ�����
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

	public void keyReleased(KeyEvent e) {  //�����ͷż���
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_F:
			fire();
			this.tc.music.play_bullet();
			break;
			
		case KeyEvent.VK_SPACE:
			if(this.sf_count > 0) {superFire(); sf_count--;this.tc.music.play_bullet();}
			break;	
			
		case KeyEvent.VK_RIGHT:
			dir_Right = false;
			this.tc.music.play_move();
			break;
		
		case KeyEvent.VK_LEFT:
			dir_Left = false;
			this.tc.music.play_move();
			break;
		
		case KeyEvent.VK_UP:
			dir_Up = false;
			this.tc.music.play_move();
			break;
		
		case KeyEvent.VK_DOWN:
			dir_Down = false;
			this.tc.music.play_move();
			break;
			

		}
		locateDirection();  //�ͷż��̺�ȷ���ƶ�����
	}

	public Bullets fire() {  //���𷽷�
		if (!live)
			return null;
		int x = this.x + Tank.width / 2 - 37;  //����λ��
		int y = this.y + Tank.length / 2 - 37;
		Bullets m = new Bullets(x+2, y+2, good, ptDir, this.tc);  //û�и�������ʱ����ԭ���ķ��򷢻�
		Flame f = null;
		switch(ptDir) {
		case L:
			f  = new Flame(x -10, y + 22, ptDir, this.tc);
			break;
		case R:
			f  = new Flame(x + 3*Tank.width/2+10, y + 22, ptDir, this.tc);
			break;
		case U:
			f  = new Flame(x+22 , y - Tank.length/2+8, ptDir, this.tc);
			break;
		case D:
			f  = new Flame(x + 22, y + Tank.length+25, ptDir, this.tc);
			break;
		case LU:
			f  = new Flame(x , y , ptDir, this.tc);
			break;
		case LD:
			f  = new Flame(x , y +50, ptDir, this.tc);
			break;
		case RD:
			f  = new Flame(x + Tank.width+10, y + Tank.length+10, ptDir, this.tc);
			break;
		case RU:
			f  = new Flame(x + Tank.width+10, y + 10, ptDir, this.tc);
			break;
		}
		
		tc.bullets.add(m);   
		tc.flames.add(f);
		return m;
	}
	
	public Bullets fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.width / 2 - 37;  //����λ��
		int y = this.y + Tank.length / 2 - 37;
		Bullets m = new Bullets(x,y, good, dir,this.tc);
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

	public boolean collideWithWall(CommonWall w) {  //��ײ����ͨǽʱ
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    //ת����ԭ���ķ�����ȥ
			 this.Dir = tc.RandomDir();
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {  //ײ������ǽ
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();  
			this.Dir = tc.RandomDir();
			return true;
		}
		return false;
	}

	public boolean collideRiver(List<River> theRiver) {    //ײ��������ʱ��
		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
				if (this.live && this.getRect().intersects(r.getRect())) {
					//this.changToOldDir();
					return true;
				}
		}		
			return false;
	}

	public boolean collideHome(Home h) {   //ײ���ҵ�ʱ��
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			this.Dir = tc.RandomDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {//ײ��̹��ʱ
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					this.Dir = tc.RandomDir();
					t.Dir = tc.RandomDir();
					return true;
				}
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

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      //ÿ��һ��������100������
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
	public void attack(Tank t) 
	 {
	 if(this.x!=tc.homeTank.x&&this.y!=tc.homeTank.y)
	 {
		 if((this.x-tc.homeTank.x)> 0&&(this.y-tc.homeTank.y)> 0)			//�з�̹�����ҷ�̹�˵����½�
			{
			 this.Dir = Direction.U;
			 this.ptDir = this.Dir;
			 }
		 else if((this.x-tc.homeTank.x)< 0&&(this.y-tc.homeTank.y)> 0)		//�з�̹�����ҷ�̹�˵����½�
		 {
			 this.Dir = Direction.R;
			 this.ptDir = this.Dir;
		 }
		 else if((this.x-tc.homeTank.x)< 0&&(this.y-tc.homeTank.y)< 0)		//�з�̹�����ҷ�̹�˵����Ͻ�
		 {
			 this.Dir = Direction.D;
			 this.ptDir = this.Dir;
		 }
		 else if((this.x-tc.homeTank.x)> 0&&(this.y-tc.homeTank.y)< 0)		////�з�̹�����ҷ�̹�˵����Ͻ�
		 {
			 this.Dir = Direction.L;
			 this.ptDir = this.Dir;
		 }
		 
	 }
	 	else fire(); 
	 for (int j = 0; j < tc.otherWall.size(); j++) {
		 CommonWall cw = tc.otherWall.get(j);
		 if (this.live && this.getRect().intersects(cw.getRect()))
		 {
			 if(this.Dir == Direction.D||this.Dir == Direction.U)
			 {
				 this.Dir = tc.RandomDir();
				 this.ptDir = this.Dir;
			 }
			 if(this.Dir == Direction.R||this.Dir == Direction.L)
			 {
				 this.Dir = tc.RandomDir();
				 this.ptDir = this.Dir;
			 }
		 }
	 }
	 }
	
	public void avoid(List<Bullets> bullets, Tank t)	
	 {
		 for(int i = 0;i< bullets.size();i++)
		 {
			Bullets m = bullets.get(i);
			 	if(m.isGood())
			 	{
			 		if(m.isLive()&&m.dir== Direction.L &&((m.getX()-t.x)> 0) &&Math.abs(m.getY() - t.y)<length)	//�ӵ���̹�˵��ұߴ����
			 	{	 		
			 		
			 		t.Dir = Direction.D;
			 		this.ptDir = Direction.D;		 
			 	}
			 	else if(m.isLive()&&m.dir== Direction.R &&((m.getX()-t.x)< 0) &&Math.abs(m.getY() - t.y)<length)//�ӵ���̹�˵���ߴ����
			 	{
//			 		if(t.y> (tc.GAME_WIDTH-60) )
//			 			{
//			 			t.dir = Direction.U;
//			 			t.y-= 30;
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
//			 			}
//			 		else {
//			 			t.dir = Direction.D;
//			 			t.y+=  30;		 
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
			 		t.Dir = Direction.D;
			 		this.ptDir = Direction.D;
//			 		}
			 	}
			 	else if(m.isLive()&&m.dir== Direction.U &&((m.getY()-t.y)> 0) &&Math.abs(m.getX() - t.x)<width)//�ӵ���̹�˵���������
			 	{
//			 		if(t.x> tc.GAME_HEIGHT-60)
//			 			{
//			 			t.dir = Direction.L;
//			 			t.x-=5;			 
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
//			 			}
//			 		else {			 			
//			 			t.dir = Direction.R;
//			 			t.x+=	5;			 
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
//			 		}
			 		t.Dir = Direction.R;
			 		this.ptDir = Direction.R;
			 
			 	}
			 	else if(m.isLive()&&m.dir== Direction.D &&((m.getY()-t.y)< 0) &&Math.abs(m.getX() - t.x)<width)//�ӵ���̹�˵���������
			 	{
//			 		if(t.x<50)
//			 		{
//			 			t.dir = Direction.R;
//			 			t.x+=5;
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
//			 		}
//			 		else 
//			 			{
//			 			t.dir = Direction.L;
//			 			t.x-= 5;	 			
//			 			this.prDir = this.dir;
//			 			t.dir = Direction.STOP;
//			 			}
			 		t.Dir = Direction.R;
			 		this.ptDir = Direction.R;
			 	}
			 	}
		 }
	 }
}