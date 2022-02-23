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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientIdUpperCaseUDPBurst {
    private static final Logger logger = Logger.getLogger(ClientIdUpperCaseUDPBurst.class.getName());
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 1024;
    private final List<String> lines;
    private final int nbLines;
    private final String[] upperCaseLines; //
    private final int timeout;
    private final String outFilename;
    private final InetSocketAddress serverAddress;
    private final DatagramChannel dc;
    private final AnswersLog answersLog;         // Thread-safe structure keeping track of missing responses

    public static void usage() {
        System.out.println("Usage : ClientIdUpperCaseUDPBurst in-filename out-filename timeout host port ");
    }

    public ClientIdUpperCaseUDPBurst(List<String> lines, int timeout, InetSocketAddress serverAddress, String outFilename) throws IOException {
        this.lines = lines;
        this.nbLines = lines.size();
        this.timeout = timeout;
        this.outFilename = outFilename;
        this.serverAddress = serverAddress;
        this.dc = DatagramChannel.open();
        dc.bind(null);
        this.upperCaseLines = new String[nbLines];
        this.answersLog = new AnswersLog(nbLines); // TODO
    }

    private void senderThreadRun() {
        // TODO : body of the sender thread
        try {
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            long lastSend = 0L;
            while (!Thread.interrupted()) {
                var currentTime = System.currentTimeMillis();
                if (currentTime - lastSend > timeout) {

                    for (var i = 0; i < nbLines; i++) {
                        if (!answersLog.getIndex(i)) {
                            buffer.putLong(i).put(UTF8.encode(lines.get(i)));
                            buffer.flip();
                            dc.send(buffer, serverAddress);
                            buffer.clear();
                        }
                        lastSend = currentTime;
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException" + e);
        }
    }

    public void launch() throws IOException {
        try {
            Thread senderThread = new Thread(this::senderThreadRun);
            senderThread.start();

            // TODO : body of the receiver thread
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (!answersLog.allTrue()) {
                try {
                    logger.info("current upperCaseList -> " + Arrays.stream(upperCaseLines).toList());
                    buffer.clear();
                    dc.receive(buffer);
                    buffer.flip();
                    if (buffer.remaining() < Integer.BYTES) {
                        continue;
                    }
                    var id = (int) buffer.getLong();
                    if (id >= 0 && !answersLog.getIndex(id)) {
                        upperCaseLines[id] = UTF8.decode(buffer).toString();
                        answersLog.setIndex(id);
                    }
                } catch (AsynchronousCloseException e) {
                    logger.info("AsynchronousCloseException " + e);
                } catch (ClosedChannelException e) {
                    logger.info("ClosedChannelException " + e);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "IOException", e);
                }
            }
            senderThread.interrupt();
            Files.write(Paths.get(outFilename), Arrays.asList(upperCaseLines), UTF8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } finally {
            dc.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            usage();
            return;
        }

        String inFilename = args[0];
        String outFilename = args[1];
        int timeout = Integer.parseInt(args[2]);
        String host = args[3];
        int port = Integer.parseInt(args[4]);
        InetSocketAddress serverAddress = new InetSocketAddress(host, port);

        //Read all lines of inFilename opened in UTF-8
        List<String> lines = Files.readAllLines(Paths.get(inFilename), UTF8);
        //Create client with the parameters and launch it
        ClientIdUpperCaseUDPBurst client = new ClientIdUpperCaseUDPBurst(lines, timeout, serverAddress, outFilename);
        client.launch();

    }

    private static class AnswersLog {
        // TODO Thread-safe class handling the information about missing lines
        private final BitSet bitSet;
        private final int nbLines;

        public AnswersLog(int nbLines) {
            this.bitSet = new BitSet(nbLines);
            this.nbLines = nbLines;
        }

        public boolean getIndex(int index) {
            synchronized (bitSet) {
                return bitSet.get(index);
            }
        }

        public void setIndex(int index) {
            synchronized (bitSet) {
                bitSet.set(index);
            }
        }

        public boolean allTrue() {
            synchronized (bitSet) {
                return bitSet.cardinality() == nbLines;
            }
        }
    }
}


