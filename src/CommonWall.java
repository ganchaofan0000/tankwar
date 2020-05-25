import java.awt.*;

public class CommonWall {
	public static final int width = 22; //����ǽ�Ĺ̶�����
	public static final int length = 22;
	int x, y;

	START tc;
	TankClient1 tc1;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { // ����commonWall��ͼƬ
		tk.getImage(CommonWall.class.getResource("Images/commenWall.png")), };
	}

	public CommonWall(int x, int y, START tc) { // ���캯��
		this.x = x;
		this.y = y;
		this.tc = tc;// ��ý������
	}

	public CommonWall(int x, int y, TankClient1 tc1) { // ���캯��
		this.x = x;
		this.y = y;
		this.tc1 = tc1;// ��ý������
	}

	public void draw(Graphics g) {// ��commonWall
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {  //����ָ�������ĳ�����ʵ��
		return new Rectangle(x, y, width, length);
	}
}
