package Component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Game.breakout;
import Utilities.SoundDriverHo;
import Component.Calculations;

public class Brick extends Rectangle {

	public static int width = 64;
	public static int height = 15;
	private boolean isVisible = true;
	private int status;
	private int speed;
	private int score;
	private Color col;
	public static boolean boomTown;
	public static int boomTownCount = 0;

	public Brick(int x, int y, int status) {
		super(x, y, width, height);
		this.status = status;
		this.setColor(this.status);
	}

	public void setColor(int status) {
		switch (status) {
		case 1:
			this.col = Color.blue;
			this.speed = 3;
			this.score = this.status;
			break;
		case 2:
			this.col = Color.green;
			this.speed = 4;
			this.score = this.status;
			break;
		case 3:
			this.col = Color.yellow;
			this.speed = 5;
			this.score = this.status;
			break;
		case 4:
			this.col = Color.orange;
			this.speed = 5;
			this.score = this.status;
			break;
		case 5:
			this.col = Color.red;
			this.speed = 6;
			this.score = this.status;
			break;
		}
	}

	public void draw(Graphics2D win) {
		if (this.isVisible) {
			win.setColor(this.col); // temp
			win.fill(this);
			win.setColor(Color.WHITE);
			win.draw(this);
		}
	}

	public void update(Brick w, Ball b, Paddle p, SoundDriverHo soundDriver) {
		if (this.isVisible) {
			if (b.oval.intersects(w)) {
				soundDriver.play(4);
				Brick.boomTown = true;
				Brick.boomTownCount++;
				//if (Calculations.checkBrickSideCollision(w, b)) {
					//b.reverseDx();
				//}
				if (Calculations.checkBottomCollision(w, b)) {
					if (b.getDy() < 0) {
						b.reverseDy();
						this.statusUpdate(b);
						b.setSpeed(this.speed);
						b.changeScore(score);
					}
				}
				if (Calculations.checkTopCollision(w, b)) {
					if (b.getDy() > 0) {
						b.reverseDy();
						this.statusUpdate(b);
						b.setSpeed(this.speed);
						b.changeScore(score);
					}
				}
				if(Calculations.checkLeftSideCollision(w, b)) {
					if(b.getDx() > 0) {
						b.reverseDx();
						this.statusUpdate(b);
						b.setSpeed(this.speed);
						b.changeScore(score);
					}
				}
				if(Calculations.checkRightSideCollision(w, b)) {
					if(b.getDx() < 0) {
						b.reverseDx();
						this.statusUpdate(b);
						b.setSpeed(this.speed);
						b.changeScore(score);
					}
				}
			}
		}
	}

	public void statusUpdate(Ball b) {
		if (this.status == 1) {
			this.isVisible = false;
			b.subtractBrick();
		} else {
			this.setColor(this.status - 1);
			this.status--;
		}
	}
	
	public void brickVisibility(Brick w, boolean visible) {
		if (visible) {
			this.isVisible = true;
		} else {
			this.isVisible = false;
		}
	}

	public boolean getBrickVisibility() {
		return this.isVisible;
	}

	public void deleteBrick() {
		this.isVisible = false;
	}

	public static void noBoom(int index) {
		Brick.boomTown = false;
		Brick.boomTownCount--;
		breakout.boom[index] = null;
	}

}
