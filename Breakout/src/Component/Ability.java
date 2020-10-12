package Component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import Component.Calculations;

public class Ability {
	private int counter = 0;
	private int countForSpawn = 1;
	private int abilitySize = 40;
	private int lifeSpan = 6;
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	private int x;
	private int y;
	private int abilityType;
	private Brick[] bricks = new Brick[50];
	// 1 = speedUp - light blue
	// 2 = biggerBall - light green
	// 3 = randomBrickDelete - purple
	// 4 = deleteColumn - dark red

	public Ability(int height, int width, int abilityType) {

		this.minX = width * 1 / 6;
		this.maxX = width * 5 / 6;
		this.minY = height * 1 / 3;
		this.maxY = height * 5 / 6;

		this.abilityType = abilityType;

		this.getNewSpawn();
		this.newCountForSpawn();

	}

	public Ability(int height, int width, int abilityType, Brick[] bricks) {

		this.minX = width * 1 / 6;
		this.maxX = width * 5 / 6;
		this.minY = height * 1 / 3;
		this.maxY = height * 5 / 6;

		this.abilityType = abilityType;
		this.bricks = bricks;
		this.getNewSpawn();
		this.newCountForSpawn();
	}

	public void newCountForSpawn() {
		// this.countForSpawn = 0;
		Random rand = new Random();
		this.countForSpawn = rand.nextInt(13) + 1;
	}

	public void getNewSpawn() {
		Random rand = new Random();
		this.x = rand.nextInt(maxX - minX + 1) + minX;
		this.y = rand.nextInt(maxY - minY + 1) + minY;
	}

	public void counter() {
		this.counter++;
	}

	public void checkLifeSpan() {
		if (this.counter >= this.countForSpawn + this.lifeSpan) {
			this.reset();
		}
	}

	public void draw(Graphics2D win, Ball b) {
		if (this.counter >= this.countForSpawn) {
			this.checkLifeSpan();
			this.spawnAbility(win);
			this.update(b);
		}
	}

	public void reset() {
		this.counter = 0;
		this.getNewSpawn();
		this.newCountForSpawn();

	}

	public int getCounter() {
		return this.counter;
	}

	public int getCountForSpawn() {
		return this.countForSpawn;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getK() {
		return this.abilitySize;
	}

	public void spawnAbility(Graphics2D win) {
		if (this.abilityType == 1) {
			win.setColor(new Color(94, 196, 255));
		} else if (this.abilityType == 2) {
			win.setColor(new Color(9, 222, 9));
		} else if (this.abilityType == 3) {
			win.setColor(new Color(73, 6, 145));
		} else if (this.abilityType == 4) {
			win.setColor(new Color(87, 6, 6));
		}
		// win.drawRect(minX, minY, (maxX-minX), (maxY-minY));
		win.fillOval(this.x, this.y, this.abilitySize, this.abilitySize);
	}

	public void deleteRandomBrick() {
		Random rand = new Random();
		int random = rand.nextInt(50);
		if (!bricks[random].getBrickVisibility()) {
			this.deleteRandomBrick();
		} else {
			bricks[random].deleteBrick();
		}
	}

	public void deleteRandomColumn() {
		Random rand = new Random();
		int random = rand.nextInt(10);
		for (int i = random; i < bricks.length; i += 10) {
			bricks[i].deleteBrick();
		}
	}

	public void update(Ball b) {
		int xCenterB = (b.getX() + (b.getK()) / 2);
		int yCenterB = (b.getY() + (b.getK()) / 2);
		int xCenterA = (this.x + (this.abilitySize / 2));
		int yCenterA = (this.y + (this.abilitySize / 2));
		if (Calculations.dist(xCenterB, yCenterB, xCenterA, yCenterA) <= (b.getK() / 2 + this.abilitySize / 2)) {
			if (this.abilityType == 1) {
				b.speedUpAbility();
			} else if (this.abilityType == 2) {
				b.biggerBall();
			} else if (this.abilityType == 3) {
				this.deleteRandomBrick();
			} else if (this.abilityType == 4) {
				this.deleteRandomColumn();
			}
			this.reset();
		}
	}
}
