package fr.uge.poo.cmdline.ex4;

import static fr.uge.poo.cmdline.ex4.CmdLineParser.Option;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        var options = new PaintSettings.PaintSettingsBuilder();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "-border-width", "100", "-remote-server", "localhost", "80",
                "-window-name", "area", "filename1", "filename2", "-min-size", "250", "250"};

        cmdParser.registerOption(new Option.OptionBuilder().setName("-legacy")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setLegacy(true)).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-with-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(true)).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-no-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(false)).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-border-width")
                .setNumberParam(1)
                .setIsObligatory(false)
                .setAction(options::setBorderWidth).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-window-name")
                .setNumberParam(1)
                .setIsObligatory(true)
                .setAction(options::setWindowName).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-min-size")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setWindowSize).build());
        cmdParser.registerOption(new Option.OptionBuilder().setName("-remote-server")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setRemoteServer).build());

        List<String> result = cmdParser.process(arguments);
        List<Path> files = result.stream().map(Path::of).collect(Collectors.toList());

        var paintSettings = options.build();

        // this code replaces the rest of the application
        files.forEach(System.out::println);

        System.out.println(paintSettings);
    }
}
