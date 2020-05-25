import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class START extends Frame implements ActionListener {
	public static int choice;
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; // 静态全局窗口大小
	public static final int Fram_length = 600;
	public static boolean printable = true;
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null, jm5 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null, jmi10, jmi11, jmi12;
	Image screenImage = null;
	
	Tank homeTank = new Tank(300, 560, true, Direction.STOP, this);// 实例化坦克
	GetBlood blood = new GetBlood(); // 实例化生命
	GetSF superfire = new GetSF(); //实例化超级炮弹
	Home home = new Home(373, 545, this);// 实例化home
	Music music = new Music();
	private static Random r = new Random();
	private static Toolkit tk = Toolkit.getDefaultToolkit();//背景图片加载
	private static Image backImage = tk.getImage(START.class.getClassLoader().getResource("Images/back.jpg"));
	public Direction RandomDir() {
		Direction[] directons = Direction.values();
		int a = r.nextInt(directons.length);
		      //产生随机方向
		return directons[a];
	}
	List<Flame> flames = new ArrayList<Flame>();
	List<River> theRiver = new ArrayList<River>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>(); // 实例化对象容器
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);
		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GRAY);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void framPaint(Graphics g) {
		g.drawImage(backImage, 0, 0, null);
		Color c = g.getColor();
		g.setColor(Color.green); // 设置字体显示属性

		Font f1 = g.getFont();
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("得分 ", 200, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + Tank.score, 250, 70);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("剩余生命值: ", 500, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + homeTank.getLife(), 650, 70);
		g.setFont(f1);

		if (tanks.size() == 0 && home.isLive() && homeTank.isLive()) {
			for (int i = 0; i < 20; i++) { // 初始化20辆坦克
				if (i < 9) // 设置坦克出现的位置
					tanks.add(new Tank(150 + 70 * i, 40, false, Direction.D, this));
				else if (i < 15)
					tanks.add(new Tank(700, 140 + 50 * (i - 6), false,  Direction.D,this));
				else
					tanks.add(new Tank(10, 50 * (i - 12), false, Direction.D,this));
			}
//			Font f = g.getFont();
//			g.setFont(new Font("TimesRoman", Font.BOLD, 60)); // 判断是否赢得比赛
//			this.otherWall.clear();
//			g.drawString("VICTORY! ", 310, 300);
//			g.setFont(f);
		}

		if (homeTank.isLive() == false) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			tanks.clear();
			bullets.clear();
			g.setFont(f);
		}
		g.setColor(c);

		for (int i = 0; i < theRiver.size(); i++) { // 画出河流
			River r = theRiver.get(i);
			r.draw(g);
		}

		homeTank.collideRiver(theRiver);
