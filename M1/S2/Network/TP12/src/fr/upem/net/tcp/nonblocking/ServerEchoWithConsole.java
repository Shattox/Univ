package fr.upem.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerEchoWithConsole {
    static private class Context {
        private final SelectionKey key;
        private final SocketChannel sc;
        private final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        private boolean closed = false;

        private Context(SelectionKey key) {
            this.key = key;
            this.sc = (SocketChannel) key.channel();
        }

        /**
         * Update the interestOps of the key looking only at values of the boolean
         * closed and the ByteBuffer buffer.
         * <p>
         * The convention is that buff is in write-mode.
         */
        private void updateInterestOps() {
            // TODO
            if (!buffer.hasRemaining() && closed) {
                silentlyClose();
                return;
            }
            if (buffer.hasRemaining()) {
                key.interestOps(SelectionKey.OP_READ);
                return;
            }
            key.interestOps(SelectionKey.OP_WRITE);
        }

        /**
         * Performs the read action on sc
         * <p>
         * The convention is that buffer is in write-mode before calling doRead and is in
         * write-mode after calling doRead
         *
         * @throws IOException
         */
        private void doRead() throws IOException {
            // TODO
            if (sc.read(buffer) == -1) {
                closed = true;
                return;
            }
            updateInterestOps();
        }

        /**
         * Performs the write action on sc
         * <p>
         * The convention is that buffer is in write-mode before calling doWrite and is in
         * write-mode after calling doWrite
         *
         * @throws IOException
         */
        private void doWrite() throws IOException {
            // TODO
            buffer.flip();
            sc.write(buffer);
            buffer.compact();
            updateInterestOps();
        }

        private void silentlyClose() {
            try {
                sc.close();
            } catch (IOException e) {
                // ignore exception
            }
        }
    }

    private static final int BUFFER_SIZE = 1_024;
    private static final Logger logger = Logger.getLogger(ServerEchoWithConsole.class.getName());

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private final ArrayBlockingQueue<String> bQueue = new ArrayBlockingQueue<>(1024);
    private final Thread console;

    public ServerEchoWithConsole(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();
        this.console = new Thread(this::consoleRun);
    }

    private void consoleRun() {
        try {
            try (var scanner = new Scanner(System.in)) {
                while (scanner.hasNextLine()) {
                    var command = scanner.nextLine();
                    sendCommand(command);
                }
            }
            logger.info("Console thread stopping");
        } catch (InterruptedException e) {
            logger.info("Console thread has been interrupted");
        }
    }

    public void launch() throws IOException {
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        console.start();

        while (!Thread.interrupted()) {
            try {
                selector.select(this::treatKey);
                processCommands();
            } catch (UncheckedIOException tunneled) {
                throw tunneled.getCause();
            }
        }
    }

    private void treatKey(SelectionKey key) {
        try {
            if (key.isValid() && key.isAcceptable()) {
                doAccept(key);
            }
        } catch (IOException ioe) {
            // lambda call in select requires to tunnel IOException
            throw new UncheckedIOException(ioe);
        }
        try {
            if (key.isValid() && key.isWritable()) {
                ((Context) key.attachment()).doWrite();
            }
            if (key.isValid() && key.isReadable()) {
                ((Context) key.attachment()).doRead();
            }
        } catch (IOException e) {
            logger.log(Level.INFO, "Connection closed with client due to IOException", e);
            silentlyClose(key);
        }
    }

    private void doAccept(SelectionKey key) throws IOException {
        // TODO
        var sc = serverSocketChannel.accept();
        if (sc == null) {
            return;
        }
        sc.configureBlocking(false);
        var client = sc.register(selector, SelectionKey.OP_READ);
        client.attach(new Context(client));
    }

    private void silentlyClose(SelectionKey key) {
        var sc = (Channel) key.channel();
        try {
            sc.close();
        } catch (IOException e) {
            // ignore exception
        }
    }

    private void sendCommand(String command) throws InterruptedException {
        // TODO
        synchronized (bQueue) {
            Objects.requireNonNull(command);
            bQueue.put(command);
            selector.wakeup();
        }
    }

    private void processCommands() throws IOException {
        // TODO
        synchronized (bQueue) {
            var command = bQueue.poll();
            if (command == null) {
                return;
            }
            switch (command) {
                case "INFO" -> System.out.println((selector.keys().size() - 1) + " client connected");
                case "SHUTDOWN" -> {
                    serverSocketChannel.close();
                    System.out.println("This server cannot accept new client");
                }
                case "SHUTDOWNNOW" -> {
                    for (var key : selector.keys()) {
                        System.out.println("here");
                        silentlyClose(key);
                    }
                    serverSocketChannel.close();
                    System.out.println("Server stopped");
                }
                default -> System.out.println("Commands : INFO || SHUTDOWN || SHUTDOWNNOW");
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        new ServerEchoWithConsole(Integer.parseInt(args[0])).launch();
    }

    private static void usage() {
        System.out.println("Usage : ServerEcho port");
    }
}
