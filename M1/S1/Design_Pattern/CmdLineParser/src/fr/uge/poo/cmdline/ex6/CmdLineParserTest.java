package fr.uge.poo.cmdline.ex6;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("static-method")
class CmdLineParserTest {
    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new fr.uge.poo.cmdline.ex2.CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }

    @Test
    public void processShouldFailOnNoObligatoryOption() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "-border-width"};
        var thrown = assertThrows(IllegalArgumentException.class, () -> cmdParser.process(arguments));
        assertEquals("At least one obligatory option is missing", thrown.getMessage());
    }

    @Test
    public void processShouldFailOnIncorrectNumberOfParam1() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "-window-name", "-border-width", "500"};
        var thrown = assertThrows(IllegalArgumentException.class, () -> cmdParser.process(arguments));
        assertEquals("-window-name has no parameter", thrown.getMessage());
    }

    @Test
    public void processShouldFailOnIncorrectNumberOfParam2() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "-window-name", "area", "-min-size", "250"};
        var thrown = assertThrows(IllegalArgumentException.class, () -> cmdParser.process(arguments));
        assertEquals("-min-size need 2 parameter(s) but got 1", thrown.getMessage());
    }

    @Test
    public void processReturnCorrectNumberOfFiles() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "-window-name", "area", "-min-size", "250", "200", "file", "file2"};
        assertEquals(2, cmdParser.process(arguments).size());
    }

    @Test
    public void processReturnCorrectFilesName() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "file8", "-window-name", "area", "-min-size", "250", "200", "file", "file2"};
        assertEquals(List.of("file8", "file", "file2"), cmdParser.process(arguments));
    }

    @Test
    public void processShouldFailOnUnknownOption() {
        var cmdParser = initCmdLineParser();
        String[] arguments = {"-legacy", "-no-borders", "file8", "-window-name", "area", "-window-color", "blue"};
        var thrown = assertThrows(IllegalArgumentException.class, () -> cmdParser.process(arguments));
        assertEquals("-window-color is not an valid option", thrown.getMessage());
    }

    @Test
    public void processRequiredOption() {
        var cmdParser = new CmdLineParser();
        var option = new CmdLineParser.Option.OptionBuilder("-test",0, l->{}).required().build();
        cmdParser.addOption(option);
        cmdParser.addFlag("-test1",() -> {});
        String[] arguments = {"-test1","a","b"};
        assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
    }

    @Test
    public void processConflicts() {
        var cmdParser = new CmdLineParser();
        var option= new CmdLineParser.Option.OptionBuilder("-test",0, l->{}).conflictWith("-test1").build();
        cmdParser.registerOption(option);
        var option2= new CmdLineParser.Option.OptionBuilder("-test1",0, l->{}).build();
        cmdParser.registerOption(option2);
        String[] arguments = {"-test","-test1"};
        assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
    }

    @Test
    public void processConflicts2() {
        var cmdParser = new CmdLineParser();
        var option= new CmdLineParser.Option.OptionBuilder("-test",0, l->{}).conflictWith("-test1").build();
        cmdParser.registerOption(option);
        var option2= new CmdLineParser.Option.OptionBuilder("-test1",0, l->{}).build();
        cmdParser.registerOption(option2);
        String[] arguments = {"-test1","-test"};
        assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
    }

    @Test
    public void processConflictsAndAliases() {
        var cmdParser = new CmdLineParser();
        var option= new CmdLineParser.Option.OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
        cmdParser.registerOption(option);
        var option2= new CmdLineParser.Option.OptionBuilder("-test1",0, l->{}).alias("-test2").build();
        cmdParser.registerOption(option2);
        String[] arguments = {"-test1","-test"};
        assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
    }

    @Test
    public void processConflictsAndAliases2() {
        var cmdParser = new CmdLineParser();
        var option= new CmdLineParser.Option.OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
        cmdParser.registerOption(option);
        var option2= new CmdLineParser.Option.OptionBuilder("-test1",0, l->{}).alias("-test2").build();
        cmdParser.registerOption(option2);
        String[] arguments = {"-test","-test1"};
        assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
    }

    private CmdLineParser initCmdLineParser() {
        var cmdParser = new CmdLineParser();
        var options = new PaintSettings.PaintSettingsBuilder();

        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-legacy")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setLegacy(true)).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-with-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(true)).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-no-borders")
                .setNumberParam(0)
                .setIsObligatory(false)
                .setAction(action -> options.setBordered(false)).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-border-width")
                .setNumberParam(1)
                .setIsObligatory(false)
                .setAction(options::setBorderWidth).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-window-name")
                .setNumberParam(1)
                .setIsObligatory(true)
                .setAction(options::setWindowName).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-min-size")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setWindowSize).build());
        cmdParser.registerOption(new CmdLineParser.Option.OptionBuilder().setName("-remote-server")
                .setNumberParam(2)
                .setIsObligatory(false)
                .setAction(options::setRemoteServer).build());

        return cmdParser;
    }
}