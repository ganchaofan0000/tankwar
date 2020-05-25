import java.applet.*;
import java.io.File;

public class Music{
	boolean looping = false; 
	File file1 = new File("C:\\QQDownload\\music\\backmusic.wav");
	File file2 = new File("C:\\QQDownload\\music\\boom.wav");
	File file3 = new File("C:\\QQDownload\\music\\bullet.wav");
	File file4 = new File("C:\\QQDownload\\music\\move.wav");
	AudioClip sound1;
	AudioClip sound2;
	AudioClip sound3;
	AudioClip sound4;

	public Music() { 
		try {
			sound1 = Applet.newAudioClip(file1.toURL());
			sound2 = Applet.newAudioClip(file2.toURL());
			sound3 = Applet.newAudioClip(file3.toURL());
			sound4 = Applet.newAudioClip(file4.toURL());
		} catch(OutOfMemoryError e){
			System.out.println("ÄÚ´æÒç³ö");
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}	
	public void start() {
		sound1.play(); 
	}
	public void circle() {
		looping = true;
		sound1.loop(); 
	}

	public void stop() {
		if (looping) {
			looping = false;
			sound1.stop(); 
		} else {
			sound1.stop();
		}
	}	
	public void play_bomb() {
		sound2.play();
	}
	public void play_bullet() {
		sound3.play();
	}
	public void play_move() {
		sound4.play();
	}
}
