package Components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Food implements GameObject {

	int maxFoods = 1;
	ArrayList<Rectangle> food;
	ArrayList<Rectangle> snakeList;
	Rectangle snakeHead;
	Snake s;
	int size = 20;

	//ADD STAR POWER UP TO FOOD
	public Food(Snake s) {
		food = new ArrayList<Rectangle>();
		Random rand = new Random();
		for (int i = 0; i < maxFoods; i++) {
			food.add(new Rectangle(this.newX(), this.newY(), size, size));
		}
		this.snakeList = s.snakeParts;
		this.s = s;

	}

	@Override
	public void update() {
		this.snakeHead = this.snakeList.get(0);
		// detect collision
		for (int i = 0; i < food.size(); i++) {
			if (food.get(i).intersects(snakeHead)) {
				s.grow();
				food.remove(i);
				this.spawnNewFood();
			}
		}

	}

	@Override
	public void draw(Graphics2D win) {
		win.setColor(Color.RED);
		for (int i = 0; i < food.size(); i++) {
			win.fill(food.get(i));

		}

	}

	public void checkNewFoodSpawn(Rectangle r) {
		// spawn a new food if it intersects with any snakebody
		for (int j = 0; j < snakeList.size(); j++) {
			if (r.intersects(snakeList.get(j))) {
				food.remove(food.size() - 1);
				this.spawnNewFood();
			}
		}
	}

	public void spawnNewFood() {
		food.add(new Rectangle(this.newX(), this.newY(), size, size));
		this.checkNewFoodSpawn(food.get(food.size() - 1));
	}
	
	public void deleteAndMakeNewFood() {
		for (int i = 0; i < food.size(); i++) {
				food.remove(i);
				this.spawnNewFood();
		}
	}

	public int newX() {
		Random rand = new Random();
		return rand.nextInt(58) * size + size;

	}

	public int newY() {
		Random rand = new Random();
		return rand.nextInt(33) * size + size;
	}

}
