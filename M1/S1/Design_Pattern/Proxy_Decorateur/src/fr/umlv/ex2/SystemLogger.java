package fr.umlv.ex2;

public class SystemLogger implements Log {
    private static final SystemLogger INSTANCE = new SystemLogger();

    private SystemLogger() {
    }

    public static SystemLogger getInstance() {
        return INSTANCE;
    }

    @Override
    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}