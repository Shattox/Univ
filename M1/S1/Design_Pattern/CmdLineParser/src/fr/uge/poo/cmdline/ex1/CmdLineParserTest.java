package fr.uge.poo.cmdline.ex1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("static-method")
class CmdLineParserTest {
    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }

    @Test
    public void attemptedOptions() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));

        assertEquals(Set.of("-legacy", "-with-borders"), cmdParser.getRegisteredOptions().keySet());
    }

    @Test
    public void noDoubleOption() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));

        assertEquals(Set.of("-legacy", "-with-borders"), cmdParser.getRegisteredOptions().keySet());
    }

    @Test
    public void optionCountCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));

        assertEquals(3, cmdParser.getRegisteredOptions().size());
    }

    @Test
    public void attemptedFile() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));

        assertEquals(List.of("filename1", "filename2"), cmdParser.process(arguments));
    }

    @Test
    public void numberFileCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));

        assertEquals(2, cmdParser.process(arguments).size());
    }

    @Test
    public void actionExecutionCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));

        cmdParser.process(arguments);

        assertTrue(options.isLegacy());
        assertTrue(options.isBordered());
    }
}