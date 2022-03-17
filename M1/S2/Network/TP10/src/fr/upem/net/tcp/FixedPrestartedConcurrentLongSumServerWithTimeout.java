package fr.upem.net.tcp;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FixedPrestartedConcurrentLongSumServerWithTimeout {
    private static final Logger logger = Logger.getLogger(OnDemandConcurrentLongSumServer.class.getName());
    private static final int MAX_CLIENT = 2;
    private static final int TIMEOUT = 5000;
    private final ServerSocketChannel serverSocketChannel;
    private static final ThreadData[] listThreadData = new ThreadData[MAX_CLIENT];
    private static final Thread[] threads = new Thread[MAX_CLIENT];

    public FixedPrestartedConcurrentLongSumServerWithTimeout(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(this.getClass().getName() + " starts on port " + port);
    }

    /**
     * Iterative server main loop
     */
    public void launch() {
        logger.info("Server started");
        for (int i = 0; i < MAX_CLIENT; i++) {
            int finalI = i;
            var thread = new Thread(() -> {
                try {
                    var threadData = new ThreadData();
                    listThreadData[finalI] = threadData;
                    while (!Thread.interrupted()) {
                        SocketChannel client = serverSocketChannel.accept();
                        threadData.setSocketClient(client);
                        try {
                            logger.info("Connection accepted from " + client.getRemoteAddress());
                            serve(threadData, client);
                        } catch (IOException ioe) {
                            logger.log(Level.SEVERE, "Connection terminated with client by IOException", ioe.getCause());
                        } finally {
                            silentlyClose(client);
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Can't accept the client", e.getCause());
                }
            });
            threads[i] = thread;
        }

        for (var thread: threads) {
            thread.start();
        }

        new Thread(() -> {
            while (!Thread.interrupted()) {
                for (var threadData : listThreadData) {
                    threadData.closeIfInactive(TIMEOUT);
                }
                try {
                    Thread.sleep(TIMEOUT);
                } catch (InterruptedException e) {
                    logger.info("InterruptedException");
                }
            }
        }).start();
    }

    /**
     * Treat the connection sc applying the protocol. All IOException are thrown
     *
     * @param data data
     * @throws IOException IOException
     */
    private void serve(ThreadData data, SocketChannel client) throws IOException {
        // TODO
        while (true) {
            var bufferSize = ByteBuffer.allocate(Integer.BYTES);
            if (!readFully(data, client, bufferSize)) {
                return;
            }
            bufferSize.flip();
            var size = bufferSize.getInt();
            var buffer = ByteBuffer.allocate(size * Long.BYTES);
            if (!readFully(data, client, buffer)) {
                return;
            }
            buffer.flip();
            var sum = 0L;
            for (int i = 0; i < size; i++) {
                sum += buffer.getLong();
            }
            buffer.clear();
            buffer.putLong(sum).flip();
            data.tick();
            client.write(buffer);
        }
    }

    /**
     * Close a SocketChannel while ignoring IOException
     *
     * @param sc sc
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

    static boolean readFully(ThreadData data, SocketChannel client, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            data.tick();
            if (client.read(buffer) == -1) {
                logger.info("Input stream closed");
                return false;
            }
        }
        return true;
    }

    public void onCommand() {
        try (var sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                var line = sc.nextLine().toUpperCase();
                switch (line) {
                    case "INFO" -> {
                        var nbClients = Arrays.stream(liscstThreadData).filter(ThreadData::isConnected).count();
                        System.out.println("Il y a " + nbClients + " connectés");
                    }
                    case "SHUTDOWN" -> {
                        logger.info("N'accepte plus de nouveau client");
                        serverSocketChannel.close();
                    }
                    case "SHUTDOWNNOW" -> {
                        logger.info("Arrêt total du serveur");
                        for (var thread : threads) {
                            thread.interrupt();
                        }
                        for (var threadData : listThreadData) {
                            threadData.close();
                        }
                        serverSocketChannel.close();
                    }
                    default -> System.out.println("Utilisez la commande INFO, SHUTDOWN ou SHUTDOWNOW");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "oops", e);
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        var server = new FixedPrestartedConcurrentLongSumServerWithTimeout(Integer.parseInt(args[0]));
        server.launch();
        server.onCommand();
    }

    private static class ThreadData {
        private final Object object = new Object();
        private SocketChannel sc;
        private long lastActivity;

        public void setSocketClient(SocketChannel client) {
            synchronized (object) {
                sc = client;
                tick();
            }
        }

        public void tick() {
            synchronized (object) {
                lastActivity = System.currentTimeMillis();
            }
        }

        public void closeIfInactive(int timeout) {
            synchronized (object) {
                var currentTime = System.currentTimeMillis();
                if (currentTime - lastActivity > timeout) {
                    close();
                }
            }
        }

        public void close() {
            synchronized (object) {
                if (sc != null) {
                    try {
                        sc.close();
                        sc = null;
                    } catch (IOException e) {
                        // Do nothing
                    }
                }
            }
        }

        public static boolean isConnected() {
            synchronized (object) {
                return sc != null;
            }
        }
    }
}
