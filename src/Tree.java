import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//���ý������ʹ���
public class Tree {
	public static final int width = 30;
	public static final int length = 30;
	int x, y;
	START tc ;
	TankClient1 tc1;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/tree.png")),
		};
	}
	
	
	public Tree(int x, int y, START tc) {  //Tree�Ĺ��췽��������x��y��tc����
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public Tree(int x, int y, TankClient1 tc1) {  //Tree�Ĺ��췽��������x��y��tc����
		this.x = x;
		this.y = y;
		this.tc1 = tc1;
	}

	public void draw(Graphics g) {           //������
		g.drawImage(treeImags[0],x, y, null);
	}
	
}
