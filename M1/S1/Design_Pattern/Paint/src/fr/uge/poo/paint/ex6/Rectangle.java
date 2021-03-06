package fr.uge.poo.paint.ex6;

import java.awt.*;

public class Rectangle extends AbstractShape {
    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics, Color color) {
        graphics.setColor(color);
        graphics.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    @Override
    public Point center() { return super.center(); }

    @Override
    public Point getPoint() { return super.getPoint(); }
}
