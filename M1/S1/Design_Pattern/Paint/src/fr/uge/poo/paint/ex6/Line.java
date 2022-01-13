package fr.uge.poo.paint.ex6;

import java.awt.*;

public record Line(int x1, int y1, int x2, int y2) implements Shape {
    public void draw(Graphics2D graphics, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public Point center() {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    @Override
    public Point getPoint() { return new Point(x2, y2); }
}

