package fr.uge.poo.cmdline.ex6;

import java.net.InetSocketAddress;
import java.util.List;

public class PaintSettings {
    private final boolean legacy;
    private final boolean bordered;
    private final int borderWidth;
    private final int minSizeX;
    private final int minSizeY;
    private final String windowName;
    private final InetSocketAddress server;
    private final String help;
    private final String version;

    public static class PaintSettingsBuilder {
        private boolean legacy = false;
        private boolean bordered = false;
        private int borderWidth = 10;
        private int minSizeX = 500;
        private int minSizeY = 500;
        private String windowName;
        private InetSocketAddress server;
        private String help;
        private String version;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public void setBordered(boolean bordered) {
            this.bordered = bordered;
        }

        public void setBorderWidth(List<String> borderWidth) {
            if (borderWidth.size() == 1) {
                this.borderWidth = Integer.parseInt(borderWidth.get(0));
            } else {
                throw new IllegalArgumentException("-border-width option need 1 parameter");
            }
        }

        public void setWindowName(List<String> windowName) {
            if (windowName.size() == 1) {
                this.windowName = windowName.get(0);
            } else {
                throw new IllegalArgumentException("-window-name option need 1 parameter");
            }
        }

        public void setWindowSize(List<String> minSize) {
            if (minSize.size() == 2) {
                this.minSizeX = Integer.parseInt(minSize.get(0));
                this.minSizeY = Integer.parseInt(minSize.get(1));
            } else {
                throw new IllegalArgumentException("-min-size option need 2 parameters");
            }
        }

        public void setRemoteServer(List<String> remoteServer) {
            if (remoteServer.size() == 2) {
                this.server = new InetSocketAddress(remoteServer.get(0), Integer.parseInt(remoteServer.get(1)));
            } else {
                throw new IllegalArgumentException("-remote-server option need 2 parameters");
            }
        }

        public void setVersion() {
            this.version = "v4.0";
        }

        public PaintSettings build() {
            if (windowName == null) {
                throw new IllegalArgumentException("Window name cannot be null");
            }
            return new PaintSettings(this.legacy, this.bordered, this.borderWidth, this.windowName,
                    this.minSizeX, this.minSizeY, this.server, this.help, this.version);
        }
    }
    private PaintSettings(boolean legacy, boolean bordered, int borderWidth, String windowName, int minSizeX,
                          int minSizeY, InetSocketAddress server, String help, String version) {
        this.legacy = legacy;
        this.bordered = bordered;
        this.borderWidth = borderWidth;
        this.windowName = windowName;
        this.minSizeX = minSizeX;
        this.minSizeY = minSizeY;
        this.server = server;
        this.help = help;
        this.version = version;
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
