package fr.uge.poo.paint.ex9;

@FunctionalInterface
public interface ShapeParsingStrategy {
    Shape parseShape(String shapeName, int x1, int y1, int x2, int y2);
}
