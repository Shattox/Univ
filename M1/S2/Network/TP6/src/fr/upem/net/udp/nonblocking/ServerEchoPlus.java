package fr.upem.net.udp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.Logger;

public class ServerEchoPlus {
    private static final Logger logger = Logger.getLogger(ServerEcho.class.getName());

    private final DatagramChannel dc;
    private final Selector selector;
    private final int BUFFER_SIZE = 1024;
    private final ByteBuffer receiveBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private final ByteBuffer sendBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private SocketAddress sender;
    private final int port;

    public ServerEchoPlus(int port) throws IOException {
        this.port = port;
        selector = Selector.open();
        dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(port));
        // TODO set dc in non-blocking mode and register it to the selector
        dc.configureBlocking(false);
        dc.register(selector, SelectionKey.OP_READ);
    }

    public void serve() throws IOException {
        logger.info("ServerEcho started on port " + port);
        while (!Thread.interrupted()) {
            try {
                selector.select(this::treatKey);
            } catch (UncheckedIOException e) {
                throw e.getCause();
            }
        }
    }

    private void treatKey(SelectionKey key) {
        try {
            if (key.isValid() && key.isWritable()) {
                doWrite(key);
            }
            if (key.isValid() && key.isReadable()) {
                doRead(key);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private void doRead(SelectionKey key) throws IOException {
        // TODO
        receiveBuffer.clear();
        sendBuffer.clear();
        sender = dc.receive(receiveBuffer);
        if (sender == null) {
            logger.info("The selector lies");
            return;
        }
        receiveBuffer.flip();
        while (receiveBuffer.hasRemaining()) {
            var octect = receiveBuffer.get();
            octect++;
            sendBuffer.put(octect);
        }
        sendBuffer.flip();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void doWrite(SelectionKey key) throws IOException {
        // TODO
        dc.send(sendBuffer, sender);
        if (sendBuffer.hasRemaining()) {
            logger.info("The selector lies");
            return;
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    public static void usage() {
        System.out.println("Usage : ServerEchoPlus port");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        new ServerEchoPlus(Integer.parseInt(args[0])).serve();
    }
}
