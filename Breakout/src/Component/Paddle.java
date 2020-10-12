package Component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {

	boolean[] keysPressed;
	private int dx = 10;
	private boolean isMoving = false;
	// Color color = new Color(WHITE);

	public Paddle(int x, int y, boolean[] keysPressed) {
		super(x, y, 100, 15);
		this.x = x;
		this.y = y;
		this.keysPressed = keysPressed;
	}

	public void update(Ball b) {
		this.isMoving = false;
		if (keysPressed[KeyEvent.VK_RIGHT]) {
			if (this.x < 800 - this.getWidth()) {
				this.isMoving = true;
				dx = 10;
				this.translate(dx, 0);
			}
		}
		if (keysPressed[KeyEvent.VK_LEFT]) {
			if (this.x > 0) {
				this.isMoving = true;
				dx = -10;
				this.translate(dx, 0);
			}
		}
	}
	
	public int getDx() {
		return this.dx;
	}
	
	public boolean getMovement() {
		return this.isMoving;
	}

	public void resetGame() {
		this.x = 350;
		this.y = 580;
	}
	
	public void draw(Graphics2D win) {
		win.setColor(Color.PINK);
		win.fill(this);
	}

}
