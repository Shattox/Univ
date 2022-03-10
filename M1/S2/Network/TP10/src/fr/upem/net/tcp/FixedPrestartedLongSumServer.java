package fr.upem.net.tcp;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FixedPrestartedLongSumServer {
    private static final Logger logger = Logger.getLogger(OnDemandConcurrentLongSumServer.class.getName());
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_CLIENT = 2;
    private final ServerSocketChannel serverSocketChannel;

    public FixedPrestartedLongSumServer(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(this.getClass().getName() + " starts on port " + port);
    }

    /**
     * Iterative server main loop
     *
     * @throws IOException
     */

    public void launch() {
        logger.info("Server started");
        for (int i = 0; i < MAX_CLIENT; i++) {
            var thread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        SocketChannel client = serverSocketChannel.accept();
                        try {
                            logger.info("Connection accepted from " + client.getRemoteAddress());
                            serve(client);
                        } catch (IOException ioe) {
                            logger.log(Level.SEVERE, "Connection terminated with client by IOException", ioe.getCause());
                        } finally {
                            silentlyClose(client);
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Enable to accept the client", e.getCause());
                }
            });
            thread.start();
        }
    }

    /**
     * Treat the connection sc applying the protocol. All IOException are thrown
     *
     * @param sc
     * @throws IOException
     */
    private void serve(SocketChannel sc) throws IOException {
        // TODO
        while (true) {
            var bufferSize = ByteBuffer.allocate(Integer.BYTES);
            if (!readFully(sc, bufferSize)) {
                return;
            }
            bufferSize.flip();
            var size = bufferSize.getInt();
            var buffer = ByteBuffer.allocate(size * Long.BYTES);
            if (!readFully(sc, buffer)) {
                return;
            }
            buffer.flip();
            var sum = 0L;
            for (int i = 0; i < size; i++) {
                sum += buffer.getLong();
            }
            buffer.clear();
            buffer.putLong(sum).flip();
            sc.write(buffer);
        }
    }

    /**
     * Close a SocketChannel while ignoring IOExecption
     *
     * @param sc
     */

    private void silentlyClose(Closeable sc) {
        if (sc != null) {
            try {
                sc.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            if (sc.read(buffer) == -1) {
                logger.info("Input stream closed");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        var server = new FixedPrestartedLongSumServer(Integer.parseInt(args[0]));
        server.launch();
    }
}
