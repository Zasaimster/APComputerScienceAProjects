package Components;

import java.awt.Polygon;

public class EnemyHealthBar extends HealthBar {

	private Enemy e;
	
	public EnemyHealthBar(Enemy e, int hp) {
		
		this.baseWidth = 10;
		this.e = e;
		this.entityWidth = (int) e.getWidth();
		this.x = (int) (e.getCenterX() - (baseWidth * hp) / 2);
		this.y = (int) (e.getCenterY() - e.getHeight() * 3 / 4 - this.height);
		this.width = baseWidth * hp;
		this.originalWidth = this.width;
		this.height = 10;
		this.totalHp = hp;
		this.hp = hp;
	}

	@Override
	public void update() {
		this.hp = e.hp;
		this.width = baseWidth * this.hp;
		this.x = (int) (e.getCenterX() - (baseWidth * totalHp) / 2);
		this.y = (int) (e.getCenterY() - e.getHeight() * 3 / 4 - this.height);
	}

}
