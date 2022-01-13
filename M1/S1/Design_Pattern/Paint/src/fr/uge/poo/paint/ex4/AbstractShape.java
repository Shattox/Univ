package fr.uge.poo.paint.ex4;

import java.awt.*;

abstract class AbstractShape implements Shape {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public AbstractShape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    @Override
    public String toString() { return x + " " + y + " " + width + " " + height; }

    @Override
    public Point center() { return new Point(x + (width / 2), y + (height / 2)); }

    @Override
    public abstract void draw(Graphics2D graphics);
}
