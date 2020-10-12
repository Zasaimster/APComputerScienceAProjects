package Components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite implements GameObject{

	Timer time;
	BufferedImage[] slides;
	int position = 0;
	
	
	public Sprite(BufferedImage img, int cols, int rows, double delay) {
		time = new Timer(delay);
		time.start();
		int width = img.getWidth() / cols;
		int height = img.getHeight() / rows;
		slides = new BufferedImage[rows * cols];
		int count = 0;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				BufferedImage subImg = img.getSubimage(j * width, i * height, width, height);
				this.slides[count] = subImg;
				count++;
			}
		}
	}
	
	@Override
	public void update() {
		if(time.update()) {
			this.position++;
			this.position %= this.slides.length;
		}
		
	}

	@Override
	public void draw(Graphics2D win) {
		win.drawImage(this.slides[this.position], null, 570, 30);	
	}

	
}
