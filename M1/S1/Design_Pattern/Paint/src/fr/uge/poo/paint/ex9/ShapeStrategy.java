package fr.uge.poo.paint.ex9;

public class ShapeStrategy implements ShapeParsingStrategy {
    @Override
    public Shape parseShape(String shapeName, int x1, int y1, int x2, int y2) {
        switch (shapeName) {
            case "line" -> { return new Line(x1, y1, x2, y2); }
            case "rectangle" -> { return new Rectangle(x1, y1, x2, y2); }
            case "ellipse" -> { return new Ellipse(x1, y1, x2, y2); }
            default -> throw new IllegalArgumentException("Unknown shape");
        }
    }
}
