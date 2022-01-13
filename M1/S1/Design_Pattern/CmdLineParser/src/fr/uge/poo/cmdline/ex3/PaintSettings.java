package fr.uge.poo.cmdline.ex3;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintSettings {
    private final boolean legacy;
    private final boolean bordered;
    private final int borderWidth;
    private final String windowName;
    private final int minSizeX;
    private final int minSizeY;
    private final InetSocketAddress server;

    public static class PaintSettingsBuilder {
        private boolean legacy = false;
        private boolean bordered = false;
        private int borderWidth = 10;
        private int minSizeX = 500;
        private int minSizeY = 500;
        private String windowName;
        private InetSocketAddress server;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public void setBordered(boolean bordered) {
            this.bordered = bordered;
        }

        public void setBorderWidth(Iterator<String> borderWidth) {
            if (borderWidth.hasNext()) {
                this.borderWidth = Integer.parseInt(borderWidth.next());
            } else {
                throw new IllegalArgumentException("-border-width option need 1 parameter");
            }
        }

        public void setWindowName(Iterator<String> windowName) {
            if (windowName.hasNext()) {
                this.windowName = windowName.next();
            } else {
                throw new IllegalArgumentException("-window-name option need 1 parameter");
            }
        }

        public void setWindowSize(Iterator<String> minSize) {
            var parameters = new ArrayList<Integer>();

            for (var i = 0; i < 2 && minSize.hasNext(); i++) {
                parameters.add(Integer.parseInt(minSize.next()));
            }
            if (parameters.size() != 2) {
                throw new IllegalArgumentException("-min-size option need 2 parameters");
            }
            this.minSizeX = parameters.get(0);
            this.minSizeY = parameters.get(1);
        }

        public void setRemoteServer(Iterator<String> remoteServer) {
            var parameters = new ArrayList<String>();

            for (var i = 0; i < 2 && remoteServer.hasNext(); i++) {
                parameters.add(remoteServer.next());
            }
            if (parameters.size() != 2) {
                throw new IllegalArgumentException("-remote-server option need 2 parameters");
            }
            this.server = new InetSocketAddress(parameters.get(0), Integer.parseInt(parameters.get(1)));
        }

        public PaintSettings build() {
            if (windowName == null) {
                throw new IllegalArgumentException("Need a window name");
            }
            return new PaintSettings(this.legacy, this.bordered, this.borderWidth, this.windowName,
                    this.minSizeX, this.minSizeY, this.server);
        }
    }
    private PaintSettings(boolean legacy, boolean bordered, int borderWidth, String windowName, int minSizeX,
                          int minSizeY, InetSocketAddress server) {
        this.legacy = legacy;
        this.bordered = bordered;
        this.borderWidth = borderWidth;
        this.windowName = windowName;
        this.minSizeX = minSizeX;
        this.minSizeY = minSizeY;
        this.server = server;
    }

    @Override
    public String toString() {
        return "PaintSettings [ " + "bordered=" + bordered + ", legacy=" + legacy +
                ", borderWidth=" + borderWidth +
                ", windowName=" + windowName +
                ", minSize=" + minSizeX + " " + minSizeY +
                ", remoteServer=" + server + " ]";
    }
}
