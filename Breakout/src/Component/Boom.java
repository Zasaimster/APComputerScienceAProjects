package Component;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Boom {

	Particle[] boom = new Particle[5];
	public static int activeBooms = 0;

	public Boom(Ball ball) {
		for (int i = 0; i < boom.length; i++) {
			boom[i] = new Particle(ball.x, ball.y);
		}
		Boom.activeBooms++;
	}

	public void update(int index) {
		int count = 0;
		
		for (Particle p : boom) {
			p.move();
			if (p.y > 800) {
				count++;
			}
		}
		if(count == boom.length) {
			Brick.noBoom(index);
			Boom.activeBooms--;
		}
	}

	public void draw(Graphics2D win) {
		for (Particle p : boom) {
			p.draw(win);
		}
	}

}
