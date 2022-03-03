package fr.upem.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientConcatenation {
    public static final Logger logger = Logger.getLogger(ClientLongSum.class.getName());
    public static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final int BUFFER_SIZE = 1024;

    public static Optional<String> serve(List<String> chaines, SocketChannel sc) throws IOException {
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);

        // Write
        buffer.putInt(chaines.size());
        for (var chaine : chaines) {
            buffer.putInt(UTF8.encode(chaine).remaining()).put(UTF8.encode(chaine));
        }
        buffer.flip();
        sc.write(buffer);

        // Read
        var bufferSize = ByteBuffer.allocate(Integer.BYTES);
        if (!readFully(sc, bufferSize)) {
            return Optional.empty();
        }
        bufferSize.flip();
        var bufferString = ByteBuffer.allocate(bufferSize.getInt());
        if (!readFully(sc, bufferString)) {
            return Optional.empty();
        }
        bufferString.flip();
        return Optional.of(UTF8.decode(bufferString).toString());
    }

    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
        // TODO
        while (buffer.hasRemaining()) {
            if (sc.read(buffer) == -1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        var server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        var lines = new ArrayList<String>();
        var line = "";

        try (var sc = SocketChannel.open(server);
             var scanner = new Scanner(System.in)) {
            while (true) {
                while (!(line = scanner.nextLine()).isEmpty()) {
                    lines.add(line);
                }
                var chaine = serve(lines, sc);
                if (chaine.isEmpty()) {
                    logger.warning("Connection with server lost.");
                    return;
                }
                lines.clear();
                System.out.println(chaine.get());
            }
        }
    }
}
