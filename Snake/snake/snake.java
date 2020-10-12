package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Components.GameObject;
import Components.Snake;
import Components.Sprite;
import Components.Timer;
import Utilities.GDV5;
import Utilities.SoundDriverHo;
import Utilities.StringDraw;

public class snake extends GDV5 {
	String[] sounds = { "../sounds/BACKGROUND-MUSIC.wav", "../sounds/EXPLOSION.wav"};
	SoundDriverHo soundDriver = new SoundDriverHo(sounds, this);
	
	Snake s = new Snake(GDV5.KeysPressed, this, soundDriver);
	BufferedImage image = this.addImage("Images/fire.png");
	public int gameState = 0;
	public Timer t = new Timer(100);
	Sprite fire = new Sprite(image, 10, 6, 0.001);


	public snake() {
		super(GameObject.FPS);
		soundDriver.loop(0);
	}

	public static void main(String[] args) {
		(new snake()).start();
	}

	@Override
	public void update() {
		if (gameState == 0) {
			if (KeysPressed[KeyEvent.VK_1]) {
				gameState = 1;
			} else if (KeysPressed[KeyEvent.VK_2]) {
				gameState = 3;
			}
			fire.update();
		} else if (gameState == 1 || gameState == 3) {
			s.update();
			if (KeysPressed[KeyEvent.VK_DELETE]) {
				gameState = 0;
				this.reset();
			}
			if (s.isDead()) {
				gameState = 2;
			}
		} else if (gameState == 2) {
			if (KeysPressed[KeyEvent.VK_SPACE]) {
				this.gameState = 0;
				this.reset();
			}
		} 
	}

	@Override
	public void draw(Graphics2D win) {
		// win.drawImage(image, null, 0, 0);
		if (gameState == 0) {
			StringDraw[] lines = new StringDraw[6];
			lines[0] = new StringDraw("snek", 1200, 700, 50, -200, Color.WHITE);
			lines[1] = new StringDraw("Press '1' to snek it up or '2' to do snek but FAST *faster than rahul fast*" , 1200, 700, 15, -67, Color.WHITE);
			lines[2] = new StringDraw("wasd", 1200, 700, 15, -22, Color.WHITE);
			lines[3] = new StringDraw("There is a scoreboard to keep track of points", 1200, 700, 15, 22, Color.WHITE);
			lines[4] = new StringDraw("press delete to say good night to snek", 1200, 700, 15, 67, Color.WHITE);
			lines[5] = new StringDraw("by saim", 1200, 700, 15, 112, Color.WHITE);

			for (int i = 0; i < lines.length; i++) {
				if (i != 0) {
					lines[i].setFont(win, "Courier", Font.PLAIN);
				} else {
					lines[i].setFont(win, "Helvetica", Font.BOLD + Font.ITALIC);
				}
				lines[i].draw(win);
				fire.draw(win);
			}
		} else if (gameState == 1 || gameState == 3) {
			s.draw(win);

			StringDraw score = new StringDraw("Score: " + s.score, 1200, 700, 20, -200, Color.white);
			score.setFont(win, "Helvetica", 3);
			score.drawAtLocation(win, 600, 20);

		} else if (gameState == 2) {
			StringDraw end = new StringDraw("You lose with a score of: " + s.score + " press space to go back to home",
					1200, 700, 20, -200, Color.white);
			end.setFont(win, "Helvetica", 3);
			end.draw(win);

		}
	}

	public void reset() {
		this.s = new Snake(GDV5.KeysPressed, this, soundDriver);
	}

}
