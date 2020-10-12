package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Laser extends Polygon {

	private int dy = 13;
	private int dx = 13;
	private Color Col = Color.RED;
	private boolean isShooting = false;
	private static final int height = 15;
	private double theta;
	private int index; // 1 = left gun, 2 = right gun;

	public Laser(int x1, int y1, int x2, int y2, Ship s) {
		super();

		this.isShooting = true;
		this.theta = s.getTotalAngle();
		super.addPoint(x1, y1);
		super.addPoint(x2, y2);
		super.addPoint((int) (x2 + height * Math.sin(theta)), (int) (y2 - height * Math.cos(theta)));
		super.addPoint((int) (x1 + height * Math.sin(theta)), (int) (y1 - height * Math.cos(theta)));
	}

	public void render(Graphics2D win, Ship s) {
		win.setColor(this.Col);
		win.fillPolygon(this.xpoints, this.ypoints, 4);
	}

	public boolean getIsShooting() {
		return this.isShooting;
	}

	public void update(Ship s) {
		Rectangle r = this.getBounds();
		if (this.ypoints[0] + height * Math.cos(s.getTotalAngle()) <= 0) {
			this.isShooting = false;
		} else {
			for (int i = 0; i < this.npoints; i++) {
				this.ypoints[i] -= dy * Math.cos(this.theta);
				this.xpoints[i] += dx * Math.sin(this.theta);
			}
		}

	}

}
