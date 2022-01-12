package fr.uge.poo.paint.ex3;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle implements Shape {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.BLACK);
		graphics.drawRect(x, y, width, height);
	}
}
