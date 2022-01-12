package fr.uge.poo.paint.ex5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Drawing {
    private static Shape selectedShape;
    private static final ListShape shapes = new ListShape();

    public static void parseFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

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
                    default -> System.out.println("Unknown shape");
                }
            }
        }
    }

    public static void paintAll(SimpleGraphics area) {
        for (var shape: shapes.getShapes()) {
            area.render(graphics -> shape.draw(graphics, Color.black));
        }
    }

    public static void onClick(SimpleGraphics area, Point clicked) {
        area.clear(Color.WHITE);
        paintAll(area);
        var shape = shapes.getShapes().stream()
                .reduce((cur, acc) -> Point.distance(cur.center(), clicked) < Point.distance(acc.center(), clicked) ?
                        cur : acc)
                .orElseThrow();
        area.render(graphics -> shape.draw(graphics, Color.ORANGE));
        selectedShape = shape;
    }
}
