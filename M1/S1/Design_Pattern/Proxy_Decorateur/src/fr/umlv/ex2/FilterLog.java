package fr.umlv.ex2;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterLog implements Log {
    private final Log next;
    private final Log logger;
    private final Predicate<Level> predicate;

    public FilterLog(Log logger, Log next, Predicate<Level> predicate) {
        this.next = next;
        this.logger = Objects.requireNonNull(logger);
        this.predicate = Objects.requireNonNull(predicate);
    }

    @Override
    public void log(Level level, String message) throws IOException {
        if (next != null) {
            next.log(level, message);
        }
        if (predicate.test(level)) {
            logger.log(level, message);
        }
    }
}
