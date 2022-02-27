package fr.uge.net.udp;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.BitSet;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerLongSumUDP {
    private static record Client(InetSocketAddress address, long sessionID) {
    }

    private static class Data {
        private final BitSet bitSet;
        private final int totalOper;
        private int counter;
        private long sum;

        public Data(int totalOper) {
            this.bitSet = new BitSet(totalOper);
            this.totalOper = totalOper;
            this.counter = 0;
            this.sum = 0;
        }

        public void update(int index, long opValue) {
            if (!bitSet.get(index)) {
                bitSet.set(index);
                counter++;
                sum += opValue;
            }
        }

        public boolean isFinished() {
            return counter == totalOper;
        }

    }

    private final static Logger logger = Logger.getLogger(ServerLongSumUDP.class.getName());
    private final static int BUFFER_SIZE = 1024;

    private final HashMap<Client, Data> container = new HashMap<>();
    private final DatagramChannel dc;

    public ServerLongSumUDP(int port) throws IOException {
        this.dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(port));
        logger.info("ServerLongSumUDP serve on port " + port);
    }

    private static void usage() {
        System.out.println("java ServerLongSumUDP port");
    }

    private void serve() throws IOException {
        try {
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (!Thread.interrupted()) {
                buffer.clear();
                var sender = (InetSocketAddress) dc.receive(buffer);
                buffer.flip();

                if ((buffer.remaining() < (Byte.BYTES + (Long.BYTES * 4)))) {
                    continue;
                }
                var type = buffer.get();
                var sessionID = buffer.getLong();
                var idPosOper = (int) buffer.getLong();
                var totalOper = (int) buffer.getLong();
                var opValue = buffer.getLong();
                var client = new Client(sender, sessionID);

                if (type == 1) {
                    var data = container.computeIfAbsent(client, __ -> new Data(totalOper));

                    data.update(idPosOper, opValue);

                    // ACK
                    buffer.clear();
                    buffer.put((byte) 2).putLong(sessionID).putLong(idPosOper).flip();
                    dc.send(buffer, sender);

                    if (data.isFinished()) {
                        buffer.clear();
                        buffer.put((byte) 3).putLong(sessionID).putLong(data.sum).flip();
                        dc.send(buffer, sender);
                    }
                } else if (type == 4) {
                    buffer.clear();
                    // ACK CLEAN
                    container.remove(client);
                    buffer.put((byte) 5).putLong(sessionID).flip();
                    dc.send(buffer, sender);
                }
            }
        } finally {
            dc.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }

        var port = Integer.parseInt(args[0]);

        if (!(port >= 1024) & port <= 65535) {
            logger.severe("The port number must be between 1024 and 65535");
            return;
        }

        try {
            new ServerLongSumUDP(port).serve();
        } catch (BindException e) {
            logger.severe("Server could not bind on " + port + "\nAnother server is probably running on this port.");
        }
    }
}