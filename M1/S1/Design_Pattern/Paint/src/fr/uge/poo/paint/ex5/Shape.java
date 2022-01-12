package fr.uge.poo.paint.ex5;

import java.awt.*;

public interface Shape {
    void draw(Graphics2D graphics, Color color);
    Point center();
}
