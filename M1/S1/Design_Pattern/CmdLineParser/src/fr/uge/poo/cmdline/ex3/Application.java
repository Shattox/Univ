package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        var options = new PaintSettings.PaintSettingsBuilder();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "-border-width", "100", "-remote-server", "localhost", "80",
                "-window-name", "area", "filename1", "filename2", "-min-size", "250", "250"};

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));

        cmdParser.registerWithParameter("-border-width", options::setBorderWidth);
        cmdParser.registerWithParameter("-window-name", options::setWindowName);
        cmdParser.registerWithParameter("-min-size", options::setWindowSize);
        cmdParser.registerWithParameter("-remote-server", options::setRemoteServer);

        List<String> result = cmdParser.process(arguments);
        List<Path> files = result.stream().map(Path::of).collect(Collectors.toList());

        var paintSettings = options.build();

        // this code replaces the rest of the application
        files.forEach(System.out::println);

        System.out.println(paintSettings);
    }
}
