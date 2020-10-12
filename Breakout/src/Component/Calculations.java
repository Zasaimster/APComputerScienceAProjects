package Component;

import Component.Ball;
import Component.Brick;
import Component.Paddle;

public class Calculations {

	public static boolean checkBreakoutCollision(Ball b, Paddle p) {
		if (b.getY() + b.getK() >= p.getY() && b.getX() + b.getK() >= p.getX()
				&& b.getX() + b.getK() <= p.getX() + p.getWidth()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkBreakoutSideCollision(Ball b, Paddle p) {
		if(b.getX() - b.getDx() + b.getK() < p.getX() && b.getX() + b.getK() >= p.getX() || b.getX() - b.getDx() * 3 > p.getX() + p.getWidth() && b.getX() <= p.getX() + p.getWidth()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkBrickCollision(Brick w, Ball b) {
		if ((b.getY() + b.getK() >= w.getY() && b.getY() <= w.getY() + w.getHeight() ) && b.getX() >= w.getX()
				&& b.getX() + b.getK() <= w.getX() + w.getWidth()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkBrickSideCollision(Brick w, Ball b) {
		if(b.getX() - b.getDx() + b.getK() < w.getX() && b.getX() + b.getK() >= w.getX() || b.getX() - b.getDx() > w.getX() + w.getWidth() && b.getX() <= w.getX() + w.getWidth()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkBottomCollision(Brick w, Ball b) {
		if(b.getY() - b.getDy() * 10 > w.getY() + w.getHeight()  && b.getY() <= w.getY() + w.getHeight()) {
			return true;
 		} else {
 			return false;
 		}
	}
	
	public static boolean checkTopCollision(Brick w, Ball b) {
		if(b.getY() + b.getK() - b.getDy() * 3 < w.getY() && b.getY() + b.getK() >= w.getY() ){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkLeftSideCollision(Brick w, Ball b) {
		if(b.getX() + b.getK() - b.getDx() * 3 < w.getX() && b.getX() + b.getK() >= w.getX()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkRightSideCollision(Brick w, Ball b) {
		if(b.getX() - b.getDx() * 10 >  w.getX() + w.getWidth() && b.getX() <= w.getX() + w.getWidth()) {
			return true;
		} else {
			return false;
		}
	}

	public static int dist(int xc1, int yc1, int xc2, int yc2) {
		return (int) Math.sqrt((xc1 - xc2) * (xc1 - xc2) + (yc1 - yc2) * (yc1 - yc2));
		
	}
}
