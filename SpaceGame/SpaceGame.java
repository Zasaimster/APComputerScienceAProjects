
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Components.Galaxy;
import Components.MotherShip;
import Components.Ship;
import Components.StringDraw;
import Utilities.GDV5;
import Utilities.SoundDriverHo;

public class SpaceGame extends GDV5 {

	public SpaceGame() {
		soundDriver.loop(0);
	}
	
	Ship saim = new Ship(GDV5.KeysPressed, GDV5.KeysTyped);
	public static Galaxy galaxy = new Galaxy();
	MotherShip ms = new MotherShip(saim);
	private int gameState = 0;
	String[] sounds = { "sounds/BACKGROUND-MUSIC.wav", "sounds/EXPLOSION.wav", "sounds/GUNSHOT.wav", "sounds/THRUSTER.wav"};
	SoundDriverHo soundDriver = new SoundDriverHo(sounds, this);

	public static void main(String[] args) {
		(new SpaceGame()).start();
	}

	@Override
	public void update() {
		if (gameState == 0) {
			if (KeysPressed[KeyEvent.VK_1]) {
				gameState = 1;
			} else if (KeysPressed[KeyEvent.VK_2]) {
				gameState = 2;
			}
		} else if (gameState == 1) {
			this.mainUpdate();
			if (saim.hp <= 0) {
				gameState = 3;
			}
			if(KeysPressed[KeyEvent.VK_DELETE]) {
				this.gameState = 0;
				this.resetGame();
			}
		} else if (gameState == 2) {
			this.mainUpdate();
			if (saim.hp > 1) {
				saim.hp = 1;
			}
			if (saim.hp <= 0) {
				gameState = 3;
			}
			if(KeysPressed[KeyEvent.VK_DELETE]) {
				this.gameState = 0;
				this.resetGame();
			}
		} else if (gameState == 3) {
			if (KeysPressed[KeyEvent.VK_ENTER]) {
				gameState = 0;
				this.resetGame();
			}
		}
	}

	@Override
	public void draw(Graphics2D win) {
		if (gameState == 0) {
			StringDraw[] lines = new StringDraw[5];
			lines[0] = new StringDraw("Saim Ahmad's Space Shooter", 800, 600, 50, -200, Color.WHITE);
			lines[1] = new StringDraw("Press '1' for easy mode or '2' for hard mode", 800, 600, 15, -67, Color.WHITE);
			lines[2] = new StringDraw("Use 'w' to move and 'a/q' and 'd/e' to rotate (q/e is faster than a/d)", 800, 600, 15, -22, Color.WHITE);
			lines[3] = new StringDraw("There is a scoreboard to keep track of points", 800, 600, 15, 22, Color.WHITE);
			lines[4] = new StringDraw("If at any point you want to exit the game, press 'delete' on your keyboard", 800,
					600, 15, 67, Color.WHITE);

			for (int i = 0; i < lines.length; i++) {
				if (i != 0) {
					lines[i].setFont(win, "Courier", Font.PLAIN);
				} else {
					lines[i].setFont(win, "Helvetica", Font.BOLD + Font.ITALIC);
				}
				lines[i].draw(win);
			}
		} else if (gameState == 1 || gameState == 2) {
			this.mainDraw(win);
		} else if (gameState == 3) {
			StringDraw[] lines = new StringDraw[3];
			lines[0] = new StringDraw("You died with a score of: " + ms.score, 800, 600, 50, -112, Color.WHITE);
			lines[1] = new StringDraw("Press 'Enter' to go back to the home page", 800, 600, 15, -67, Color.WHITE);
			lines[2] = new StringDraw("to try again!", 800, 600, 15, -22, Color.WHITE);

			for (int i = 0; i < lines.length; i++) {
				lines[i].setFont(win, "Helvetica", Font.BOLD + Font.ITALIC);
				lines[i].draw(win);
			}
		}
	}

	public void mainUpdate() {
		galaxy.update(saim);
		for (int i = 0; i < saim.lGun.length; i++) {
			if (saim.lGun[i] != null) {
				if (!saim.lGun[i].getIsShooting()) {
					saim.lGun[i] = null;
				}
				if (saim.rGun[i] != null) {
					if (!saim.rGun[i].getIsShooting()) {
						saim.rGun[i] = null;
					}
				}
			}
			if (saim.lGun[i] != null) {
				saim.lGun[i].update(saim);
			}
			if (saim.rGun[i] != null) {
				saim.rGun[i].update(saim);
			}
		}

		for (int i = 0; i < saim.leftThrust.length; i++) {
			if (saim.leftThrust[i] != null) {
				if (saim.leftThrust[i].getAlpha() <= 20) {
					saim.leftThrust[i] = null;
				}
			}
			if (saim.rightThrust[i] != null) {
				if (saim.rightThrust[i].getAlpha() <= 20) {
					saim.rightThrust[i] = null;
				}
			}

			if (saim.leftThrust[i] != null) {
				saim.leftThrust[i].update();
			}
			if (saim.rightThrust[i] != null) {
				saim.rightThrust[i].update();
			}
		}
		saim.update(soundDriver);
		ms.update(saim, soundDriver);
	}

	public void mainDraw(Graphics2D win) {
		galaxy.draw(win, saim);
		saim.draw(win);

		for (int i = 0; i < saim.lGun.length; i++) {
			if (saim.lGun[i] != null) {
				saim.lGun[i].render(win, saim);
			}
			if (saim.rGun[i] != null) {
				saim.rGun[i].render(win, saim);
			}
		}

		for (int i = 0; i < saim.leftThrust.length; i++) {
			if (saim.leftThrust[i] != null) {
				saim.leftThrust[i].render(win);
			}
			if (saim.rightThrust[i] != null) {
				saim.rightThrust[i].render(win);

			}
		}

		ms.draw(win, 0);

		StringDraw[] lines = new StringDraw[1];
		lines[0] = new StringDraw("Score: " + ms.score, 800, 600, 15, -112, Color.red);

		for (int i = 0; i < lines.length; i++) {
			lines[i].setFont(win, "Courier", Font.PLAIN);
			lines[i].drawScores(win);
		}
	}

	public void resetGame() {
		this.saim = null;
		saim = new Ship(GDV5.KeysPressed, GDV5.KeysTyped);
		galaxy = null;
		galaxy = new Galaxy();
		this.ms = null;
		ms = new MotherShip(saim);
	}
	
}
