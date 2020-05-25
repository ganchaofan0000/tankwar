import java.awt.*;

public class CommonWall {
	public static final int width = 22; //设置墙的固定参数
	public static final int length = 22;
	int x, y;

	START tc;
	TankClient1 tc1;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { // 储存commonWall的图片
		tk.getImage(CommonWall.class.getResource("Images/commenWall.png")), };
	}

	public CommonWall(int x, int y, START tc) { // 构造函数
		this.x = x;
		this.y = y;
		this.tc = tc;// 获得界面控制
	}

	public CommonWall(int x, int y, TankClient1 tc1) { // 构造函数
		this.x = x;
		this.y = y;
		this.tc1 = tc1;// 获得界面控制
	}

	public void draw(Graphics g) {// 画commonWall
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {  //构造指定参数的长方形实例
		return new Rectangle(x, y, width, length);
	}
}
