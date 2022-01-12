package fr.uge.poo.paint.ex8;

import fr.uge.poo.simplegraphics.SimpleGraphics;
import java.awt.Color;
import java.util.Objects;

public class SimpleGraphicAdapter implements Canvas {
	private final SimpleGraphics area;
	
	public SimpleGraphicAdapter(String name, int x, int y) {
		Objects.requireNonNull(name);
		area = new SimpleGraphics(name, x, y);
	}
	
	private Color getColor(CanvasColor color) {
		switch (color) {
			case WHITE: return Color.WHITE;
			case BLACK: return Color.BLACK;
			case ORANGE: return Color.ORANGE;
			default: break;
		}
		return Color.WHITE;
	}

	@Override
	public Canvas newGraphic(String name, int x, int y) {
		return new SimpleGraphicAdapter(name, x, y);
	}

	@Override
	public void clear(CanvasColor color) {
		area.clear(getColor(color));
	}

	@Override
	public void drawLine(CanvasColor color, int x, int y, int x1, int y1) {
		area.render(graphic -> {
			graphic.setColor(getColor(color));
			graphic.drawLine(x, y, x1, y1);
		});
	}

	@Override
	public void drawRectangle(CanvasColor color, int x, int y, int width, int height) {
		area.render(graphic -> {
			graphic.setColor(getColor(color));
			graphic.drawRect(x, y, width, height);

		});
	}

	@Override
	public void drawEllipse(CanvasColor color, int x, int y, int width, int height) {
		area.render(graphic -> {
			graphic.setColor(getColor(color));
			graphic.drawOval(x, y, width, height);
		});
	}

	@Override
	public void waitForMouseClick(Callback callback) {
		area.waitForMouseEvents(callback::mouseClicked);
	}
}
