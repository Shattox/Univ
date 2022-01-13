package fr.uge.poo.paint.ex8;

import java.util.Objects;

public class SimpleGraphicFactory {
    public SimpleGraphicAdapter newGraphic(String name, int width, int height) {
        Objects.requireNonNull(name);
        return new SimpleGraphicAdapter(name, width, height);
    }
}
