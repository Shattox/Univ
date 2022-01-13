package fr.uge.poo.paint.ex4;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;

public class Paint {
    private static void drawShapes(SimpleGraphics area, Shape shape) {
        switch (shape) {
            case Line line -> area.render(line::draw);
            case Rectangle rect -> area.render(rect::draw);
            case Ellipse ellipse -> area.render(ellipse::draw);
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }

    private static void closestFigureFromClick(ListShape figure, Point clicked) {
        Shape shape = figure.getShapes().stream()
                .reduce((cur, acc) -> Point.distance(cur.center(), clicked) < Point.distance(acc.center(), clicked) ?
                        cur : acc)
                .orElseThrow();
        System.out.println(shape);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need file name");
        }
        SimpleGraphics area = new SimpleGraphics("area", 800, 600);
        area.clear(Color.WHITE);

        ListShape shapes = Drawing.parseFile(args[0]);

        shapes.getShapes().forEach(shape -> drawShapes(area, shape));

        area.waitForMouseEvents((x, y) -> closestFigureFromClick(shapes, new Point(x, y)));
    }
}
