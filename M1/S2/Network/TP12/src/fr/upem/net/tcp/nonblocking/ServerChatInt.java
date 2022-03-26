package fr.upem.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerChatInt {
    static private class Context {
        private final SelectionKey key;
        private final SocketChannel sc;
        private final ByteBuffer bufferIn = ByteBuffer.allocate(BUFFER_SIZE);
        private final ByteBuffer bufferOut = ByteBuffer.allocate(BUFFER_SIZE);
        private final ArrayDeque<Integer> queue = new ArrayDeque<>();
        private final ServerChatInt server; // we could also have Context as an instance class, which would naturally
        // give access to ServerChatInt.this
        private boolean closed = false;

        private Context(ServerChatInt server, SelectionKey key) {
            this.key = key;
            this.sc = (SocketChannel) key.channel();
            this.server = server;
        }

        /**
         * Process the content of bufferIn
         * <p>
         * The convention is that bufferIn is in write-mode before the call to process and
         * after the call
         */
        private void processIn() {
            // TODO
            bufferIn.flip();
            while (bufferIn.remaining() >= Integer.BYTES) {
                server.broadcast(bufferIn.getInt());
            }
            bufferIn.compact();
        }

        /**
         * Add a message to the message queue, tries to fill bufferOut and updateInterestOps
         *
         * @param msg message
         */
        public void queueMessage(Integer msg) {
            // TODO
            queue.add(msg);
            processOut();
            updateInterestOps();
        }

        /**
         * Try to fill bufferOut from the message queue
         */
        private void processOut() {
            // TODO
            if (bufferOut.remaining() < Integer.BYTES) {
                return;
            }
            var message = queue.poll();
            if (message == null) {
                return;
            }
            bufferOut.putInt(message);
        }

        /**
         * Update the interestOps of the key looking only at values of the boolean
         * closed and of both ByteBuffers.
         * <p>
         * The convention is that both buffers are in write-mode before the call to
         * updateInterestOps and after the call. Also it is assumed that process has
         * been be called just before updateInterestOps.
         */

        private void updateInterestOps() {
            // TODO
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
         * <p>
         * The convention is that both buffers are in write-mode before the call to
         * doRead and after the call
         *
         * @throws IOException IOException
         */
        private void doRead() throws IOException {
            // TODO
            if (sc.read(bufferIn) == -1) {
                closed = true;
            }
            processIn();
        }

        /**
         * Performs the write action on sc
         * <p>
         * The convention is that both buffers are in write-mode before the call to
         * doWrite and after the call
         *
         * @throws IOException IOException
         */

        private void doWrite() throws IOException {
            // TODO
            bufferOut.flip();
            sc.write(bufferOut);
            bufferOut.compact();
            processOut();
            updateInterestOps();
        }

    }

    private static final int BUFFER_SIZE = 1_024;
    private static final Logger logger = Logger.getLogger(ServerChatInt.class.getName());

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public ServerChatInt(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();
    }

    public void launch() throws IOException {
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.interrupted()) {
            Helpers.printKeys(selector); // for debug
            System.out.println("Starting select");
            try {
                selector.select(this::treatKey);
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
        client.attach(new ServerChatInt.Context(this, client));
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
    private void broadcast(Integer msg) {
        // TODO
        for (var key : selector.keys()) {
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
        new ServerChatInt(Integer.parseInt(args[0])).launch();
    }

    private static void usage() {
        System.out.println("Usage : ServerSumBetter port");
    }
}