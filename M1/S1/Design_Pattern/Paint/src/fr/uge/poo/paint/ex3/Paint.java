package fr.uge.poo.paint.ex3;

import java.awt.Color;
import java.io.IOException;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Paint {
	private static void draw(SimpleGraphics area, Shape shape) {
		switch (shape) {
			case Line line -> area.render(line::draw);
			case Rectangle rect -> area.render(rect::draw);
			case Ellipse ellipse -> area.render(ellipse::draw);
			default -> throw new IllegalStateException("Unexpected value: " + shape);
		}
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Need file name");
		}
		SimpleGraphics area = new SimpleGraphics("area", 800, 600);
		area.clear(Color.WHITE);

		ListShape shapes = Drawing.parseFile(args[0]);

		shapes.getShapes().forEach(shape -> draw(area, shape));
	}
}
