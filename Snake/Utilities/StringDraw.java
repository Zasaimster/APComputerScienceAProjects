package Utilities;

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
	private Color col;

	public StringDraw(String s, int width, int height, int fontSize, int buffer, Color col) {
		this.s = s;
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.buffer = buffer;
		this.col = col;
	}

	public void setFont(Graphics2D win, String font, int type) {
		win.setColor(col);
		StringDraw.font = new Font(font, type, this.fontSize);
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
		win.drawString(this.s, (this.width - fm.stringWidth(s)) / 2, this.height / 2 + this.buffer);
	}
	
	public void setColor(Color col) {
		this.col = col;
	}
	
	public void drawAtLocation(Graphics2D win, int x, int y) {
		win.drawString(this.s, x, y);
	}
}
