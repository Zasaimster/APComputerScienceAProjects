package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

public abstract class Missile extends Rectangle {
	//missile class used as basic class for all enemy projectiles

	Rectangle spawn;
	Ship target;
	Color col;
	int dx, dy;
	boolean isShooting = false;
	boolean isHit = false;
	
	public Missile(Ship target, Rectangle spawn) { //origin is enemy
		super();
		this.spawn = spawn;
		this.target = target;
		
		this.setSize(10, 30);
		this.setLocation((int) (spawn.getCenterX() - this.width/2), (int) spawn.getCenterY());
		
	}
	
	
	public abstract void update();
	
	public void draw(Graphics2D win) {
		win.setColor(this.col);
		win.fill(this);
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
