import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient1 extends Frame implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; // ��̬ȫ�ִ��ڴ�С
	public static final int Fram_length = 600;
	public static boolean printable = true;
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	Image screenImage = null;

	Tank1 homeTank = new Tank1(750, 550, true, 1,Direction.STOP, this);
	Tank1 homeTank2 = new Tank1(50, 550, false,2, Direction.STOP, this);// ʵ����̹��
	GetBlood blood = new GetBlood(); // ʵ��������
	GetSF superfire = new GetSF(); //ʵ���������ڵ�
	/*Home home = new Home(373, 545, this);// ʵ����home*/
	private static Toolkit tk = Toolkit.getDefaultToolkit();//����ͼƬ����
	private static Image backImage = tk.getImage(TankClient1.class.getClassLoader().getResource("Images/back.jpg"));
	
	List<Flame> flames = new ArrayList<Flame>();
	List<River> theRiver = new ArrayList<River>();
	/*List<Tank> tanks = new ArrayList<Tank>();*/
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	List<Bullets1> bullets = new ArrayList<Bullets1>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>(); // ʵ������������
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();
	
	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);
		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GREEN);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void framPaint(Graphics g) {
		g.drawImage(backImage, 0, 0, null);
		Color c = g.getColor();
		g.setColor(Color.GREEN); // ����������ʾ����

		Font f1 = g.getFont();
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("player2ʣ������ֵ: ", 400, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + homeTank.getLife(), 590, 70);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("player1ʣ������ֵ: ", 100, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + homeTank2.getLife(), 290, 70);
		g.setFont(f1);

		if (homeTank.isLive() == false||homeTank2.isLive() == false) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			bullets.clear();
			g.setFont(f);
		}
		g.setColor(c);

		for (int i = 0; i < theRiver.size(); i++) { // ��������
			River r = theRiver.get(i);
			r.draw(g);
		}

		homeTank.collideRiver(theRiver);
		homeTank2.collideRiver(theRiver);
//		for (int i = 0; i < theRiver.size(); i++) {// ������ײ���
//			River r = theRiver.get(i);
//			homeTank.collideRiver(r);
//
//			r.draw(g);
//		}
		
		for(int i = 0; i < flames.size(); i++) {//������ը����
			Flame f = flames.get(i);
			f.draw(g);
		}

		homeTank.draw(g);// �����Լ��ҵ�̹��
		homeTank.eat(blood);// ����Ѫ--����ֵ
		homeTank.eat_sf(superfire);
		homeTank2.draw(g);// �����Լ��ҵ�̹��
		homeTank2.eat(blood);// ����Ѫ--����ֵ
		homeTank2.eat_sf(superfire);
		
		for (int i = 0; i < bullets.size(); i++) { // ��ÿһ���ӵ�
			Bullets1 m = bullets.get(i);
			m.hitTank(homeTank); // ÿһ���ӵ����Լ��ҵ�̹����ʱ
			m.hitTank(homeTank2);

			for (int j = 0; j < metalWall.size(); j++) { // ÿһ���ӵ��򵽽���ǽ��
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {// ÿһ���ӵ�������ǽ��
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {// ÿһ���ӵ��򵽼ҵ�ǽ��
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); // ����Ч��ͼ
		}

		blood.draw(g);
		superfire.draw(g);

		for (int i = 0; i < trees.size(); i++) { // ����trees
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < bombTanks.size(); i++) { // ������ըЧ��
			BombTank bt = bombTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { // ����otherWall
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) { // ����metalWall
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		homeTank.collideWithTank(homeTank2);


		for (int i = 0; i < metalWall.size(); i++) {// ײ������ǽ
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			homeTank2.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			homeTank2.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) { // �����̹��ײ���Լ���
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			homeTank2.collideWithWall(w);
			w.draw(g);
		}
		if(!homeTank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font(" ", Font.PLAIN, 40));
			g.drawString("Player1 Win!!!��", 220, 250);
			g.setFont(f);
		}
		if(!homeTank2.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font(" ", Font.PLAIN, 40));
			g.drawString("Player2 Win!!!��", 220, 250);
			g.setFont(f);
		}
	}

	public TankClient1() {
		// �����˵����˵�ѡ��
		jmb = new MenuBar();
		jm1 = new Menu("��Ϸ");
		jm2 = new Menu("��ͣ/����");
		jm3 = new Menu("����");
		jm4 = new Menu("��Ϸ����");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));// ���ò˵���ʾ������
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));// ���ò˵���ʾ������
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));// ���ò˵���ʾ������
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));// ���ò˵���ʾ������

		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("EXIT");
		jmi3 = new MenuItem("PAUSE");
		jmi4 = new MenuItem("CONTINUE");
		jmi5 = new MenuItem("HELP");
		jmi6 = new MenuItem("LEVEL1");
		jmi7 = new MenuItem("LEVEL2");
		jmi8 = new MenuItem("LEVEL3");
		jmi9 = new MenuItem("LEVEL4");
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

		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm4);
		jmb.add(jm3);

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

		this.setMenuBar(jmb);// �˵�Bar�ŵ�JFrame��
		this.setVisible(true);

		for (int i = 0; i < 10; i++) { // �ҵĸ��
			if (i < 4)
				homeWall.add(new CommonWall(350, 580 - 21 * i, this));
			else if (i < 7)
				homeWall.add(new CommonWall(372 + 22 * (i - 4), 517, this));
			else
				homeWall.add(new CommonWall(416, 538 + (i - 7) * 21, this));

		}
		
		
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new CommonWall(220 + 20 * i, 300, this)); // ��ͨǽ����
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
	

