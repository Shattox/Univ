package fr.uge.poo.cmdline.ex4;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {
    public static class Option {
        private final String name;
        private final int numberParam;
        private final boolean isObligatory;
        private final Consumer<List<String>> action;

        public static class OptionBuilder {
            private String name;
            private int numberParam;
            private boolean isObligatory;
            private Consumer<List<String>> action;

            public OptionBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public OptionBuilder setNumberParam(int numberParam) {
                this.numberParam = numberParam;
                return this;
            }

            public OptionBuilder setIsObligatory(boolean isObligatory) {
                this.isObligatory = isObligatory;
                return this;
            }

            public OptionBuilder setAction(Consumer<List<String>> action) {
                this.action = action;
                return this;
            }

            public Option build() {
                return new Option(this.name, this.numberParam, this.isObligatory, this.action);
            }
        }

        private Option(String name, int numberParam, boolean isObligatory, Consumer<List<String>> action) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(action);
            this.name = name;
            if (numberParam < 0) {
                throw new IllegalArgumentException("number of option param need to be greater or equals than 0");
            }
            this.numberParam = numberParam;
            this.action = action;
            this.isObligatory = isObligatory;
        }
    }

    private final Map<String, Option> registeredOptions = new HashMap<>();

    public Map<String, Option> getRegisteredOptions() {
        return Map.copyOf(registeredOptions);
    }

    public void registerOption(Option option) {
        if(!isOption(option.name)) {
            throw new IllegalArgumentException(option.name + " is not an valid option");
        }
        registeredOptions.put(option.name, option);
    }

    public List<String> process(String[] arguments) {
        var files = new ArrayList<String>();
        var it = Arrays.stream(arguments).iterator();

        obligatoryOptionsIsPresent(arguments);
        while (it.hasNext()) {
            var next = it.next();
            if (isOption(next) && ! registeredOptions.containsKey(next)) {
                throw new IllegalArgumentException(next + " is not an valid option");
            }
            if (registeredOptions.containsKey(next)) {
                var args = args(it, registeredOptions.get(next));
                registeredOptions.get(next).action.accept(args);
            } else {
                files.add(next);
            }
        }
        return files;
    }

    /**
     * Return the list of parameter(s) of the given option.
     * @param it iterator at the current option.
     * @param option the current option.
     * @return list of option parameter.
     */
    private List<String> args(Iterator<String> it, Option option) {
        var args = new ArrayList<String>();

        for (var i = 0; i < option.numberParam; i++) {
            if (it.hasNext()) {
                var next = it.next();
                if (isOption(next)) {
                    throw new IllegalArgumentException(option.name + " has no parameter");
                }
                args.add(next);
            } else {
                throw new IllegalArgumentException(option.name + " need " + option.numberParam +
                        " parameter(s) but got " + i);
            }
        }
        return args;
    }

    /**
     * Verify if name is a legal option.
     * @param name the option name.
     */
    private boolean isOption(String name) {
        Objects.requireNonNull(name);
        return name.charAt(0) == '-';
    }

    /**
     * Verify if options set by the users contains all obligatory options.
     * @param arguments array of user option.
     */
    private void obligatoryOptionsIsPresent(String[] arguments) {
        var obligatoryOptions = registeredOptions.values().stream()
                .filter(option -> option.isObligatory)
                .map(option -> option.name).toList();
        var args = Arrays.stream(arguments).toList();
        if (!args.containsAll(obligatoryOptions)) {
            throw new IllegalArgumentException("At least one obligatory option is missing");
        }
    }
}