//		for (int i = 0; i < theRiver.size(); i++) {// 河流碰撞检测
//			River r = theRiver.get(i);
//			homeTank.collideRiver(r);
//
//			r.draw(g);
//		}
		
		for(int i = 0; i < flames.size(); i++) {//画出爆炸火焰
			Flame f = flames.get(i);
			f.draw(g);
		}

		home.draw(g); // 画出home
		homeTank.draw(g);// 画出自己家的坦克
		homeTank.eat(blood);// 充满血--生命值
		homeTank.eat_sf(superfire);

		for (int i = 0; i < bullets.size(); i++) { // 对每一个子弹
			Bullets m = bullets.get(i);
			m.hitTanks(tanks); // 每一个子弹打到坦克上
			m.hitTank(homeTank); // 每一个子弹打到自己家的坦克上时
			m.hitHome(); // 每一个子弹打到家里是

			for (int j = 0; j < metalWall.size(); j++) { // 每一个子弹打到金属墙上
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {// 每一个子弹打到其他墙上
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {// 每一个子弹打到家的墙上
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); // 画出效果图
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i); // 获得键值对的键

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				t.collideWithWall(cw); // 每一个坦克撞到家里的墙时
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) { // 每一个坦克撞到家以外的墙
				CommonWall cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < metalWall.size(); j++) { // 每一个坦克撞到金属墙
				MetalWall mw = metalWall.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			
			t.collideRiver(theRiver);
//			for (int j = 0; j < theRiver.size(); j++) {
//				River r = theRiver.get(j); // 每一个坦克撞到河流时
//				t.collideRiver(r);
//				r.draw(g);
//				// t.draw(g);
//			}

			t.collideWithTanks(tanks); // 撞到自己的人
			t.collideHome(home);

			if(i%4!=0)
				t.avoid(bullets, t);
			if(i%4==0)
				t.attack(t);
			
			t.draw(g);
		}

		blood.draw(g);
		superfire.draw(g);

		for (int i = 0; i < trees.size(); i++) { // 画出trees
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < bombTanks.size(); i++) { // 画出爆炸效果
			BombTank bt = bombTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { // 画出otherWall
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) { // 画出metalWall
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		homeTank.collideWithTanks(tanks);
		homeTank.collideHome(home);

		for (int i = 0; i < metalWall.size(); i++) {// 撞到金属墙
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) { // 家里的坦克撞到自己家
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

	}

	public START() {
		// 创建菜单及菜单选项
		jmb = new MenuBar();
		jm1 = new Menu("游戏");
		jm2 = new Menu("暂停/继续");
		jm3 = new Menu("帮助");
		jm4 = new Menu("游戏级别");
		jm5 = new Menu("背景音乐");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm5.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("EXIT");
		jmi3 = new MenuItem("PAUSE");
		jmi4 = new MenuItem("CONTINUE");
		jmi5 = new MenuItem("HELP");
		jmi6 = new MenuItem("LEVEL1");
		jmi7 = new MenuItem("LEVEL2");
		jmi8 = new MenuItem("LEVEL3");
		jmi9 = new MenuItem("LEVEL4");
		jmi10 = new MenuItem("START");
		jmi11 = new MenuItem("CIRCLE");
		jmi12 = new MenuItem("STOP");
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);
		jm5.add(jmi10);
		jm5.add(jmi11);
		jm5.add(jmi12);
		
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm3);
		jmb.add(jm4);
		jmb.add(jm5);

		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");
		jmi10.addActionListener(this);
		jmi10.setActionCommand("StartM");
		jmi11.addActionListener(this);
		jmi11.setActionCommand("CircleM");
		jmi12.addActionListener(this);
		jmi12.setActionCommand("StopM");

		this.setMenuBar(jmb);// 菜单Bar放到JFrame上
		this.setVisible(true);

		for (int i = 0; i < 10; i++) { // 家的格局
			if (i < 4)
				homeWall.add(new CommonWall(350, 580 - 21 * i, this));
			else if (i < 7)
				homeWall.add(new CommonWall(372 + 22 * (i - 4), 517, this));
			else
				homeWall.add(new CommonWall(416, 538 + (i - 7) * 21, this));

		}
		
		
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new CommonWall(220 + 20 * i, 300, this)); // 普通墙布局
			//	otherWall.add(new CommonWall(500 + 20 * i, 180, this));
				otherWall.add(new CommonWall(200, 400 + 20 * i, this));
				otherWall.add(new CommonWall(500, 400 + 20 * i, this));
			} else if (i < 32) {
				otherWall.add(new CommonWall(220 + 20 * (i - 16), 320, this));
				//otherWall.add(new CommonWall(500 + 20 * (i - 16), 220, this));
				otherWall.add(new CommonWall(220, 400 + 20 * (i - 16), this));
				otherWall.add(new CommonWall(520, 400 + 20 * (i - 16), this));
			}
		}
		
		
		for(int i = 0; i < 10; i++) {
			metalWall.add(new MetalWall(600, 400 + 22 * (i), this));
			metalWall.add(new MetalWall(100, 400 + 20 * (i), this));
		}
	

//		for (int i = 0; i < 20; i++) { // 金属墙布局
//			if (i < 10) {
//				metalWall.add(new MetalWall(140 + 30 * i, 150, this));
// 				metalWall.add(new MetalWall(600, 400 + 20 * (i), this));
//			} else if (i < 20)
//				metalWall.add(new MetalWall(140 + 30 * (i - 10), 180, this));
//			else
//				metalWall.add(new MetalWall(500 + 30 * (i - 10), 160, this));
//		}

		
		for(int i = 0; i < 7; i++) {
			trees.add(new Tree(150, 100 + 22*i, this));
			trees.add(new Tree(216, 100 + 22*i, this));
		}
		for(int i = 0; i < 2; i++) {
			trees.add(new Tree(172+22*i, 166, this));
		}														//画出H
		for(int i = 0; i < 7; i++) {
			trees.add(new Tree(150+7*22, 100 + 22*i, this));
			trees.add(new Tree(216+7*22, 100 + 22*i, this));
		}
		for(int i = 0; i < 2; i++) {
			trees.add(new Tree(172+22*i+7*22, 166+3*22, this));
		}															//画出S
		for(int i = 0; i < 3; i++) {
			trees.add(new Tree(150+14*22, 100 + 22*i, this));
			trees.add(new Tree(216+14*22, 100 + 22*i+4*22, this));
		}
		for(int i = 0; i < 4; i++) {
			trees.add(new Tree(172+22*i+13*22, 100, this));
			trees.add(new Tree(172+22*i+13*22, 166, this));
			trees.add(new Tree(172+22*i+13*22, 166+3*22, this));
		}																//画出T
		for(int i = 0; i < 5; i++) {
			trees.add(new Tree(150+22*i+20*22, 100, this));
		}
		for(int i = 0; i < 6; i++) {
			trees.add(new Tree(172+22*21, 122+22*i, this));
		}
		
