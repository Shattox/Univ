package fr.uge.poo.paint.ex5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;

public class Paint {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Need file name");
        }
        SimpleGraphics area = new SimpleGraphics("area", 800, 600);

        Drawing.parseFile(args[0]);
        Drawing.paintAll(area);

        area.waitForMouseEvents((x, y) -> Drawing.onClick(area, new Point(x, y)));
    }
}
