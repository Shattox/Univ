package fr.uge.poo.cmdline.ex2;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {
    private final Map<String, Consumer<Iterator<String>>> registeredOptions = new HashMap<>();

    public void registerOption(String option, Consumer<Iterator<String>> consumer) {
        Objects.requireNonNull(option);
        registeredOptions.put(option, consumer);
    }

    public void registerWithParameter(String option, Consumer<Iterator<String>> consumer) {
        Objects.requireNonNull(option);
        registeredOptions.put(option, consumer);
    }

    public Map<String, Consumer<Iterator<String>>> getRegisteredOptions() {
        return Map.copyOf(registeredOptions);
    }

    public List<String> process(String[] arguments) {
        var files = new ArrayList<String>();

        for (String argument: arguments) {
            var tokens = argument.split(" ");
            if (registeredOptions.containsKey(tokens[0])) {
                var it = Arrays.stream(tokens).iterator();
                registeredOptions.get(tokens[0]).accept(it);
            }
            else {
                files.add(argument);
            }
        }
        return files;
    }
}