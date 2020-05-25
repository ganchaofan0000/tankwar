import java.awt.*;
import java.util.Random;

public class GetSF {
	
	public static final int width = 34;
	public static final int length = 31;

	private int x, y;
	START tc;
	private static Random r = new Random();

//	int step = 0; 
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] SFImags = null;
	static {
		SFImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/SF.png")), };
	}

//	private int[][] poition = { { 155, 196 }, { 500, 58 }, { 80, 340 },
//			{ 99, 199 }, { 345, 456 }, { 123, 321 }, { 258, 413 } };

	public void draw(Graphics g) {
		if (r.nextInt(200) > 198) {
			this.live = true;
			move();
		}
		if (!live)
			return;
		g.drawImage(SFImags[0], x, y, null);

	}

	private void move() {
//		step++;
//		if (step == poition.length) {
//			step = 0;
//		}
		x = r.nextInt(780);
		y = r.nextInt(580);
		
	}

	public Rectangle getRect() { //���س�����ʵ��
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {//�ж��Ƿ񻹻���
		return live;
	}

	public void setLive(boolean live) {  //��������
		this.live = live;
	}

}