package fr.upem.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class ClientUpperCaseUDPFile {
    private final static Charset UTF8 = StandardCharsets.UTF_8;
    private final static int BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger(ClientUpperCaseUDPTimeout.class.getName());

    private static void usage() {
        System.out.println("Usage : ClientUpperCaseUDPFile in-filename out-filename timeout host port ");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 5) {
            usage();
            return;
        }

        var inFilename = args[0];
        var outFilename = args[1];
        var timeout = Integer.parseInt(args[2]);
        var server = new InetSocketAddress(args[3], Integer.parseInt(args[4]));

        // Read all lines of inFilename opened in UTF-8
        var lines = Files.readAllLines(Path.of(inFilename), UTF8);
        var upperCaseLines = new ArrayList<String>();

        var charset = StandardCharsets.UTF_8;
        var bQueue = new ArrayBlockingQueue<String>(BUFFER_SIZE);

        try (var dc = DatagramChannel.open()) {
            dc.bind(null);
            var listener = new Thread(() -> {
                try {
                    var buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    while (true) {
                        dc.receive(buffer);
                        buffer.flip();
                        bQueue.put(charset.decode(buffer).toString());
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
            });
            listener.start();
            for (var line : lines) {
                dc.send(charset.encode(line), server);
                var message = bQueue.poll(timeout, TimeUnit.MILLISECONDS);
                while (message == null) {
                    System.out.println("Le serveur n'a pas répondu, retry...");
                    dc.send(charset.encode(line), server);
                    message = bQueue.poll(timeout, TimeUnit.MILLISECONDS);
                }
                upperCaseLines.add(message);
            }
            listener.interrupt();
        }
        // Write upperCaseLines to outFilename in UTF-8
        Files.write(Path.of(outFilename), upperCaseLines, UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
    }
}