package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import Utilities.GDV5;

public abstract class Enemy extends Rectangle {

	BufferedImage image;
	int dx = 0, dy = 0, speed = 3, hp;
	Color col = Color.red;
	Missile pewpew;
	Missile boomboom;
	HealthBar h;
	DeathParticle[] dp;
	boolean isDestroyed = false;
	boolean dpDestroyed = false;
	boolean collision = false;

	public Enemy() {
		super(GDV5.width() / 2, GDV5.height() / 2, 20, 20);
	}

	public void draw(Graphics2D win) {
		if (!this.isDestroyed) {
			if (image == null) {
				win.setColor(col);
				win.fill(this);
			} else {
				win.drawImage(image, null, this.x, this.y);
			}
			// h.draw(win);
		}
	}

	public abstract void update();

	public void takeDamage(int num) {
		hp -= num;
	}

	public void deathParticle() {
		// 2 x 2 boxes
		this.isDestroyed = true;
		int count = (this.height * this.width) / 20;
		this.dp = new DeathParticle[count];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = new DeathParticle(this);
		}
	}
	
	public void noParticle() {
		for(int i = 0; i < dp.length; i++) {
			dp[i] = null;
		}
	}
	
	public boolean collision(Ship s) {
		Area area = new Area(s.layers[0]);
		Area area1 = new Area(this);
		area1.intersect(area);
		if(area1.isEmpty()) {
			return false;	
		} else {
			return true;
		}
	}

}
