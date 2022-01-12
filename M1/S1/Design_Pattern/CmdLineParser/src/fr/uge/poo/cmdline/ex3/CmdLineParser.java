package fr.uge.poo.cmdline.ex3;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {
    private final Map<String, Consumer<Iterator<String>>> registeredOptions = new HashMap<>();

    public void registerOption(String option, Runnable runnable) {
        Objects.requireNonNull(option);
        registeredOptions.put(option, it -> runnable.run());
    }

    public void registerWithParameter(String option, Consumer<Iterator<String>> consumer) {
        Objects.requireNonNull(option);
        registeredOptions.put(option, consumer);
    }

    public List<String> process(String[] arguments) {
        var files = new ArrayList<String>();
        var it = Arrays.stream(arguments).iterator();

        while (it.hasNext()) {
            var next = it.next();
            if (registeredOptions.containsKey(next)) {
                registeredOptions.get(next).accept(it);
            } else {
                files.add(next);
            }
        }
        return files;
    }
}