package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class BossMissile extends Missile{

	double angle;
	private int speed = 5;
	private boolean isClose = false;
	
	//target = ship, spawn = boss 
	public BossMissile(Ship target, Rectangle spawn) {
		super(target, spawn);
		this.setSize(5, 30);
		this.col = new Color(0, 0, 204);
		this.setLocation((int) (spawn.getCenterX() - this.width/2 - 56), (int) spawn.getCenterY());
		this.angle = Math.atan2(this.getCenterY() - target.getCenterY(), (this.getCenterX() - this.width/2) - target.getCenterX()) - Math.PI/2;	
		
		
	}

	@Override
	public void update() {
		if( Math.sqrt(Math.pow(this.getCenterY() - target.getCenterY(), 2) + Math.pow((this.getCenterX() - this.width/2) - target.getCenterX(), 2)) <= 100 ) {
			this.isClose = true;
			this.col = new Color(119, 235, 52);
		}
		if(!this.isClose) {
			this.angle = Math.atan2(this.getCenterY() - target.getCenterY(), (this.getCenterX() - this.width/2) - target.getCenterX()) - Math.PI/2;	
		}
		
		
		this.y -=  ( this.speed * Math.cos(this.angle));
		this.x += ( this.speed * Math.sin(this.angle));
		
		//this.translate(dx, dy);
		
		if(this.x + this.width >= 800 || this.x <= 0 || this.y + this.height <= 0 || this.y >= 600 ) {
			this.isShooting = false;
		}
	}
	
	
	
	public void draw(Graphics2D win) {
		if(this.collision(target)) {
			this.isShooting = false;
			target.takeDamage(1);
		}
		
		AffineTransform previous = win.getTransform();
		win.rotate(this.angle, this.getCenterX(), this.getCenterY());
		super.draw(win);
		win.setTransform(previous);
	}
	
	

}
