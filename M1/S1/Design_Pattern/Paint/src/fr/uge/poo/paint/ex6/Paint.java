package fr.uge.poo.paint.ex6;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;

public class Paint {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need file name");
        }
        Drawing.parseFile(args[0]);
        Point size = Drawing.getWindowSize();

        SimpleGraphics area = new SimpleGraphics("area", size.x(), size.y());

        Drawing.paintAll(area);

        area.waitForMouseEvents((x, y) -> Drawing.onClick(area, new Point(x, y)));
    }
}
