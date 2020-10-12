package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import Component.Ability;
import Component.Ball;
import Component.Boom;
import Component.Brick;
import Component.StringDraw;
import Component.Paddle;
import Utilities.GDV5;
import Utilities.SoundDriverHo;

public class breakout extends GDV5 {

	public breakout() {
		reset();
		soundDriver.loop(3);
	}

	String[] sounds = { "sounds/burp_x.wav", "sounds/boing2.wav", "sounds/minecraft.wav", "sounds/absolute-fire.wav", "sounds/brick-break.wav" };
	SoundDriverHo soundDriver = new SoundDriverHo(sounds, this);
	public static Boom[] boom = new Boom[10];
	Paddle p = new Paddle(350, 580, GDV5.KeysPressed);
	Ball b = new Ball(400, 300, 10, GDV5.KeysPressed);
	public int gamestate = 0;
	private FontMetrics fm;
	Brick[] wall = new Brick[50];
	//Ability speedUp = new Ability(600, 800, 1);
	Ability biggerBall = new Ability(600, 800, 2);
	Ability deleteRandomBrick = new Ability(600, 800, 3, wall);
	Ability deleteRandomColumn = new Ability(600, 800, 4, wall);
	public int brickBuffer = 15;
	public boolean debuggingState = false;

	public static void main(String[] args) throws IOException {
		(new breakout()).start();
	}

	@Override
	public void update() {
		if (gamestate == 1) {
			if (b.safetyUsages() == 1) {
				if (KeysPressed[KeyEvent.VK_SPACE]) {
					b.safety();
				}
			}
			if (b.getLives() > 0) {
				p.update(b);
				b.update(b, p, debuggingState, biggerBall, deleteRandomBrick, deleteRandomColumn, soundDriver);
				for (Brick w : wall) {
					w.update(w, b, p, soundDriver);
				}
				int count = 0;
				int i = 0;
				for (Boom explosion : boom) {
					if (explosion != null) {
						explosion.update(i);
					} else if (Brick.boomTown) {
						for (int j = 0; j < boom.length; j++) {
							if (boom[j] != null) {
								count++;
							}
						}
						if (Brick.boomTownCount > count) {
							boom[i] = new Boom(b);
						}
					} else {
						boom[i] = null;
					}
					i++;
				}

				if (b.getRemainingBricks() == 0) {
					gamestate = 2;
				}
			} else {
				gamestate = 2;
			}
		}
	}

	@Override
	public void draw(Graphics2D win) {
		if (gamestate == 0) {
			homePage(win);
		} else if (gamestate == 1) {
			game(win);
			//speedUp.draw(win, b);
			biggerBall.draw(win, b);
			deleteRandomBrick.draw(win, b);
			deleteRandomColumn.draw(win, b);
		} else if (gamestate == 2) {
			gameFinished(win);
		}
	}

	public void homePage(Graphics2D win) {
		/*
		 * BufferedImage img; try { img =
		 * ImageIO.read(getClass().getResource("./Images/download.png"));
		 * win.drawImage(img, 400, 400, this); } catch(Exception e) {
		 * System.out.println(e); }
		 */
		
		StringDraw[] lines = new StringDraw[8];
		lines[0] = new StringDraw("Press '1' for level 1, '2' for level 2, '3' for level 3,", 800, 600, 15, -112);
		lines[1] = new StringDraw("'4' for level 4, and '5' for level 5", 800, 600, 15, -67);
		lines[2] = new StringDraw("Rules: The match will end end once you either beat the game or lose 3 lives!", 800, 600, 15, -22);
		lines[3] = new StringDraw("There is a scoreboard to keep track of points", 800, 600, 15, 22);
		lines[4] = new StringDraw("Use the left and right arrow keys to move", 800, 600, 15, 67);
		lines[5] = new StringDraw("Press 'Space' to activate your safety bounce", 800, 600, 15, 112);
		lines[6] = new StringDraw("If at any point you want to exit the game, press 'delete' on your keyboard", 800, 600, 15, 157);
		lines[7] = new StringDraw("By: Saim Ahmad", 800, 600, 15, 202);

		
		for(int i = 0; i < lines.length; i++){
			lines[i].setFont(win);
			lines[i].draw(win);
		}
		if (KeysPressed[KeyEvent.VK_1]) { // row 1
			gamestate = 1;
			setBrickVisibility(1);
		} else if (KeysPressed[KeyEvent.VK_2]) { // row 1, 2
			gamestate = 1;
			setBrickVisibility(2);
		} else if (KeysPressed[KeyEvent.VK_3]) { // row 1, 2, 3
			gamestate = 1;
			setBrickVisibility(3);
		} else if (KeysPressed[KeyEvent.VK_4]) { // row 1, 2, 3, 4
			gamestate = 1;
			setBrickVisibility(4);
		} else if (KeysPressed[KeyEvent.VK_5]) { // row 1, 2, 3, 4, 5
			gamestate = 1;
			setBrickVisibility(5);
		}

	}

