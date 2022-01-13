package fr.uge.poo.paint.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListShape {
    private final List<Shape> shapes = new ArrayList<>();

    public void add(Shape draw) {
        Objects.requireNonNull(draw);
        shapes.add(draw);
    }

    public List<Shape> getShapes() {
        return List.copyOf(shapes);
    }
}
