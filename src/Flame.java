import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class Flame {
	private int x, y, w = 33, h = 33;
	private Direction dir;
	START tc;
	TankClient1 tc1;
	

	public Flame(int x, int y, Direction dir, START tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
	}
	
	public Flame(int x, int y, Direction dir, TankClient1 tc1) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc1 = tc1;
	}

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] flameImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	static {
		flameImages = new Image[] {
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameL.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameR.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameD.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameU.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameLU.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameLD.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameRU.png")),
			tk.getImage(Flame.class.getClassLoader().getResource("Images/flameRD.png"))
		};
		
		imgs.put("L",flameImages[0]);
		imgs.put("R",flameImages[1]);
		imgs.put("D",flameImages[2]);
		imgs.put("U",flameImages[3]);
		imgs.put("LU",flameImages[4]);
		imgs.put("LD",flameImages[5]);
		imgs.put("RU",flameImages[6]);
		imgs.put("RD",flameImages[7]);
	
	}
	
	public void draw(Graphics g) {
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case LU:
			g.drawImage(imgs.get("LU"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case LD:
			g.drawImage(imgs.get("LD"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case RD:
			g.drawImage(imgs.get("RD"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		case RU:
			g.drawImage(imgs.get("RU"),x,y,null);
			if(START.choice == 1)tc.flames.remove(this);
			else tc1.flames.remove(this);
			break;
		default:
			break;
		}
	}
}
