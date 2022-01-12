package fr.uge.poo.cmdline.ex5;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {
    static class Option {
        private final String name;
        private final int numberParam;
        private final boolean isObligatory;
        private final Consumer<List<String>> action;
        private final List<String> aliases;
        private final String documentation;

        public static class OptionBuilder {
            private String name;
            private int numberParam;
            private boolean isObligatory;
            private Consumer<List<String>> action;
            private List<String> aliases;
            private String documentation;

            public OptionBuilder setName(String name) {
                Objects.requireNonNull(name);
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

            public OptionBuilder setOtherNames(List<String> aliases) {
                this.aliases = aliases;
                return this;
            }

            public OptionBuilder setDocumentation(String documentation) {
                this.documentation = documentation;
                return this;
            }

            public Option build() {
                return new Option(this.name, this.numberParam, this.isObligatory, this.action, this.aliases, this.documentation);
            }
        }

        private Option(String name, int numberParam, boolean isObligatory,Consumer<List<String>> action, List<String> aliases, String documentation) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(action);
            this.name = name;
            if (numberParam < 0) {
                throw new IllegalArgumentException("number of option param need to be greater or equals than 0");
            }
            this.numberParam = numberParam;
            this.action = action;
            this.isObligatory = isObligatory;
            this.aliases = aliases;
            this.documentation = documentation;
        }
    }

    private final Map<String, Option> registeredOptions = new HashMap<>();

    public void registerOption(Option option) {
        Objects.requireNonNull(option);
        if(option.name.charAt(0) != '-') {
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
            var optionName = isRegisteredOption(next);
            if (optionName != null) {
                var args = args(it, registeredOptions.get(optionName));
                registeredOptions.get(optionName).action.accept(args);
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
                if (startWithOneOrTwoHyphen(next)) {
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
    private boolean startWithOneOrTwoHyphen(String name) {
        Objects.requireNonNull(name);
        return name.charAt(0) == '-' || name.startsWith("--");
    }

    /**
     * Verify if name is a registered option by checking map keys and
     * option other name(s) if exists. throw IAE if name is not an option.
     * @param name the option name
     */
    private String isRegisteredOption(String name) {
        if (registeredOptions.containsKey(name)) {
            return name;
        }
        for (var option: registeredOptions.keySet()) {
            var aliases = registeredOptions.get(option).aliases;
            if (aliases != null) {
                for (var alias: aliases) {
                    if (name.equals(alias)) {
                        return registeredOptions.get(option).name;
                    }
                }
            }
        }
        return null;
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

    /**
     * display declared options with their documentation if exists.
     */
    public void usage() {
        var sortedOptionsKey = registeredOptions.keySet().stream().sorted().toList();
        for (var key: sortedOptionsKey) {
            var option = registeredOptions.get(key);
            System.out.print(option.name + " ");
            if (option.aliases != null) {
                for (var alias : option.aliases) {
                    System.out.print(alias + " ");
                }
            }
            System.out.println(": " + option.documentation);
        }
    }
}