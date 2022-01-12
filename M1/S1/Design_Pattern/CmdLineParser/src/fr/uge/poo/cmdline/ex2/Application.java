package fr.uge.poo.cmdline.ex2;

import javax.swing.text.html.HTMLDocument;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Application {

    public static class PaintSettings {
        private boolean legacy = false;
        private boolean bordered = true;
        private Iterator<String> borderWidth;
        private Iterator<String> windowName;
        private Iterator<String> minSize;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public void setBordered(boolean bordered){
            this.bordered=bordered;
        }

        public void setBorderWidth(Iterator<String> borderWidth) { this.borderWidth = borderWidth; }

        public void setWindowName(Iterator<String> windowName) { this.windowName = windowName; }

        public void setWindowSize(Iterator<String> minSize) { this.minSize = minSize; }

        public boolean isLegacy(){
            return legacy;
        }

        public boolean isBordered(){
            return bordered;
        }

        public String iteratorToString(Iterator<String> it) {
            var stringJoiner = new StringJoiner(" ", "", "");
            if (it.hasNext()) { it.next(); }
            it.forEachRemaining(stringJoiner::add);
            return stringJoiner.toString();
        }

        @Override
        public String toString() {
            return "PaintSettings [ " + "bordered=" + bordered + ", legacy=" + legacy +
                    ", borderWidth=" + iteratorToString(borderWidth) +
                    ", windowName=" + iteratorToString(windowName) +
                    ", minSize=" + iteratorToString(minSize) + " ]";
        }
    }

    public static void main(String[] args) {
        var options = new PaintSettings();
        var cmdParser = new CmdLineParser();

        String[] arguments = {"-legacy", "-no-borders", "-border-width 100",
                "-window-name area", "filename1", "filename2", "-min-size 50 50"};

        cmdParser.registerOption("-legacy", (it) -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", (it) -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", (it) -> options.setBordered(false));

        cmdParser.registerWithParameter("-border-width", options::setBorderWidth);
        cmdParser.registerWithParameter("-window-name", options::setWindowName);
        cmdParser.registerWithParameter("-min-size", options::setWindowSize);

        List<String> result = cmdParser.process(arguments);
        List<Path> files = result.stream().map(Path::of).collect(Collectors.toList());

        // this code replaces the rest of the application
        files.forEach(System.out::println);

        System.out.println(options);
    }
}
