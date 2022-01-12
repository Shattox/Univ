package fr.uge.poo.paint.ex8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
