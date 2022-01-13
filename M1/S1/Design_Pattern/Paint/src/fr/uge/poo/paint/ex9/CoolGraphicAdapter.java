package fr.uge.poo.paint.ex9;

import fr.uge.poo.simplegraphics.CoolGraphics;

import java.util.Objects;

public class CoolGraphicAdapter implements Canvas {
	private final CoolGraphics area;

	public CoolGraphicAdapter(String name, int x, int y) {
		Objects.requireNonNull(name);
		area = new CoolGraphics(name, x, y);
	}

	private CoolGraphics.ColorPlus getColor(CanvasColor color) {
		switch (color) {
			case WHITE: return CoolGraphics.ColorPlus.WHITE;
			case BLACK: return CoolGraphics.ColorPlus.BLACK;
			case ORANGE: return CoolGraphics.ColorPlus.ORANGE;
			default: break;
		}
		return CoolGraphics.ColorPlus.WHITE;
	}

	@Override
	public void clear(CanvasColor color) {
		area.repaint(getColor(color));
	}

	@Override
	public void drawLine(CanvasColor color, int x, int y, int x1, int y1) {
		area.drawLine(x, y, x1, y1, getColor(color));
	}

	@Override
	public void drawRectangle(CanvasColor color, int x, int y, int width, int height) {
		area.drawLine(x, y, x + width , y, getColor(color));
		area.drawLine(x, y, x, y + height, getColor(color));
		area.drawLine(x, y + height, x + width, y, getColor(color));
		area.drawLine(x + width, y, x, y + height, getColor(color));
	}

	@Override
	public void drawEllipse(CanvasColor color, int x, int y, int width, int height) {
		area.drawEllipse(x, y, width, height, getColor(color));
	}

	@Override
	public void render() {}

	@Override
	public void waitForMouseClick(Callback callback) {
		area.waitForMouseEvents(callback::mouseClicked);
	}
}