//		for (int i = 0; i < 20; i++) { // ����ǽ����
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
		}														//����H
		for(int i = 0; i < 7; i++) {
			trees.add(new Tree(150+7*22, 100 + 22*i, this));
			trees.add(new Tree(216+7*22, 100 + 22*i, this));
		}
		for(int i = 0; i < 2; i++) {
			trees.add(new Tree(172+22*i+7*22, 166+3*22, this));
		}															//����S
		for(int i = 0; i < 3; i++) {
			trees.add(new Tree(150+14*22, 100 + 22*i, this));
			trees.add(new Tree(216+14*22, 100 + 22*i+4*22, this));
		}
		for(int i = 0; i < 4; i++) {
			trees.add(new Tree(172+22*i+13*22, 100, this));
			trees.add(new Tree(172+22*i+13*22, 166, this));
			trees.add(new Tree(172+22*i+13*22, 166+3*22, this));
		}																//����T
		for(int i = 0; i < 5; i++) {
			trees.add(new Tree(150+22*i+20*22, 100, this));
		}
		for(int i = 0; i < 6; i++) {
			trees.add(new Tree(172+22*21, 122+22*i, this));
		}
		
//		for (int i = 0; i < 4; i++) { // ���Ĳ���
//			if (i < 4) {
//				trees.add(new Tree(0 + 30 * i, 360, this));
//				trees.add(new Tree(220 + 30 * i, 360, this));
//				trees.add(new Tree(440 + 30 * i, 360, this));
//				trees.add(new Tree(660 + 30 * i, 360, this));
//			}
//
//		}

		theRiver.add(new River(40, 350, this));   //���Ӻ���
		theRiver.add(new River(320, 350, this));
		theRiver.add(new River(620, 350, this));
		this.setSize(Fram_width, Fram_length); // ���ý����С
		this.setLocationRelativeTo(null);
		this.setTitle("TankWar��(���¿�ʼ��R��  ����F��)   �İ�����");
		this.addWindowListener(new WindowAdapter() { // ���ڼ����ر�
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GRAY);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());// ���̼���
		new Thread(new PaintThread()).start(); // �߳�����
	}

	public static void main(String[] args) {
		new TankClient1(); // ʵ����
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

		public void keyReleased(KeyEvent e) { // ���������ͷ�
			homeTank.keyReleased(e);
			homeTank2.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { // �������̰���
			homeTank.keyPressed(e);
			homeTank2.keyPressed(e);
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
				new TankClient1();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // �߳�����
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
				new Thread(new PaintThread()).start(); // �߳�����
			}
			// System.out.println("����");
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
				new Thread(new PaintThread()).start(); // �߳�����

			}

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "�á� �� �� �����Ʒ���F���̷��䣬�ո񳬼����棬����ɫ��Դ�ָ�����ֵ����ɫ��������������棬R���¿�ʼ��",
					"��ʾ��", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // �߳�����
		} else if (e.getActionCommand().equals("level1")) {
			Tank1.count = 12;
			Tank1.XSPEED = 6;
			Tank1.YSPEED = 6;
			Bullets1.XSPEED = 10;
			Bullets1.YSPEED = 10;
			this.dispose();
			new TankClient1();
		} else if (e.getActionCommand().equals("level2")) {
			Tank1.count = 12;
			Tank1.XSPEED = 10;
			Tank1.YSPEED = 10;
			Bullets1.XSPEED = 12;
			Bullets1.YSPEED = 12;
			this.dispose();
			new TankClient1();

		} else if (e.getActionCommand().equals("level3")) {
			Tank1.count = 20;
			Tank1.XSPEED = 14;
			Tank1.YSPEED = 14;
			Bullets1.XSPEED = 16;
			Bullets1.YSPEED = 16;
			this.dispose();
			new TankClient1();
		} else if (e.getActionCommand().equals("level4")) {
			Tank1.count = 20;
			Tank1.XSPEED = 16;
			Tank1.YSPEED = 16;
			Bullets1.XSPEED = 18;
			Bullets1.YSPEED = 18;
			this.dispose();
			new TankClient1();
		}
	}
}
