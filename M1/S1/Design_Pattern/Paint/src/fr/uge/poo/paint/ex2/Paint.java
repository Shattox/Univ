package fr.uge.poo.paint.ex2;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class Paint {
	public static void draw(SimpleGraphics area, List<String> lines) {
		for (String line: lines) {
			String[] tokens = line.split(" ");
			int x1 = Integer.parseInt(tokens[1]);
			int y1 = Integer.parseInt(tokens[2]);
			int x2 = Integer.parseInt(tokens[3]);
			int y2 = Integer.parseInt(tokens[4]);
			Line newLine = new Line(x1, y1, x2, y2);
			area.render(newLine::draw);
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Need file name");
		}

		SimpleGraphics area = new SimpleGraphics("area", 800, 600);
		area.clear(Color.WHITE);
		
		List<String> lines = Drawing.readFile(args[0]);

		draw(area, lines);
	}
}
