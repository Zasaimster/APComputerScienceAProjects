package Components;

import java.awt.Polygon;

public class ShipHealthBar extends HealthBar {
	
	private Ship s;
	
	public ShipHealthBar(Ship s, int hp) {

		this.baseWidth = 4;
		this.s = s;
		this.entityWidth = (int) layerWidth();
		this.x = (int) (xCenter() - (baseWidth * hp) / 2);
		this.y = (int) (yCenter() - this.entityWidth * 3 / 4 - this.height);
		this.width = baseWidth * hp;
		this.originalWidth = this.width;
		this.height = 10;
		this.totalHp = hp;
		this.hp = hp;
		this.totalHp = hp;		
	}

	@Override
	public void update() {
		this.hp = s.hp;
		this.width = baseWidth * this.hp;
		this.x = (int) (xCenter() - (baseWidth * totalHp) / 2);
		this.y = (int) (yCenter() - this.entityWidth * 3 / 4 - this.height);
	}
	
	public Polygon getLayers() {
		Polygon layers = new Polygon();
		for (int j = 0; j < s.xLayers[0].length; j++) {
			layers.addPoint((int) s.xLayers[0][j], (int) s.yLayers[0][j]);
		}
		return layers;
	}
	
	public double xCenter() {
		Polygon layers = getLayers();
		return layers.getBounds().getCenterX();
	}
	
	public double yCenter() {
		Polygon layers = getLayers();
		return layers.getBounds().getCenterY();
	}
	
	public double layerWidth() {
		Polygon layers = getLayers();
		return layers.getBounds().width;
	}

}
