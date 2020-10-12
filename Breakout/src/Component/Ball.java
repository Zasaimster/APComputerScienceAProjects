package Component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import Component.Calculations;

import Utilities.GDV5;
import Utilities.SoundDriverHo;

public class Ball {
	public int x;
	public int y;
	private int k;
	private int dx;
	private int dy;
	private int newDx;
	private int newDy;
	private boolean speedUpActivated;
	private int lives;
	private int score;
	private int bricksLeft = 0;
	private int delay = 100;
	private boolean biggerBall;
	private boolean keysPressed[];
	public Shape oval;
	private int safetyUsages = 1;

	public Ball(int x, int y, int k, boolean keysPressed[]) {
		this.x = x;
		this.y = y;
		this.k = k;
		this.newSpeed();
		this.lives = 3;
		this.score = 0;
		this.keysPressed = keysPressed;
		oval = new Ellipse2D.Double(x, y, k, k);
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.WHITE);

		win.fill(oval);
		oval = new Ellipse2D.Double(x, y, k, k);
	}

	public void update(Ball b, Paddle p, boolean debuggingState, Ability a1, Ability a2, Ability a3,
			SoundDriverHo soundDriver) {
		
		if (!debuggingState) {
			if (oval.intersects(p)) {
				soundDriver.play(0);
				if (dy > 0) {
					if (p.getMovement()) {
						if (p.getDx() * this.getDx() < 0) {
							if (this.dy > 0) {
								this.reverse();
								this.abilityCounter(a1, a2, a3);
							}
						} else {
							if(this.dy > 0) {
								this.reverseDy();
								this.abilityCounter(a1, a2, a3);
							}
						}
					} else if (this.getDx() < 0) {
						if (this.x > (p.getX() + (p.getWidth() / 2))) {
							this.reverse();
							this.abilityCounter(a1, a2, a3);
						} else {
							this.reverseDy();
							this.abilityCounter(a1, a2, a3);
						}
					} else {
						if (this.x < (p.getX() + (p.getWidth() / 2))) {
							this.reverse();
							this.abilityCounter(a1, a2, a3);
						} else {
							this.reverseDy();
							this.abilityCounter(a1, a2, a3);
						}
					}
				}

			} else if (this.y + this.k > GDV5.height()) {
				this.resetLife();
				soundDriver.play(2);
			} else if (this.x <= 0 || this.x + this.k > GDV5.width()) {
				this.reverseDx();
				soundDriver.play(1);
			} else if (this.y < 0) {
				this.reverseDy();
				soundDriver.play(1);
			}

			if (this.speedUpActivated) {
				if (this.delay == 0) {
					this.delay = 100;
					if (this.newDx != this.dx) {
						if (this.newDx > 0) {
							this.newDx--;
						} else {
							this.newDx++; 
						}
					}
					if (this.newDy != this.dy) {
						if (this.newDy > 0) {
							this.newDy--;
						} else {
							this.newDy++;
						}
					}

					if (Math.abs(this.newDx) == Math.abs(this.dx) && Math.abs(this.newDy) == Math.abs(this.dy)) {
						this.speedUpDeactivated();
					}
				} else {
					delay--;
				}
			}
			
			if (this.biggerBall) {
				if (this.delay == 0) {
					this.biggerballDeactivated();
				} else {
					delay--;
				}
			}

			this.x += dx;
			this.y += dy;
		} else {
			if (keysPressed[KeyEvent.VK_D]) {
				dx = 1;
				dy = 0;
				this.translate();
			}
			if (keysPressed[KeyEvent.VK_W]) {
				dy = -1;
				dx = 0;
				this.translate();
			}
			if (keysPressed[KeyEvent.VK_A]) {
				dx = -1;
				dy = 0;
				this.translate();
			}
			if (keysPressed[KeyEvent.VK_S]) {
				dy = 1;
				dx = 0;
				this.translate();
			}
		}
	}

	
	public void abilityCounter(Ability a1, Ability a2, Ability a3) {
		a1.counter();
		a2.counter();
		a3.counter();
	}
	public void setSpeed(int speed) {
		if (this.dx < 0) {
			this.dx = -speed;
		} else {
			this.dx = speed;
		}
		if (this.dy < 0) {
			this.dy = -speed;
		} else {
			this.dy = speed;
		}
	}

	public void addBrickLife() {
		this.bricksLeft++;
	}

	public int getRemainingBricks() {
		return this.bricksLeft;
	}

	public void subtractBrick() {
		this.bricksLeft--;
	}

	public void resetGame() {
		this.x = 400;
		this.y = 300;
		this.lives = 3;
		this.score = 0;
		this.bricksLeft = 0;
	}

	public void resetLife() {
		// this.newSpeed();
		this.randomDirection();
		this.x = 400;
		this.y = 300;
		this.lives -= 1;
	}

	public int getLives() {
		return lives;
	}

	public void randomDirection() {
		Random rand = new Random();
		this.dx = (int) (this.dx * Math.pow(-1, rand.nextInt(2)));
	}

	public void changeScore(int points) {
		this.score += points;
	}

	public int getScore() {
		return this.score;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getK() {
		return k;
	}

	public int getDx() {
		if (!this.speedUpActivated) {
			return dx;
		} else {
			return this.newDx;
		}
	}

	public int getDy() {
		if (!this.speedUpActivated) {
			return dy;
		} else {
			return this.newDy;
		}
	}

	public void translate() {
		if (!this.speedUpActivated) {
			this.x += dx;
			this.y += dy;
		} else {
			this.x += newDx;
			this.y += newDy;
		}
	}

	public void reverseDx() {
		if (!speedUpActivated) {
			this.dx *= -1;
		} else {
			this.newDx *= -1;
		}
	}

	public void reverseDy() {
		if (!speedUpActivated) {
			this.dy *= -1;
		} else {
			this.newDy *= -1;
		}
	}

	public void reverse() {
		if (!speedUpActivated) {
			this.dx *= -1;
			this.dy *= -1;
		} else {
			this.newDx *= -1;
			this.newDy *= -1;
		}
	}

	public void topBottomBounce() {
		this.dx *= -1.5;
		this.dy *= -1.5;
		this.x += dx;
		this.y = Math.abs(dy);
	}

	public void speedUpAbility() {
		this.delay = 100;
		this.speedUpActivated = true;
		this.newDx = this.dx * 2;
		this.newDy = this.dy * 2;
	}

	public void speedUpDeactivated() {
		this.speedUpActivated = false;
		if (this.newDx > 0) {
			if (this.dx <= 0) {
				this.dx *= -1;
			}
		} else {
			if (this.dx >= 0) {
				this.dx *= -1;
			}
		}

		if (this.newDy > 0) {
			if (this.dy <= 0) {
				this.dy *= -1;
			}
		} else {
			if (this.dy >= 0) {
				this.dy *= -1;
			}
		}
	}

	public void biggerBall() {
		this.delay = 1000;
		this.biggerBall = true;
		this.k += 10;
	}
	
	public void biggerballDeactivated() {
		this.delay = 100;
		this.biggerBall = false;
		this.k -= 10;
	}

	public void safety() {
		this.reverseDy();
		this.safetyUsages--;
	}
	
	public int safetyUsages() {
		return this.safetyUsages;
	}
	
	public void newSpeed() {
		Random rand = new Random();
		// this.dx = rand.nextInt(2) + 4; // rand.nextInt(3);
		// this.dy = rand.nextInt(2) + 4; // rand.nextInt(3);
		this.dx = 3;
		this.dy = 3;

		// if (rand.nextInt(2) == 0) {
		// dx *= -1;
		// }
	}
}
