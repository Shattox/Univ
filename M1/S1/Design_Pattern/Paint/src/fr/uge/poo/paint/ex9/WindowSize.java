package fr.uge.poo.paint.ex9;

public record WindowSize(int width, int height) {
    public WindowSize union(WindowSize windowSize) {
        return width < windowSize.width || height < windowSize.height ?
                windowSize : new WindowSize(width, height);
    }
}