//		for (int i = 0; i < 4; i++) { // 树的布局
//			if (i < 4) {
//				trees.add(new Tree(0 + 30 * i, 360, this));
//				trees.add(new Tree(220 + 30 * i, 360, this));
//				trees.add(new Tree(440 + 30 * i, 360, this));
//				trees.add(new Tree(660 + 30 * i, 360, this));
//			}
//
//		}

		theRiver.add(new River(20, 350, this));   //增加河流
		theRiver.add(new River(320, 350, this));
		theRiver.add(new River(620, 350, this));
		for (int i = 0; i < 20; i++) { // 初始化20辆坦克
			if (i < 9) // 设置坦克出现的位置
				tanks.add(new Tank(150 + 70 * i, 40, false,RandomDir(), this));
			else if (i < 15)
				tanks.add(new Tank(700, 140 + 50 * (i - 6), false,  RandomDir(),this));
			else
				tanks.add(new Tank(10, 50 * (i - 12), false, RandomDir(),this));
		}

		this.setSize(Fram_width, Fram_length); // 设置界面大小
		this.setLocationRelativeTo(null);
		this.setTitle("TankWar—(重新开始：R键  开火：F键)   四班六组");
		this.addWindowListener(new WindowAdapter() { // 窗口监听关闭
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());// 键盘监听
		new Thread(new PaintThread()).start(); // 线程启动
	}

	public static void main(String[] args) {
		JFrame start = new JFrame();
		start.setSize(Fram_width, Fram_length);
		start.setLayout(null);
		start.setLocation(0,0);
		start.setTitle("TankWar");
		start.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		start.setLocationRelativeTo(null);	
		
		ImageIcon img = new ImageIcon("C:\\QQDownload\\music\\tank.jpg");
		JLabel jl_bg = new JLabel(img); //背景
		jl_bg.setBounds(-355, -138, 1500, 800);
		start.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));
		((JPanel)start.getContentPane()).setOpaque(false); 
		
		JButton button = new JButton("单人游戏");
		JButton button2 = new JButton("双人对战");
		button.setBounds(50, 350, 150, 50);
		button2.setBounds(50, 450, 150, 50);
		start.add(button);
		start.add(button2);
		start.setVisible(true);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				choice = 1;
				start.setVisible(false);
				new START(); // 实例化
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				choice = 2;
				start.setVisible(false);
				new TankClient1();
			}
		});
	}

	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) { // 监听键盘释放
			homeTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { // 监听键盘按下
			homeTank.keyPressed(e);
		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "YES", "NO" };
			int response = JOptionPane.showOptionDialog(this, "Restart?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new START();
				Tank.score = 0;
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
			// try {
			// Thread.sleep(10000);
			//
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}
			// System.out.println("继续");
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "YES", "NO" };
			int response = JOptionPane.showOptionDialog(this, "EXIT?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("EXIT");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动

			}

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，F键盘发射，空格超级火焰，吃蓝色能源恢复生命值，红色获得三发超级火焰，R重新开始！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		} else if (e.getActionCommand().equals("level1")) {
			Tank.count = 12;
			Tank.XSPEED = 6;
			Tank.YSPEED = 6;
			Bullets.XSPEED = 10;
			Bullets.YSPEED = 10;
			this.dispose();
			new START();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.count = 12;
			Tank.XSPEED = 10;
			Tank.YSPEED = 10;
			Bullets.XSPEED = 12;
			Bullets.YSPEED = 12;
			this.dispose();
			new START();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.count = 20;
			Tank.XSPEED = 14;
			Tank.YSPEED = 14;
			Bullets.XSPEED = 16;
			Bullets.YSPEED = 16;
			this.dispose();
			new START();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.count = 20;
			Tank.XSPEED = 16;
			Tank.YSPEED = 16;
			Bullets.XSPEED = 18;
			Bullets.YSPEED = 18;
			this.dispose();
			new START();
		}else if(e.getActionCommand().equals("StartM")) {
			music.start();
		}else if(e.getActionCommand().equals("CircleM")) {
			music.circle();
		}else if(e.getActionCommand().equals("StopM")) {
			music.stop();
		}
	}
}
