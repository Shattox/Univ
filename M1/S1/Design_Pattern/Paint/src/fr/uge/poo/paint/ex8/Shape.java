package fr.uge.poo.paint.ex8;

import fr.uge.poo.paint.ex8.Canvas.CanvasColor;

public interface Shape {
    void draw(Canvas canvas, CanvasColor color);
    double distance(int x, int y);
    WindowSize minWindowSize();
}
