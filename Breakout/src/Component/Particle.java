package Component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Particle extends Rectangle {

	static final double GRAVITY = 1;
	static int size = 4;
	static int maxSpeed = 5;
	
	private double dx = 0, dy = 0;
	
	Color col;
	
	public Particle(int x, int y) {
		
		super(x, y, size, size);
		
		Random rand = new Random();
		
		dx = rand.nextInt(maxSpeed) + 1;
		dx = dx * Math.pow(-1, rand.nextInt(2));
		dy = rand.nextInt(maxSpeed) + 1;
		dy = dy * Math.pow(-1, rand.nextInt(2));
		
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		this.col = new Color(r, g, b);
	}
	
	public void move() {
		dy += this.GRAVITY;
		this.translate((int) dx, (int) dy);
	}
	
	public void draw(Graphics2D win) {
		win.setColor(col);
		win.fill(this);
	}
	
}
