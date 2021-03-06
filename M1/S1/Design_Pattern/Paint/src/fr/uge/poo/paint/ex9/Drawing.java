package fr.uge.poo.paint.ex9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Drawing {
    private Shape selectedShape;
    private static final ListShape shapes = new ListShape();

    public static void parseFile(String fileName, ShapeStrategy shapeStrategy) throws IOException {
        Path path = Paths.get(fileName);

        try(Stream<String> lines = Files.lines(path)) {
            for (String line: lines.toList()) {
                String[] tokens = line.split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                shapes.add(shapeStrategy.parseShape(tokens[0], x1, y1, x2, y2));
            }
        }
    }

    public static void paintAll(Canvas canvas) {
        for (var shape: shapes.getShapes()) {
            shape.draw(canvas, Canvas.CanvasColor.BLACK);
        }
        canvas.render();
    }

    public static void onClick(Canvas canvas, int x, int y) {
        canvas.clear(Canvas.CanvasColor.WHITE);
        paintAll(canvas);
        var shape = shapes.getShapes().stream()
                .reduce((cur, acc) -> cur.distance(x, y) < acc.distance(x, y) ? cur : acc)
                .orElseThrow();
        shape.draw(canvas, Canvas.CanvasColor.ORANGE);
        canvas.render();
    }

    public static WindowSize minWindowSize() {
        var minWindowSize = shapes.getShapes().stream()
                .reduce((cur, acc) -> cur.minWindowSize().union(acc.minWindowSize()).width() > acc.minWindowSize().width()
                        || cur.minWindowSize().union(acc.minWindowSize()).height() > acc.minWindowSize().height() ?
                        cur : acc)
                .map(shape -> new WindowSize(shape.minWindowSize().width(), shape.minWindowSize().height()))
                .orElseThrow();
        return minWindowSize.width() < 500 || minWindowSize.height() < 500 ? new WindowSize(500, 500) : minWindowSize;
    }
}
