package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

public abstract class HealthBar extends Rectangle {

	int totalHp;
	int hp;
	int baseWidth;
	int entityWidth;
	int originalWidth;
	Ship s;
	Enemy e;


	public HealthBar() {
		super();
		this.totalHp = hp;
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.green);
		win.fill(this);
		win.setColor(Color.white);
		win.drawRect(this.x, this.y, this.originalWidth, this.height);
	}

	public abstract void update();
	
}
