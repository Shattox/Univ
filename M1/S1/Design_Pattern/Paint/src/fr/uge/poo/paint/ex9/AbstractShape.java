package fr.uge.poo.paint.ex9;

abstract class AbstractShape implements Shape {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public AbstractShape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    @Override
    public double distance(int x, int y) {
        var centerX = this.x + (width / 2);
        var centerY = this.y + (height / 2);
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
    }

    @Override
    public WindowSize minWindowSize() {
    	return new WindowSize(x + width, y + height);
    }
}
