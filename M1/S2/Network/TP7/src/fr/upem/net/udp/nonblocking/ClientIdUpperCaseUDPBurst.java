package fr.upem.net.udp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class ClientIdUpperCaseUDPBurst {
    private static final Logger logger = Logger.getLogger(ClientIdUpperCaseUDPOneByOne.class.getName());
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 1024;

    private enum State {
        SENDING, RECEIVING, FINISHED
    }

    private final List<String> lines;
    private final String[] upperCaseLines;
    private final long timeout;
    private final InetSocketAddress serverAddress;
    private final DatagramChannel dc;
    private final Selector selector;
    private final SelectionKey uniqueKey;
    private final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    private int idLine = 0;
    private long lastSend = 0L;
    private final AnswersLog answersLog;
    private List<Integer> missingAnswers;
    // TODO add new fields

    private State state;

    private static void usage() {
        System.out.println("Usage : ClientIdUpperCaseUDPOneByOne in-filename out-filename timeout host port ");
    }

    private ClientIdUpperCaseUDPBurst(List<String> lines, long timeout, InetSocketAddress serverAddress,
                                      DatagramChannel dc, Selector selector, SelectionKey uniqueKey) {
        this.lines = lines;
        this.timeout = timeout;
        this.serverAddress = serverAddress;
        this.dc = dc;
        this.selector = selector;
        this.uniqueKey = uniqueKey;
        this.state = State.SENDING;
        this.upperCaseLines = new String[lines.size()];
        this.answersLog = new AnswersLog(lines.size());
        this.missingAnswers = answersLog.missingAnswer();
    }

    public static ClientIdUpperCaseUDPBurst create(String inFilename, long timeout,
                                                   InetSocketAddress serverAddress) throws IOException {
        Objects.requireNonNull(inFilename);
        Objects.requireNonNull(serverAddress);
        Objects.checkIndex(timeout, Long.MAX_VALUE);

        // Read all lines of inFilename opened in UTF-8
        var lines = Files.readAllLines(Path.of(inFilename), UTF8);
        var dc = DatagramChannel.open();
        dc.configureBlocking(false);
        dc.bind(null);
        var selector = Selector.open();
        var uniqueKey = dc.register(selector, SelectionKey.OP_WRITE);
        return new ClientIdUpperCaseUDPBurst(lines, timeout, serverAddress, dc, selector, uniqueKey);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            usage();
            return;
        }

        var inFilename = args[0];
        var outFilename = args[1];
        var timeout = Long.parseLong(args[2]);
        var server = new InetSocketAddress(args[3], Integer.parseInt(args[4]));

        // Create client with the parameters and launch it
        var upperCaseLines = create(inFilename, timeout, server).launch();

        Files.write(Path.of(outFilename), upperCaseLines, UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
    }

    private List<String> launch() throws IOException {
        try {
            while (!isFinished()) {
                try {
                    selector.select(this::treatKey, updateInterestOps());
                } catch (UncheckedIOException tunneled) {
                    throw tunneled.getCause();
                }
            }
            return Arrays.asList(upperCaseLines);
        } finally {
            dc.close();
        }
    }

    private void treatKey(SelectionKey key) {
        try {
            if (key.isValid() && key.isWritable()) {
                doWrite();
            }
            if (key.isValid() && key.isReadable()) {
                doRead();
            }
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     * Updates the interestOps on key based on state of the context
     *
     * @return the timeout for the next select (0 means no timeout)
     */

    private long updateInterestOps() {
        // TODO
        var time = lastSend + timeout - System.currentTimeMillis();
        if (state == State.RECEIVING) {
            idLine = 0;
            missingAnswers = answersLog.missingAnswer();
            if (time <= 0) {
                state = State.SENDING;
            } else {
                uniqueKey.interestOps(SelectionKey.OP_READ);
                return time;
            }
        }
        if (state == State.SENDING) {
            uniqueKey.interestOps(SelectionKey.OP_WRITE);
            return 0;
        }
        return time;
    }

    private boolean isFinished() {
        return state == State.FINISHED;
    }

    /**
     * Performs the receptions of packets
     *
     * @throws IOException IOException
     */

    private void doRead() throws IOException {
        // TODO
        buffer.clear();
        var sender = (InetSocketAddress) dc.receive(buffer);
        if (sender == null) {
            logger.info("Any packets received");
            return;
        }
        buffer.flip();
        if (buffer.remaining() < Long.BYTES) {
            return;
        }
        var receivedId = (int) buffer.getLong();
        if (answersLog.bitSet.get(receivedId)) {
            return;
        }
        state = State.SENDING;
        upperCaseLines[receivedId] = UTF8.decode(buffer).toString();
        answersLog.update(receivedId);
        if (answersLog.isFinished()) {
            state = State.FINISHED;
        }
    }

    /**
     * Tries to send the packets
     *
     * @throws IOException IOException
     */

    private void doWrite() throws IOException {
        // TODO
        var id = missingAnswers.get(idLine);
        buffer.clear();
        buffer.putLong(id).put(UTF8.encode(lines.get(id))).flip();
        dc.send(buffer, serverAddress);
        if (buffer.hasRemaining()) {
            logger.info("Any packets sent");
            return;
        }
        idLine++;
        lastSend = System.currentTimeMillis();
        if (idLine == answersLog.missingAnswer().size()) {
            state = State.RECEIVING;
        }
    }

    public static class AnswersLog {
        private final BitSet bitSet;
        private int counter;
        private final int nbLines;

        public AnswersLog(int nbLines) {
            this.nbLines = nbLines;
            this.bitSet = new BitSet(nbLines);
            this.counter = 0;
        }

        public void update(int index) {
            bitSet.set(index);
            counter++;
        }

        public List<Integer> missingAnswer() {
            var missingAnswer = new ArrayList<Integer>();
            for (int i = 0; i < nbLines; i++) {
                if (!bitSet.get(i)) {
                    missingAnswer.add(i);
                }
            }
            return missingAnswer;
        }

        public boolean isFinished() {
            return counter == nbLines;
        }
    }
}
