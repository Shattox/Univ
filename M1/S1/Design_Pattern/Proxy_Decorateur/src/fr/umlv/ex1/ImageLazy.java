package fr.umlv.ex1;

import java.util.Objects;

public class ImageLazy implements Image {
    private Image image;
    private final String url;

    public ImageLazy(String url) {
        this.url = Objects.requireNonNull(url);
    }

    private void initialise() {
        if (image != null) {
            return;
        }
        image = Image.download(url);
    }

    @Override
    public String name() {
        initialise();
        return image.name();
    }

    @Override
    public int size() {
        initialise();
        return image.size();
    }

    @Override
    public double hue() {
        initialise();
        return image.hue();
    }
}
