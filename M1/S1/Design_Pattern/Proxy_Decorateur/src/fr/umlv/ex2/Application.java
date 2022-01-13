package fr.umlv.ex2;

import java.io.IOException;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) throws IOException {
        try (var path = new PathLogger(Path.of("tmp/log.txt"), null)) {
            var logger = new FilterLog(path, SystemLogger.getInstance(), level -> level.ordinal() <= Log.Level.WARNING.ordinal());
            logger.log(Log.Level.INFO, "info");
            logger.log(Log.Level.WARNING, "warning");
            logger.log(Log.Level.ERROR, "error");
        }
    }
}
