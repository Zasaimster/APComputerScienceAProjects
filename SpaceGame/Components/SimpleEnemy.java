package Components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import Utilities.GDV5;

public class SimpleEnemy extends Enemy {

	private double dtheta = Math.PI / 60;
	private double theta = Math.PI / 2;
	private int timer = 70;
	private int delay = 0;
	private boolean hasGun = false;
	Ship s;

	public SimpleEnemy(Ship s) {
		this.setSize(50, 50);
		Random rand = new Random();
		// this.x = rand.nextInt(600 - this.width) + 100;
		this.setLocation(rand.nextInt(600 - this.width) + 100, (int) (0 - this.getHeight()));
		this.dy = this.speed;
		this.hp = 2;
		h = new EnemyHealthBar(this, hp);
		
		if(Math.random() > 0.8) {
			this.pewpew = new BossLaser(s, this);
			this.hasGun = true;
		}
		
		this.s = s;

	}

	@Override
	public void update() {
		if (!this.isDestroyed) {
			Random rand = new Random();
			this.translate(dx, dy);
			if (this.y >= GDV5.height()) {
				if (this.delay == 0) {
					this.y = 0 - this.height;
					this.x = rand.nextInt(600 - this.width) + 100;
					delay = 100;
				} else {
					delay--;
				}
			}
			this.theta %= 2 * Math.PI;
			this.theta += this.dtheta;

			if(this.hasGun) {
				if (this.pewpew.isShooting) {
					if(!this.isDestroyed) {
						this.pewpew.update();
					}
					if (pewpew.isHit) {
						this.pewpew.isShooting = false;
					}
				} else {
						if(delay == 0) {
							pewpew = null;
							this.pewpew = new BossLaser(s, this);
							this.pewpew.isShooting = true;
							delay = timer;
						} else {
							delay--;
						}
				}
			}
			
			
			h.update();
		} else {
			for (int i = 0; i < dp.length; i++) {
				dp[i].update();
			}
		}
	}

	public void draw(Graphics2D win) {
		AffineTransform previous = win.getTransform();
		win.rotate(this.theta, this.getCenterX(), this.getCenterY());
		super.draw(win);
		win.setTransform(previous);

		
		if(this.hasGun) {
			if (this.pewpew.isShooting) {
				if(!this.isDestroyed) {
					this.pewpew.draw(win);
				}
			} 
		}
		
		if (!this.isDestroyed) {
			h.draw(win);
		} else {
			for (int i = 0; i < dp.length; i++) {
				dp[i].draw(win);
			}
		}

	}
}
