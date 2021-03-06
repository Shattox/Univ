package fr.uge.poo.cmdline.ex6;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static fr.uge.poo.cmdline.ex6.CmdLineParser.Option;

public class Application {
    public static void main(String[] args) {
        var options = new PaintSettings.PaintSettingsBuilder();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "-border-width", "100", "-remote-server", "localhost", "80",
                "-window-name", "area", "filename1", "filename2", "-min-size", "250", "250", "--help", "-v"};

        cmdParser.addFlag(new Option.OptionBuilder().setName("-legacy")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setLegacy(true))
                .setAlias(List.of())
                .setDocumentation("useless.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-with-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(true))
                .setAlias(List.of())
                .setDocumentation("allows to set window border if this option is true.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-no-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(false))
                .setAlias(List.of())
                .setDocumentation("allows to not set window border if this option is true.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-border-width")
                .setNumberParam(1)
                .setIsObligatory(false)
                .setAction(options::setBorderWidth)
                .setAlias(List.of())
                .setDocumentation("takes 1 parameter which indicate the border width.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-window-name")
                .setNumberParam(1)
                .setIsObligatory(true)
                .setAction(options::setWindowName)
                .setAlias(List.of())
                .setDocumentation("takes 1 parameter which indicate the window name.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-min-size")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setWindowSize)
                .setAlias(List.of())
                .setDocumentation("takes 2 parameters the width and height of the window. (can't be less than (500, 500).").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-remote-server")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setRemoteServer)
                .setAlias(List.of())
                .setDocumentation("takes 2 parameters (hostname, port) define server hostname and port.").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-h")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> cmdParser.usage())
                .setAlias(List.of("--help"))
                .setDocumentation("shows documentation of options").build());
        cmdParser.addFlag(new Option.OptionBuilder().setName("-v")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setVersion())
                .setAlias(List.of("--version"))
                .setDocumentation("shows the version of this application.").build());

        List<String> result = cmdParser.process(arguments);
        List<Path> files = result.stream().map(Path::of).collect(Collectors.toList());

        var paintSettings = options.build();

        // this code replaces the rest of the application
        files.forEach(System.out::println);

        System.out.println(paintSettings);
    }
}
