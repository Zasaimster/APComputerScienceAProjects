package Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class StringDraw {
	private String s;
	private int width;
	private int height;
	private FontMetrics fm;
	private static Font font;
	private int fontSize;
	private int buffer;

	public StringDraw(String s, int width, int height, int fontSize, int buffer) {
		this.s = s;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.buffer = buffer;
	}

	public void setFont(Graphics2D win) {
		win.setColor(Color.white);
		StringDraw.font = new Font("Courier", Font.PLAIN, this.fontSize);
		this.fm = win.getFontMetrics(StringDraw.font);
		win.setFont(StringDraw.font);
	}

	public void drawScores(Graphics2D win) {
		// win.drawString(lives, (this.getWidth() - fm.stringWidth(lives) - 10),
		// this.getHeight() - 40);
		// win.drawString(score, (this.getWidth() - fm.stringWidth(score) - 10),
		// this.getHeight() - 70);
		// win.drawString(safetyUsages, 0, this.getHeight() - 40);

		if (width != 0) {
			win.drawString(this.s, (this.width - fm.stringWidth(s) - 10), this.height + this.buffer);
		} else {
			win.drawString(this.s, 0, this.height + this.buffer);
		}
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.WHITE);
		win.drawString(this.s, (this.width - fm.stringWidth(s)) / 2, this.height / 2 + this.buffer);
	}
}
