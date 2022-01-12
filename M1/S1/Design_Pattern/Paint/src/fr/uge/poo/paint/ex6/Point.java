package fr.uge.poo.paint.ex6;

public record Point(int x, int y) {
    public static Point setPoint(Point p) {
        return new Point(p.x, p.y);
    }

    public static double distance(Point point1, Point point2) {
        return Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2);
    }
}
