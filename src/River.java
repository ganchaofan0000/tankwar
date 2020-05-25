import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class River {
	public static final int riverWidth = 138;
	public static final int riverLength = 57;//��̬ȫ�ֱ���
	private int x, y;
	START tc ;
	TankClient1 tc1;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	
	static {   //�洢ͼƬ
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.png")),
		};
	}
	
	
	public River(int x, int y, START tc) {    //River�Ĺ��췽��
		this.x = x;
		this.y = y;
		this.tc = tc;             //��ÿ���
	}
	
	public River(int x, int y, TankClient1 tc1) {    //River�Ĺ��췽��
		this.x = x;
		this.y = y;
		this.tc1 = tc1;             //��ÿ���
	}

	public void draw(Graphics g) {
		g.drawImage(riverImags[0],x, y, null);            //�ڶ�ӦX��Y������
	}
	public static int getRiverWidth() {
		return riverWidth;
	}

	public static int getRiverLength() {
		return riverLength;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
