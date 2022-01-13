package fr.uge.poo.paint.ex9;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class SimpleGraphicAdapter implements Canvas {
	private final SimpleGraphics area;
	private final LinkedBlockingDeque<Consumer<Graphics2D>> listGraphics2D = new LinkedBlockingDeque<>();
	private Consumer<Graphics2D> graphics2DConsumer = graphics2D -> {};

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
	public void clear(CanvasColor color) {
		area.clear(getColor(color));
	}

	@Override
	public void drawLine(CanvasColor color, int x, int y, int x1, int y1) {
		graphics2DConsumer = graphics2DConsumer.andThen(Graphics2D -> {
			Graphics2D.setColor(getColor(color));
			Graphics2D.drawLine(x, y, x1, y1);
		});
	}

	@Override
	public void drawRectangle(CanvasColor color, int x, int y, int width, int height) {
		graphics2DConsumer = graphics2DConsumer.andThen(Graphics2D -> {
			Graphics2D.setColor(getColor(color));
			Graphics2D.drawRect(x, y, width, height);
		});
	}

	@Override
	public void drawEllipse(CanvasColor color, int x, int y, int width, int height) {
		graphics2DConsumer = graphics2DConsumer.andThen(Graphics2D -> {
			Graphics2D.setColor(getColor(color));
			Graphics2D.drawOval(x, y, width, height);
		});
	}

	@Override
	public void render() {
		area.render(graphic -> {
			graphics2DConsumer.accept(graphic);
			graphics2DConsumer = graphics2D -> {};
		});
	}

	@Override
	public void waitForMouseClick(Callback callback) {
		area.waitForMouseEvents(callback::mouseClicked);
	}
}
