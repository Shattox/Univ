package fr.upem.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientConcatenation {
    public static final Logger logger = Logger.getLogger(ClientLongSum.class.getName());
    public static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 1024;

    public static void sendRequest(List<String> chaines, SocketAddress server) throws IOException {
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        var sc = SocketChannel.open();
        sc.connect(server);

        buffer.putInt(chaines.size());

        for (var chaine: chaines) {
            buffer.putInt(UTF8.encode(chaine).remaining()).put(UTF8.encode(chaine));
        }
        buffer.flip();
        sc.write(buffer);
        sc.shutdownOutput();
        buffer.clear();
    }

    public static void getResponse(SocketAddress server) throws IOException {
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        var sc = SocketChannel.open();
        sc.connect(server);

        buffer.clear();
        if (readFully(sc, buffer)) {
            buffer.flip();
            var size = buffer.getInt();
            System.out.println("Received : " + UTF8.decode(buffer.slice(buffer.position(), size)));
        }
    }

    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
        // TODO
        while (buffer.hasRemaining()) {
            if (sc.read(buffer) == -1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        var server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        var chaines = new ArrayList<String>();

        try (var scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                System.out.println(line == " ");
                if (line.isEmpty()) {
                    sendRequest(chaines, server);
                    getResponse(server);
                    break;
                }
                chaines.add(line);
            }
        }
    }
}
