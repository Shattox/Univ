package fr.uge.poo.paint.ex8;

public class Ellipse extends AbstractShape {
    public Ellipse(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Canvas canvas, Canvas.CanvasColor color) {
        canvas.drawEllipse(color, super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    @Override
    public double distance(int x, int y) {
        return super.distance(x, y);
    }

    @Override
    public WindowSize minWindowSize() {
        return super.minWindowSize();
    }
}
