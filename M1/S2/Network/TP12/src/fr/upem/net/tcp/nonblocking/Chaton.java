package fr.upem.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chaton {

    /*static private class ServerEchoWithConsole {
        private final Thread console;

        public ServerEchoWithConsole() {
            this.console = new Thread(this::consoleRun);
        }

        public void sendCommand(String line) throws InterruptedException {
            Objects.requireNonNull(line);
            bQueue.put(line.toUpperCase());
        }

        public void processCommand() {
            var line = bQueue.poll();

            switch (Objects.requireNonNull(line)) {
                case "INFO" -> {
                }
                case "SHUTDOWN" -> {
                }
                case "SHUTDOWNNOW" -> {
                }
                default -> System.out.println("Unknown command please retry !");
            }
        }

        public void consoleRun() {
            try {
                try (var scanner = new Scanner(System.in)) {
                    while (scanner.hasNextLine()) {
                        var line = scanner.nextLine();
                        sendCommand(line);
                    }
                }
                logger.info("Console thread stopping");
            } catch (InterruptedException e) {
                logger.info("Console thread has been interrupted");
            }
        }
    }*/

    static private class Context {
        private final SelectionKey key;
        private final SocketChannel sc;
        private final ByteBuffer bufferIn = ByteBuffer.allocate(BUFFER_SIZE);
        private final ByteBuffer bufferOut = ByteBuffer.allocate(BUFFER_SIZE);
        private final ArrayDeque<MessageReader.Message> queue = new ArrayDeque<>();
        private final MessageReader messageReader = new MessageReader();
        private final Charset charset = StandardCharsets.UTF_8;
        private final Chaton server; // we could also have Context as an instance class, which would naturally
        // give access to ServerChatInt.this
        private boolean closed = false;

        private Context(Chaton server, SelectionKey key) {
            this.key = key;
            this.sc = (SocketChannel) key.channel();
            this.server = server;
        }

        /**
         * Process the content of bufferIn
         *
         * The convention is that bufferIn is in write-mode before the call to process and
         * after the call
         *
         */
        private void processIn() {
            var status = messageReader.process(bufferIn);
            if (status != Reader.ProcessStatus.DONE) {
                return;
            }
            var msg = messageReader.get();
            server.broadcast(msg);
            messageReader.reset();
        }

        /**
         * Add a message to the message queue, tries to fill bufferOut and updateInterestOps
         *
         * @param msg message
         */
        public void queueMessage(MessageReader.Message msg) {
            queue.add(msg);
            processOut();
            updateInterestOps();
        }

        /**
         * Try to fill bufferOut from the message queue
         *
         */
        private void processOut() {
            var message = queue.peek();
            if (message == null) {
                return;
            }
            var login = charset.encode(message.login());
            var text = charset.encode(message.text());
            if (bufferOut.remaining() < (login.remaining() + text.remaining() + (2 * Integer.BYTES))) {
                return;
            }
            bufferOut.putInt(login.remaining())
                    .put(login)
                    .putInt(text.remaining())
                    .put(text);
            queue.remove();
        }

        /**
         * Update the interestOps of the key looking only at values of the boolean
         * closed and of both ByteBuffers.
         *
         * The convention is that both buffers are in write-mode before the call to
         * updateInterestOps and after the call. Also, it is assumed that process has
         * been called just before updateInterestOps.
         */

        private void updateInterestOps() {
            var interestOps = 0;

            if (!closed && bufferIn.hasRemaining()) {
                interestOps |= SelectionKey.OP_READ;
            }
            if (bufferOut.position() != 0) {
                interestOps |= SelectionKey.OP_WRITE;
            }
            if (interestOps == 0) {
                silentlyClose();
                return;
            }
            key.interestOps(interestOps);
        }

        private void silentlyClose() {
            try {
                sc.close();
            } catch (IOException e) {
                // ignore exception
            }
        }

        /**
         * Performs the read action on sc
         *
         * The convention is that both buffers are in write-mode before the call to
         * doRead and after the call
         *
         * @throws IOException IOException
         */
        private void doRead() throws IOException {
            if (sc.read(bufferIn) == -1) {
                closed = true;
                key.cancel();
            }
            processIn();
        }

        /**
         * Performs the write action on sc
         *
         * The convention is that both buffers are in write-mode before the call to
         * doWrite and after the call
         *
         * @throws IOException IOException
         */

        private void doWrite() throws IOException {
            bufferOut.flip();
            sc.write(bufferOut);
            bufferOut.compact();
            processOut();
            updateInterestOps();
        }

    }

    private static final int BUFFER_SIZE = 1_024;
    private static final Logger logger = Logger.getLogger(ServerChatInt.class.getName());
    private static final ArrayBlockingQueue<String> bQueue = new ArrayBlockingQueue<>(1);

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    /*private final ServerEchoWithConsole serverEchoWithConsole;*/

    public Chaton(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();
        System.out.println(serverSocketChannel.socket().getInetAddress().getHostName());
        /*this.serverEchoWithConsole = new ServerEchoWithConsole();*/
    }

    public void launch() throws IOException {
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.interrupted()) {
            Helpers.printKeys(selector); // for debug
            System.out.println("Starting select");
            try {
                selector.select(this::treatKey);
                /*serverEchoWithConsole.processCommand();*/
            } catch (UncheckedIOException tunneled) {
                throw tunneled.getCause();
            }
            System.out.println("Select finished");
        }
    }

    private void treatKey(SelectionKey key) {
        Helpers.printSelectedKey(key); // for debug
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
        client.attach(new Context(this, client));
    }

    private void silentlyClose(SelectionKey key) {
        Channel sc = key.channel();
        try {
            sc.close();
        } catch (IOException e) {
            // ignore exception
        }
    }

    /**
     * Add a message to all connected clients queue
     *
     * @param msg message
     */
    private void broadcast(MessageReader.Message msg) {
        // TODO
        for (var key: selector.keys()) {
            var context = (Context) key.attachment();
            if (context != null) {
                context.queueMessage(msg);
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        new Chaton(Integer.parseInt(args[0])).launch();
    }

    private static void usage() {
        System.out.println("Usage : ServerSumBetter port");
    }
}
