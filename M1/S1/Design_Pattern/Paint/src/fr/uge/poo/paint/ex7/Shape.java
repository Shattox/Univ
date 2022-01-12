package fr.uge.poo.paint.ex7;

import fr.uge.poo.paint.ex7.Canvas.CanvasColor;

public interface Shape {
    void draw(Canvas canvas, CanvasColor color);
    double distance(int x, int y);
    WindowSize minWindowSize();
}
