package Components;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import Utilities.GDV5;

public class Galaxy {

	Stars[] galaxy = new Stars[300];
	private int xBound, yBound, xBase, yBase;
	double angle = 0;
	int speed = 3;
	
	public Galaxy() {
		Random rand = new Random();
		xBound = 1000;
		yBound = 800;
		xBase = -100;
		yBase = -100;
		for (int i = 0; i < galaxy.length; i++) {
			galaxy[i] = new Stars(rand.nextInt(xBound) + xBase, rand.nextInt(yBound) + yBase, rand.nextInt(4) + 1);
		}
	}

	public void update(Ship s) {
		for (Stars s1 : galaxy) {
			s1.move(s);
			if (s1.y > 700 || s1.y < -100 || s1.x < -100 || s1.x > 900) {
				s1.reset(s);
			}
			
			if(s.isMoving) {
				for(Stars s2 : galaxy) {
					s2.updateSpeed(10);
					s2.height = 15;
				}
			} else {
				for(Stars s2 : galaxy) {
					s2.updateSpeed(3);
					s2.height = s2.width;
				}
			}
			
		}
	}
	
	public void speedUp(int newSpeed) {
		for(Stars s : galaxy) {
			s.updateSpeed(newSpeed);
		}
	}

	public void draw(Graphics2D win, Ship s) {
		for (Stars s1 : galaxy) {
			s1.draw(win, s);
		}
	}

}
