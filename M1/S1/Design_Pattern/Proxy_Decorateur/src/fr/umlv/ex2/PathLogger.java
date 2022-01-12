package fr.umlv.ex2;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PathLogger implements Closeable, Log {
    private final Path path;
    private final BufferedWriter bufferedWriter;
    private final Log next;

    public PathLogger(Path path, Log next) throws IOException {
        this.path = Objects.requireNonNull(path);
        this.bufferedWriter = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        this.next = next;
    }

    @Override
    public void log(SystemLogger.Level level, String message) {
        try {
            bufferedWriter.write(level + " " + message + '\n');
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
