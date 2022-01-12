package fr.uge.poo.paint.ex8;

public interface Canvas {
	enum CanvasColor {
        WHITE,
        BLACK,
        ORANGE,
	}

	Canvas newGraphic(String name, int x, int y);
	void clear(CanvasColor color);
	void drawLine(CanvasColor color, int x, int y, int x1, int y1);
	void drawRectangle(CanvasColor color, int x, int y, int width, int height);
	void drawEllipse(CanvasColor color, int x, int y, int width, int height);
	void waitForMouseClick(Callback callback);
}
