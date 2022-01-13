package fr.uge.poo.cmdline.ex1;

import java.util.*;

public class CmdLineParser {
    private final Map<String, Runnable> registeredOptions = new HashMap<>();

    public Map<String, Runnable> getRegisteredOptions() {
        return Map.copyOf(registeredOptions);
    }

    public void registerOption(String option, Runnable runnable) {
        Objects.requireNonNull(option);
        registeredOptions.put(option, runnable);
    }

    public List<String> process(String[] arguments) {
        var files = new ArrayList<String>();

        for (String argument: arguments) {
            if (registeredOptions.containsKey(argument)) {
                registeredOptions.get(argument).run();
            } else {
                files.add(argument);
            }
        }
        return files;
    }
}