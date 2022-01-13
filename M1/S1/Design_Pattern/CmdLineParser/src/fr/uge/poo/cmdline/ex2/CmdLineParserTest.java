package fr.uge.poo.cmdline.ex2;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));

        assertEquals(Set.of("-legacy", "-with-borders"), cmdParser.getRegisteredOptions().keySet());
    }

    @Test
    public void noDoubleOption() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));

        assertEquals(Set.of("-legacy", "-with-borders"), cmdParser.getRegisteredOptions().keySet());
    }

    @Test
    public void optionCountCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", (it) -> options.setBordered(false));

        assertEquals(3, cmdParser.getRegisteredOptions().size());
    }

    @Test
    public void attemptedFile() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", (it) -> options.setBordered(false));

        assertEquals(List.of("filename1", "filename2"), cmdParser.process(arguments));
    }

    @Test
    public void numberFileCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", (it) -> options.setBordered(false));

        assertEquals(2, cmdParser.process(arguments).size());
    }

    @Test
    public void actionExecutionCorrect() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "filename1", "filename2"};

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));

        cmdParser.process(arguments);

        assertTrue(options.isLegacy());
        assertTrue(options.isBordered());
    }

    @Test
    public void correctOptionWithParameter() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-border-width 100", "-window-name area", "filename1", "filename2", "-min-size 50 50"};

        cmdParser.registerWithParameter("-border-width", options::setBorderWidth);
        cmdParser.registerWithParameter("-window-name", options::setWindowName);
        cmdParser.registerWithParameter("-min-size", options::setWindowSize);
        cmdParser.process(arguments);

        assertEquals("PaintSettings [ bordered=true, legacy=false, borderWidth=100, windowName=area, minSize=50 50 ]",
                options.toString());
    }

    @Test
    public void correctNumberOptionWithParameter() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-border-width 100", "-window-name area", "filename1", "filename2", "-min-size 50 50"};

        cmdParser.registerWithParameter("-window-name", options::setWindowName);
        cmdParser.registerWithParameter("-min-size", options::setWindowName);
        cmdParser.registerWithParameter("-border-width", options::setWindowName);

        cmdParser.process(arguments);

        assertEquals(3, cmdParser.getRegisteredOptions().keySet().size());
    }
}