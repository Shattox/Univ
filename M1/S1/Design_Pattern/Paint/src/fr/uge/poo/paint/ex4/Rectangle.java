package fr.uge.poo.paint.ex4;

import java.awt.*;

public class Rectangle extends AbstractShape {
    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    public Point center() { return super.center(); }

    @Override
    public String toString() { return "rectangle " + super.toString(); }
}
