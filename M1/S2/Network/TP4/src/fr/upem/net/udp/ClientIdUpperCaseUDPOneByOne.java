package fr.upem.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class ClientIdUpperCaseUDPOneByOne {

    private static final Logger logger = Logger.getLogger(ClientIdUpperCaseUDPOneByOne.class.getName());
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 1024;

    private record Response(long id, String message) {
    }

    private final String inFilename;
    private final String outFilename;
    private final long timeout;
    private final InetSocketAddress server;
    private final DatagramChannel dc;
    private final SynchronousQueue<Response> queue = new SynchronousQueue<>();

    public static void usage() {
        System.out.println("Usage : ClientIdUpperCaseUDPOneByOne in-filename out-filename timeout host port ");
    }

    public ClientIdUpperCaseUDPOneByOne(String inFilename, String outFilename, long timeout, InetSocketAddress server)
            throws IOException {
        this.inFilename = Objects.requireNonNull(inFilename);
        this.outFilename = Objects.requireNonNull(outFilename);
        this.timeout = timeout;
        this.server = server;
        this.dc = DatagramChannel.open();
        dc.bind(null);
    }

    private void listenerThreadRun() {
        // TODO Listener thread run
        try {
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (true) {
                var sender = (InetSocketAddress) dc.receive(buffer);
                buffer.flip();
                System.out.println("Received " + buffer.remaining() + " bytes from " + sender);
                queue.put(new Response(buffer.getLong(), UTF8.decode(buffer).toString()));
                buffer.clear();
            }
        } catch (AsynchronousCloseException e) {
            logger.info("AsynchronousCloseException " + e);
        } catch (ClosedChannelException e) {
            logger.info("ClosedChannelException " + e);
        } catch (InterruptedException e) {
            logger.info("InterruptedException " + e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException", e);
        }
    }

    public void launch() throws IOException, InterruptedException {
        try {
            var listenerThread = new Thread(this::listenerThreadRun);
            listenerThread.start();

            // Read all lines of inFilename opened in UTF-8
            var lines = Files.readAllLines(Path.of(inFilename), UTF8);
            var upperCaseLines = new ArrayList<String>();

            // TODO
            var id = 0L;
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            for (var line : lines) {
                buffer.putLong(id).put(UTF8.encode(line));
                buffer.flip();
                long lastSend = 0L;
                while (true) {
                    var currentTime = System.currentTimeMillis();
                    if (currentTime - lastSend > timeout) {
                        try {
                            dc.send(buffer, server);
                            lastSend = currentTime;
                            buffer.flip();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "IOException", e);
                        }
                    }
                    var remainingTime = (lastSend + timeout) - currentTime;
                    var message = queue.poll(remainingTime, TimeUnit.MILLISECONDS);
                    if (message == null) {
                        lastSend = 0;
                        continue;
                    }
                    if (message.id == id) {
                        System.out.println(message.message);
                        upperCaseLines.add(message.message);
                        break;
                    }
                }
                id++;
            }
            listenerThread.interrupt();
            Files.write(Paths.get(outFilename), upperCaseLines, UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
        } finally {
            dc.close();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 5) {
            usage();
            return;
        }

        var inFilename = args[0];
        var outFilename = args[1];
        var timeout = Long.parseLong(args[2]);
        var server = new InetSocketAddress(args[3], Integer.parseInt(args[4]));

        // Create client with the parameters and launch it
        new ClientIdUpperCaseUDPOneByOne(inFilename, outFilename, timeout, server).launch();
    }
}
