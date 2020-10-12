package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class BossLaser extends Missile{

	double angle;
	
	//target = ship, spawn = boss 
	public BossLaser(Ship target, Rectangle spawn) {
		super(target, spawn);
		this.setSize(3, 15);
		this.col = Color.red;
		this.dx = 10;
		this.dy = 10;
		
		
		this.angle = Math.atan2(this.getCenterY() - target.getCenterY(), (this.getCenterX() - this.width/2) - target.getCenterX()) - Math.PI/2;
	}

	@Override
	public void update() {
		
		if(this.collision(target)) {
			this.isShooting = false;
			target.takeDamage(1);
		}
		
		this.y -= this.dy * Math.cos(angle);
		this.x += this.dx * Math.sin(angle);
		
		if(this.x + this.width >= 800 || this.x <= 0 || this.y + this.height <= 0 || this.y >= 600 ) {
			this.isShooting = false;
		}
	}
	
	
	
	public void draw(Graphics2D win) {
		
		AffineTransform previous = win.getTransform();
		win.rotate(angle, this.getCenterX(), this.getCenterY());
		super.draw(win);
		win.setTransform(previous);
	}
	
	

}