	public void setBrickVisibility(int rows) {
		for (int i = 0; i < rows * 10; i++) {
			wall[i].brickVisibility(wall[i], true);
			b.addBrickLife();
		}
		for (int j = rows * 10; j < wall.length; j++) {
			wall[j].brickVisibility(wall[j], false);
		}
	}

	public void game(Graphics2D win) {
		for (int i = 0; i < wall.length; i++) {
			Brick w = wall[i];
			w.draw(win);
		}
		p.draw(win);
		b.draw(win);
		for (Boom explosion : boom) {
			if (explosion != null) {
				explosion.draw(win);
			}
		}
		
		StringDraw[] lines = new StringDraw[3];
		lines[0] = new StringDraw("Lives:" + b.getLives(), 800, 600, 30, -40);
		lines[1] = new StringDraw("Score:" + b.getScore(), 800, 600, 30, -70);
		lines[2] = new StringDraw("Safeties: " + b.safetyUsages(), 0, 600, 30, -40);
		for(int i = 0; i < lines.length; i++){
			lines[i].setFont(win);
			lines[i].drawScores(win);
		}
		
		if (KeysPressed[KeyEvent.VK_DELETE]) {
			gamestate = 0;
			b.resetGame();
			p.resetGame();
			this.reset();
			//speedUp.reset();
			biggerBall.reset();
			deleteRandomBrick.reset();
			deleteRandomColumn.reset();
		}
		if (KeysPressed[KeyEvent.VK_0]) {
			debuggingState = !debuggingState;
			if (!debuggingState) {
				b.newSpeed();
			}
		}
	}

	public void setFont(Graphics2D win, int size) {
		if (b.getLives() != 1) {
			win.setColor(Color.WHITE);
		} else {
			win.setColor(Color.RED);
		}
		Font font = new Font("Courier", Font.PLAIN, size);
		fm = win.getFontMetrics(font);
		win.setFont(font);
	}

	public void reset() {
		for (int i = 0; i < 10; i++) { // i <
			for (int j = 0; j < 5; j++) {
				wall[i + j * 10] = new Brick(i * Brick.width + brickBuffer * (i + 1),
						j * Brick.height + brickBuffer * (j + 1), 5 - j);
				// wall[i + j*5] = new Brick (i * Brick.width + brickBuffer*(i+1), Brick.height,
				// 5);
			}
		}
	}

	public void gameFinished(Graphics2D win) {
		StringDraw[] lines = new StringDraw[5];
		lines[0] = new StringDraw("Thanks for playing!", 800, 600, 15, -67);
		lines[1] = new StringDraw("Here are your stats from this match", 800, 600, 15, -22);
		lines[2] = new StringDraw("Points: " + b.getScore(), 800, 600, 15, 22);
		lines[3] = new StringDraw("Lives remaining: " + b.getLives(), 800, 600, 15, 67);
		lines[4] = new StringDraw("Click enter to go back to the home screen", 800, 600, 15, 112);
		
		for(int i = 0; i < lines.length; i++){
			lines[i].setFont(win);
			lines[i].draw(win);
		}
		
		if (KeysPressed[KeyEvent.VK_ENTER]) {
			gamestate = 0;
			b.resetGame();
			p.resetGame();
			this.reset();
			//speedUp.reset();
			biggerBall.reset();
			deleteRandomBrick.reset();
			deleteRandomColumn.reset();
		}
	}

}
