package fr.uge.poo.paint.ex9;

import java.io.IOException;

public class Paint {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need file name");
        }
        var shapeStrategy = new ShapeStrategy();
        Drawing.parseFile(args[0], shapeStrategy);
        var windowSize = Drawing.minWindowSize();

        if (args.length == 2 && args[1].equals("-legacy")) {
            var area = new SimpleGraphicAdapter("area", windowSize.width(), windowSize.height());
            area.clear(Canvas.CanvasColor.WHITE);
            Drawing.paintAll(area);
            area.waitForMouseClick((x, y) -> Drawing.onClick(area, x, y));
        } else {
            var area = new CoolGraphicAdapter("area", windowSize.width(), windowSize.height());
            area.clear(Canvas.CanvasColor.WHITE);
            Drawing.paintAll(area);
            area.waitForMouseClick((x, y) -> Drawing.onClick(area, x, y));
        }
    }
}
