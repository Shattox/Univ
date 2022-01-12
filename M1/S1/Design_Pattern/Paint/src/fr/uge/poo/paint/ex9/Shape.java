package fr.uge.poo.paint.ex9;

public interface Shape {
    void draw(Canvas canvas, Canvas.CanvasColor color);
    double distance(int x, int y);
    WindowSize minWindowSize();
}
