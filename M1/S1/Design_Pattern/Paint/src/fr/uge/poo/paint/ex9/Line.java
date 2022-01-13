package fr.uge.poo.paint.ex9;

public record Line(int x1, int y1, int x2, int y2) implements Shape {
	@Override
	public void draw(Canvas canvas, Canvas.CanvasColor color) {
		canvas.drawLine(color, x1, y1, x2, y2);
	}

	@Override
	public double distance(int x, int y) {
		var centerX = (x1 + x2) / 2;
		var centerY = (y1 + y2) / 2;
		return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
	}

	@Override
	public WindowSize minWindowSize() {
		return new WindowSize(x1 + x2, y1 + y2);
	}
}
