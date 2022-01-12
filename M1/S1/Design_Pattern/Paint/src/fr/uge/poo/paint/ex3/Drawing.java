package fr.uge.poo.paint.ex3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Drawing {
	public static ListShape parseFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		ListShape shapes = new ListShape();

		try(Stream<String> lines = Files.lines(path)) {
			for (String line: lines.toList()) {
				String[] tokens = line.split(" ");
				int x1 = Integer.parseInt(tokens[1]);
				int y1 = Integer.parseInt(tokens[2]);
				int x2 = Integer.parseInt(tokens[3]);
				int y2 = Integer.parseInt(tokens[4]);
				switch (tokens[0]) {
					case "line" -> shapes.add(new Line(x1, y1, x2, y2));
					case "rectangle" -> shapes.add(new Rectangle(x1, y1, x2, y2));
					case "ellipse" ->  shapes.add(new Ellipse(x1, y1, x2, y2));
				}
			}
		}
		return shapes;
	}
}
