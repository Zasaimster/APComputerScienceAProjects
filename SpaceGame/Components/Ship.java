package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import Utilities.GDV5;
import Utilities.SoundDriverHo;

public class Ship extends Polygon {

	public Laser[] lGun = new Laser[200];
	public Laser[] rGun = new Laser[200];

	public Thruster[] leftThrust = new Thruster[10];
	public Thruster[] rightThrust = new Thruster[10];

	boolean[] keysPressed;
	boolean[] keysTyped;
	private int dx;
	private int dy;
	private int multiplier = 1;
	private int gunIndex = 0;
	private int delay = 0;
	public int hp = 10;
	private double rotationAngle = Math.PI / 60;
	private double totalAngle = 0;
	HealthBar h;
	Polygon[] layers;
	Rectangle[] r;
	private double trueCenterY, trueCenterX;
	public boolean isMoving = false;

	public double[][] xLayers = new double[][] {
			{ 0, 3, 13, 18, 21, 23, 23, 25, 31, 33, 33, 35, 38, 43, 53, 56, 46, 44, 52, 50, 43, 43, 13, 13, 6, 4, 12,
					10, 0 }, // [0][0]. [2][2]
			{ 6, 6, 7, 7 }, { 6, 5, 5, 8, 8, 7 }, // 13 --> 43, middle at 28, 15 right before thruster, 23 right before
													// thruster
			{ 50, 50, 49, 49 }, { 50, 51, 51, 48, 48, 49 }, { 6, 4, 12, 10 }, { 46, 44, 52, 50 },
			{ 24, 24, 25, 31, 32, 32 }, { 23, 20, 38, 35 } };
	public double[][] yLayers = new double[][] {
			{ 10, 0, 0, -22, -25, -25, -33, -35, -35, -33, -25, -25, -22, 0, 0, 10, 10, 14, 14, 10, 10, 8, 8, 10, 10,
					14, 14, 10, 10 },
			{ 0, -8, -8, 0 }, { -8, -8, -10, -10, -8, -8, }, { 0, -8, -8, 0 }, { -8, -8, -10, -10, -8, -8 },
			{ 10, 14, 14, 10 }, { 10, 14, 14, 10 }, { -30, -33, -34, -34, -33, -30 }, { 8, 11, 11, 8 }

	};

	public Ship(boolean[] keysPressed, boolean[] keysTyped) {
		this.dx = 5;
		this.dy = 5;
		this.keysPressed = keysPressed;
		// adjust starting position & size
		for (int i = 0; i < xLayers.length; i++) {
			for (int j = 0; j < xLayers[i].length; j++) {
				xLayers[i][j] *= this.multiplier;
				xLayers[i][j] += 400 - 28;
			}
		}
		for (int i = 0; i < yLayers.length; i++) {
			for (int j = 0; j < yLayers[i].length; j++) {
				yLayers[i][j] *= this.multiplier;
				yLayers[i][j] += 450;
			}
		}

		for (int i = 0; i < leftThrust.length; i++) {
			Random rand = new Random();
			int xBuffer = ((int) Math.pow(-1, rand.nextInt(2) + 1) * rand.nextInt(3));
			int yBuffer = 0;
			// rand.nextInt(2) + 1;
			int x1 = (int) (xLayers[5][2] + xLayers[5][1]) / 2;
			int x2 = (int) (xLayers[6][2] + xLayers[6][1]) / 2;
			leftThrust[i] = new Thruster(x1, (int) yLayers[5][1], x1 + 2, (int) yLayers[5][1], this);
			rightThrust[i] = new Thruster(x2, (int) yLayers[6][1], x2 + 2, (int) yLayers[6][1], this);
		}
		h = new ShipHealthBar(this, hp);
	}

	public void draw(Graphics2D win) {
		this.layers = new Polygon[8];
		win.setColor(Color.WHITE);
		for (int i = 0; i < layers.length; i++) {
			if (i <= 1 || i == 3) {
				win.setColor(new Color(161, 161, 161));
			} else if (i == 2 || i == 4) {
				win.setColor(new Color(100, 0, 0)); // gun i = 2, i = 4;
			} else if (i == 7) {
				win.setColor(new Color(0, 13, 255));
			} else if (i >= 5) {
				win.setColor(new Color(255, 255, 0));
			}

			this.layers[i] = new Polygon();
			for (int j = 0; j < this.xLayers[i].length; j++) {
				this.layers[i].addPoint((int) xLayers[i][j], (int) yLayers[i][j]);
			}
			win.fillPolygon(this.layers[i]);

			win.setColor(Color.WHITE);
			// win.fillRect( (int) trueCenterX - 1, (int) trueCenterY - 1, 2, 2); //draws a
			// center point of the ship
		}
		this.setCenter();
		//if (this.r != null) {
			//win.setColor(Color.WHITE);
			//for (int i = 0; i < this.r.length; i++) {
			//	win.draw(r[0]);
		//	}
			// this segment draws the bounds of every layer
	//	}
		h.draw(win);

	}

