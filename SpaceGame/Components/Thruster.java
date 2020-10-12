package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

public class Thruster extends Polygon {

	private static int size = 4;
	private int dx, dy, g;
	private double theta;
	private Color col; // 235, 60, 45 --> 235, 228, 45
	private int alpha = 255;
	Ship s;

	public Thruster(int x1, int y1, int x2, int y2, Ship s) {
		super();
		// creates thruster particle
		this.theta = s.getTotalAngle();
		super.addPoint(x1, y1);
		super.addPoint(x2, y2);
		super.addPoint((int) (x2 - size * Math.sin(theta)), (int) (y2 + size * Math.cos(theta)));
		super.addPoint((int) (x1 - size * Math.sin(theta)), (int) (y1 + size * Math.cos(theta)));

		// chooses a random warm color
		Random rand = new Random();
		this.g = rand.nextInt(169) + 60;
		// this.g = 230;
		this.col = new Color(235, this.g, 45, alpha);
		// chooses dy and dx
		//this.dy = (int) (rand.nextInt(3));
		
		if ((theta > Math.PI / 3 && theta < 2 * Math.PI / 3) || (theta < -4 * Math.PI / 3) && theta > -5 * Math.PI/3) {
			this.dx = (int) (rand.nextInt(3) * -1);
			this.dy = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));
		} else if ((theta > 4 * Math.PI / 3 && theta < 5 * Math.PI / 3) || (theta < -Math.PI / 3) && theta > -2 * Math.PI/3) {
			this.dx = (int) (rand.nextInt(3) * 1);
			this.dy = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));
		} else if((theta < Math.PI/2 && theta > -Math.PI/2) || (theta > 3 * Math.PI/2 && theta < -3 * Math.PI / 2)){
			this.dx = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));
			this.dy = (int) (rand.nextInt(3) * 1);
		} else if((theta > Math.PI/2 && theta < -Math.PI/2) || (theta < 3 * Math.PI/2 && theta > -3 * Math.PI / 2)){
			this.dx = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));
			this.dy = (int) (rand.nextInt(3) * -1);
		} else {
			this.dx = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));	
			this.dy = (int) (rand.nextInt(3) * Math.pow(-1, rand.nextInt(2)));
		}
		this.s = s;

	}

	public void render(Graphics2D win) {
		win.setColor(this.col);
		win.fillPolygon(this.xpoints, this.ypoints, 4);
		// win.drawLine(this.xpoints[0], this.ypoints[0], this.xpoints[1],
		// this.ypoints[1]);
	}

	public double getAlpha() {
		return this.alpha;
	}

	public void update() {
		for (int i = 0; i < this.npoints; i++) {
			this.ypoints[i] += dy;
			this.xpoints[i] += dx;

			// adjusts alpha
			if (s.isMoving) {
				alpha -= 15;
			} else {
				alpha -= 20;
			}
			if (alpha <= 0) {
				alpha = 0;
			}
			this.col = new Color(235, this.g, 45, alpha);
		}
	}

}
