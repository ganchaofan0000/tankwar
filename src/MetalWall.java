import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall {
	public static final int width = 30; // ���ý���ǽ�ĳ�����̬ȫ�ֲ���
	public static final int length = 30;
	private int x, y;
	START tc;
	TankClient1 tc1;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/metalWall.png")), };
	}

	public MetalWall(int x, int y, START tc) {// ���캯��������Ҫ����ĳ�������ֵ
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public MetalWall(int x, int y, TankClient1 tc1) {// ���캯��������Ҫ����ĳ�������ֵ
		this.x = x;
		this.y = y;
		this.tc1 = tc1;
	}

	public void draw(Graphics g) { // ������ǽ
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() { // ����ָ�������ĳ�����ʵ��
		return new Rectangle(x, y, width, length);
	}
}