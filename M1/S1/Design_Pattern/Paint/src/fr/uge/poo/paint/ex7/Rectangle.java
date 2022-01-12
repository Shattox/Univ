package fr.uge.poo.paint.ex7;

import fr.uge.poo.paint.ex7.Canvas.CanvasColor;

public class Rectangle extends AbstractShape {
    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

	@Override
	public void draw(Canvas canvas, CanvasColor color) {
		canvas.drawRectangle(color, super.getX(), super.getY(), super.getWidth(), super.getHeight());
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
