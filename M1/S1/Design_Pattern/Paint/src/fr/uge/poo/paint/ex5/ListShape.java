package fr.uge.poo.paint.ex5;

import java.util.*;

public class ListShape {
    private final List<Shape> shapes = new ArrayList<>();

    public void add(Shape shape) {
        Objects.requireNonNull(shape);
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return List.copyOf(shapes);
    }
}
