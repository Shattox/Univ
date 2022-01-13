package fr.uge.poo.paint.ex6;

import java.awt.*;

public interface Shape {
    void draw(Graphics2D graphics, Color color);
    Point center();
    Point getPoint();
}
