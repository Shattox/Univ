package fr.umlv.ex2;

import java.io.IOException;

public interface Log {
    enum Level {
        ERROR, WARNING, INFO
    }

    void log(SystemLogger.Level level, String message) throws IOException;
}
