package fr.uge.poo.paint.ex8;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class Paint {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need file name");
        }
        Drawing.parseFile(args[0]);
        var windowSize = Drawing.minWindowSize();
        var serviceLoader = ServiceLoader.load(Canvas.class);

        try {
            var area = serviceLoader.findFirst().get().newGraphic("area", windowSize.width(), windowSize.height());
            area.clear(Canvas.CanvasColor.WHITE);
            Drawing.paintAll(area);
            area.waitForMouseClick((x, y) -> Drawing.onClick(area, x, y));
        } catch (NoSuchElementException e) {
            var area = new SimpleGraphicFactory().newGraphic("area", windowSize.width(), windowSize.height());
            area.clear(Canvas.CanvasColor.WHITE);
            Drawing.paintAll(area);
            area.waitForMouseClick((x, y) -> Drawing.onClick(area, x, y));
        }
    }
}
