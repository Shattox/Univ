package fr.upem.net.udp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.Logger;

public class ServerEchoMultiPort {
    private static final Logger logger = Logger.getLogger(ServerEchoMultiPort.class.getName());

    private final Selector selector;
    private static final int BUFFER_SIZE = 1024;

    public ServerEchoMultiPort(int begin, int end) throws IOException {
        this.selector = Selector.open();

        for (int i = begin; i < end; i++) {
            var dc = DatagramChannel.open();
            dc.bind(new InetSocketAddress(i));
            dc.configureBlocking(false);
            dc.register(selector, SelectionKey.OP_READ, new Context());
        }
    }

    public static class Context {
        private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        private InetSocketAddress sender;
    }

    public void serve() throws IOException {
        logger.info("ServerEcho started on ports : 7000 .. 7100 ");
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
        var context = (Context) key.attachment();
        var dc = (DatagramChannel) key.channel();
        context.buffer.clear();
        context.sender = (InetSocketAddress) dc.receive(context.buffer);
        if (context.sender == null) {
            logger.info("The selector lies");
            return;
        }
        context.buffer.flip();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void doWrite(SelectionKey key) throws IOException {
        // TODO
        var context = (Context) key.attachment();
        var dc = (DatagramChannel) key.channel();
        dc.send(context.buffer, context.sender);
        if (context.buffer.hasRemaining()) {
            logger.info("The selector lies");
            return;
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    public static void usage() {
        System.out.println("Usage : ServerEchoMultiPort port(1) port(2) ... port(n)");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
            return;
        }
        new ServerEchoMultiPort(Integer.parseInt(args[0]), Integer.parseInt(args[1])).serve();
    }
}
