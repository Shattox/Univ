package fr.upem.net.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUpperCaseUDPRetry {
    public static final int BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger(ClientUpperCaseUDPTimeout.class.getName());

    private static void usage() {
        System.out.println("Usage : fr.upem.net.udp.NetcatUDP host port charset");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 3) {
            usage();
            return;
        }

        var server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        var cs = Charset.forName(args[2]);
        var bQueue = new ArrayBlockingQueue<String>(BUFFER_SIZE);

        try (var dc = DatagramChannel.open()) {
            dc.bind(null);
            var listener = new Thread(() -> {
                try {
                    var buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    while (true) {
                        var sender = (InetSocketAddress) dc.receive(buffer);
                        buffer.flip();
                        System.out.println("Received " + buffer.remaining() + " bytes from " + sender);
                        bQueue.put(cs.decode(buffer).toString());
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
            try (var scanner = new Scanner(System.in)) {
                while (scanner.hasNextLine()) {
                    var line = scanner.nextLine();
                    dc.send(cs.encode(line), server);
                    var message = bQueue.poll(1, TimeUnit.SECONDS);
                    while (message == null) {
                        System.out.println("Le serveur n'a pas répondu, retry...");
                        dc.send(cs.encode(line), server);
                        message = bQueue.poll(1, TimeUnit.SECONDS);
                    }
                    System.out.println("String : " + message);

                }
            }
            listener.interrupt();
        }
    }
}