	public int[] doubleToIntArray(double[] arr) {
		int[] newArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = (int) arr[i];
		}
		return newArr;
	}

	public double getTotalAngle() {
		return this.totalAngle;
	}

	public double getRotationAngle() {
		return this.rotationAngle;
	}

	public void takeDamage(int num) {
		this.hp -= num;
	}
	
	public void update(SoundDriverHo sd) {
		Rectangle r = new Rectangle();
		r = layers[7].getBounds();
		this.isMoving = false;

		
		h.update();
		if (keysPressed[KeyEvent.VK_W]) {
			// long live the brute force technique :)
			if(!sd.isPlaying(3)) {
				sd.play(3);
			}
			this.isMoving = true;

			if ((r.x <= 0 && r.y <= 0) || (r.x + r.width >= GDV5.width() && r.y <= 0)
					|| (r.x + r.width >= GDV5.width() && r.y + r.height >= GDV5.height())
					|| (r.x <= 0 && r.y + r.height >= GDV5.height())) {

			} else if (r.x + r.width >= GDV5.width() && (r.y > 0 && r.y + r.height < GDV5.height())) {
				for (int i = 0; i < yLayers.length; i++) {
					for (int j = 0; j < yLayers[i].length; j++) {
						yLayers[i][j] -= dy * Math.cos(totalAngle);
					}
				}
				// h.update(0, dy * Math.cos(totalAngle) * -1);
			} else if (r.x <= 0 && (r.y > 0 && r.y + r.height < GDV5.height())) {
				for (int i = 0; i < yLayers.length; i++) {
					for (int j = 0; j < yLayers[i].length; j++) {
						yLayers[i][j] -= dy * Math.cos(totalAngle);

					}
				}
				// h.update(0, dy * Math.cos(totalAngle) * -1);
			} else if (r.y <= 0 && (r.x > 0 && r.x + r.width < GDV5.width())) {
				for (int i = 0; i < xLayers.length; i++) {
					for (int j = 0; j < xLayers[i].length; j++) {
						xLayers[i][j] += dx * Math.sin(totalAngle);
					}
				}
				// h.update(dx * Math.sin(totalAngle), 0);
			} else if (r.y + r.height >= GDV5.height() && (r.x > 0 && r.x + r.width < GDV5.width())) {
				for (int i = 0; i < xLayers.length; i++) {
					for (int j = 0; j < xLayers[i].length; j++) {
						xLayers[i][j] += dx * Math.sin(totalAngle);
					}
				}
				// h.update(dx * Math.sin(totalAngle), 0);
			} else {
				for (int i = 0; i < yLayers.length; i++) {
					for (int j = 0; j < yLayers[i].length; j++) {
						yLayers[i][j] -= dy * Math.cos(totalAngle);
						xLayers[i][j] += dx * Math.sin(totalAngle);
					}
				}
				// h.update(dx * Math.sin(totalAngle), dy * Math.cos(totalAngle) * -1);
			}
		} else {
			sd.stop(3);
		}
		if (keysPressed[KeyEvent.VK_SPACE]) {
			if (delay == 0) {
				if(sd.isPlaying(2)) {
					sd.stop(2);
					System.out.println("stopped it");
				}
				lGun[gunIndex] = new Laser((int) this.xLayers[2][2], (int) this.yLayers[2][2], (int) this.xLayers[2][3],
						(int) this.yLayers[2][3], this);
				rGun[gunIndex] = new Laser((int) this.xLayers[4][3], (int) this.yLayers[4][3], (int) this.xLayers[4][2],
						(int) this.yLayers[4][2], this);
				sd.play(2);
				// mGun[gunIndex] = new Laser((int) this.xLayers[7][1], (int)
				// this.yLayers[7][1], (int) this.xLayers[7][4], (int) this.yLayers[7][4],
				// this);

				if (this.gunIndex == this.lGun.length - 1) {
					this.gunIndex = 0;
				} else {
					this.gunIndex++;
				}

				delay = 15;
			} else {
				delay--;
				//sd.stop(2);
			}
		} else {
			if (delay != 0) {
				delay--;
			}
		}

		if (keysPressed[KeyEvent.VK_D]) {
			this.rotateShip(1);
		}

		if (keysPressed[KeyEvent.VK_A]) {
			this.rotateShip(-1);
		}
		if (keysPressed[KeyEvent.VK_E]) {
			this.rotateShip(2);
		}

		if (keysPressed[KeyEvent.VK_Q]) {
			this.rotateShip(-2);
		}

		for (int i = 0; i < leftThrust.length; i++) {
			Random rand = new Random();
			int xBuffer = 0;
			// ((int) Math.pow(-1, rand.nextInt(2) + 1) * rand.nextInt(2));
			int yBuffer = 0;
			int thrusterSize = 4;
			double theta = this.getTotalAngle();

			// int x1 = (int) ((xLayers[5][2] + xLayers[5][1]) / 2 + (xBuffer -
			// thrusterSize/2) * Math.cos(theta));
			// int x2 = (int) ((xLayers[6][2] + xLayers[6][1]) / 2 + (xBuffer -
			// thrusterSize/2) * Math.cos(theta));
			int x1 = (int) ((xLayers[5][2] + xLayers[5][1]) / 2 - thrusterSize / 2 * Math.cos(theta));
			int x2 = (int) ((xLayers[6][2] + xLayers[6][1]) / 2 - thrusterSize / 2 * Math.cos(theta));
			//System.out.println(this.totalAngle);
			
			if (leftThrust[i] == null) {
				leftThrust[i] = new Thruster(x1, (int) (yLayers[5][1] + (yLayers[5][2] - yLayers[5][1]) * 1 / 3),
						(int) ((x1 + thrusterSize * Math.cos(theta))),
						(int) (yLayers[5][1] + (yLayers[5][2] - yLayers[5][1]) * 2 / 3), this);
			}
			if (rightThrust[i] == null) {
				rightThrust[i] = new Thruster(x2, (int) (yLayers[6][1] + (yLayers[6][2] - yLayers[6][1]) * 1 / 3),
						(int) (x2 + thrusterSize * Math.cos(theta)),
						(int) (yLayers[6][1] + (yLayers[6][2] - yLayers[6][1]) * 2 / 3), this);
			}
		}

	}

	public void setCenter() {
		this.r = new Rectangle[8];
		double left, right, top, bot;
		double centerX[] = new double[8];
		double centerY[] = new double[8];
		for (int i = 0; i < this.layers.length; i++) {
			this.r[i] = new Rectangle();
			this.r[i] = layers[i].getBounds();
			left = r[i].getX();
			right = r[i].getX() + r[i].getWidth();
			top = r[i].getY();
			bot = r[i].getY() + r[i].getHeight();
			centerX[i] = (left + right) / 2;
			centerY[i] = (top + bot) / 2;
		}

		this.trueCenterX = 0;
		this.trueCenterY = 0;
		for (int i = 0; i < centerX.length; i++) {
			trueCenterX += centerX[i];
			trueCenterY += centerY[i];
		}
		trueCenterX /= centerX.length;
		trueCenterY /= centerY.length;
	}

	public double getCenterX() {
		return this.trueCenterX;
	}

	public double getCenterY() {
		return this.trueCenterY;
	}

	public void rotateShip(int direction) {
		this.totalAngle += direction * this.rotationAngle;
		this.totalAngle %= (2 * Math.PI);
		this.setCenter();
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < xLayers[i].length; j++) {

				xLayers[i][j] -= trueCenterX;
				yLayers[i][j] -= trueCenterY;

				double newX = (((xLayers[i][j]) * Math.cos(direction * this.rotationAngle))
						- (yLayers[i][j]) * Math.sin(direction * this.rotationAngle));
				double newY = (((xLayers[i][j]) * Math.sin(direction * this.rotationAngle))
						+ (yLayers[i][j]) * Math.cos(direction * this.rotationAngle));

				xLayers[i][j] = newX + trueCenterX;
				yLayers[i][j] = newY + trueCenterY;

			}
		}
	}

	public Rectangle getMainBound() {
		Rectangle r = this.layers[0].getBounds();
		return r;
	}

}
