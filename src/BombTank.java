import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BombTank {
	private int x, y;
	private boolean live = true; // ��ʼ״̬Ϊ���ŵ�
	private START tc;
	private TankClient1 tc1;
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs = { // �洢��ըͼƬ ��С����ı�ըЧ��ͼ
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e0.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e1.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e2.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e3.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e4.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e5.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e6.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource("Images/e7.gif")),
			};
	int step = 0;

	public BombTank(int x, int y, START tc) { // ���캯��
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public BombTank(int x, int y, TankClient1 tc1) { // ���캯��
		this.x = x;
		this.y = y;
		this.tc1 = tc1;
	}

	public void draw(Graphics g) { // ������ըͼ��

		if (!live) { // ̹����ʧ��ɾ����ըͼ
			tc.bombTanks.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
