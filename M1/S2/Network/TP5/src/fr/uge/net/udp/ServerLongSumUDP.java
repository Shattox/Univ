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
    public static class Data {
        private final long totalOper;
        private final BitSet bitSet;
        private long result;
        private int counter;
        /*private final long lifeCycle;*/

        public Data(long totalOper) {
            if (totalOper < 0) {
                throw new IllegalArgumentException("totalOper need to be >= 0");
            }
            this.result = 0;
            this.counter = 0;
            this.totalOper = totalOper;
            this.bitSet = new BitSet((int) totalOper);
            /*this.lifeCycle = System.currentTimeMillis();*/
        }

        public void update(int idPosOper, long opValue) {
            if (!bitSet.get(idPosOper)) {
                bitSet.set(idPosOper);
                counter++;
                result += opValue;
            }
        }

        public boolean isFinished() {
            return counter == totalOper;
        }
    }

    private static final Logger logger = Logger.getLogger(ServerLongSumUDP.class.getName());
    private static final int BUFFER_SIZE = 1024;

    private final DatagramChannel dc;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private final HashMap<InetSocketAddress, HashMap<Long, Data>> container;

    private ServerLongSumUDP(int port) throws IOException {
        dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(port));
        this.container = new HashMap<>();
        logger.info("ServerBetterUpperCaseUDP started on port " + port);
    }

    public void serve() throws IOException {
        try {
            while (!Thread.interrupted()) {
                buffer.clear();
                var sender = (InetSocketAddress) dc.receive(buffer);
                buffer.flip();

                if ((buffer.remaining() < ((Long.BYTES * 4) + Byte.BYTES))) {
                    continue;
                }
                var type = buffer.get();
                var sessionId = buffer.getLong();

                if (type == 1) {
                    var idPosOper = (int) buffer.getLong();
                    var totalOper = buffer.getLong();
                    var opValue = buffer.getLong();

                    var data = new Data(totalOper);
                    data.update(idPosOper, opValue);

                    // OP
                    if (!container.containsKey(sender)) {
                        var map = new HashMap<Long, Data>();
                        map.put(sessionId, data);
                        container.put(sender, map);
                    } else {
                        var client = container.get(sender);
                        if (client.containsKey(sessionId)) {
                            client.get(sessionId).update(idPosOper, opValue);
                        } else {
                            client.put(sessionId, data);
                        }
                    }

                    // ACK
                    buffer.clear();
                    buffer.put((byte) 2).putLong(sessionId).putLong(idPosOper).flip();
                    dc.send(buffer, sender);

                    // RES
                    var clientSessionData = container.get(sender).get(sessionId);
                    if (clientSessionData.isFinished()) {
                        buffer.clear();
                        buffer.put((byte) 3).putLong(sessionId).putLong(clientSessionData.result).flip();
                        dc.send(buffer, sender);
                    }
                }
                // FONCTIONNE PAS
                /*if (type == 4 || (System.currentTimeMillis() - container.get(sender).get(sessionId).lifeCycle) >= 400) {
                    // CLEAN
                    container.get(sender).remove(sessionId);
                    // ACK CLEAN
                    buffer.clear();
                    buffer.put((byte) 5).putLong(sessionId).flip();
                    dc.send(buffer, sender);
                }*/
            }
        } finally {
            dc.close();
        }
    }

    public static void usage() {
        System.out.println("Usage : ServerIdUpperCaseUDP port");
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
            return;
        }
    }
}

// Question : Pourquoi ne peut-on pas libérer les ressources liées à une session avec un client après avoir envoyé le paquet contenant le résultat ?
// Car il est possible que le client ne reçoit pas le paquet contenant le résultat.
// Dans se cas si on libère les ressources on ne pourra pas renvoyé le paquet contenant ce résultat final.