package fr.upem.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ClientEOS {

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    public static final int BUFFER_SIZE = 1024;
    public static final Logger logger = Logger.getLogger(ClientEOS.class.getName());

    /**
     * This method: 
     * - connect to server 
     * - writes the bytes corresponding to request in UTF8 
     * - closes the write-channel to the server 
     * - stores the bufferSize first bytes of server response 
     * - return the corresponding string in UTF8
     *
     * @param request request
     * @param server server
     * @param bufferSize bufferSize
     * @return the UTF8 string corresponding to bufferSize first bytes of server
     *         response
     * @throws IOException IOException
     */

    public static String getFixedSizeResponse(String request, SocketAddress server, int bufferSize) throws IOException {
        // TODO
        var buffer = ByteBuffer.allocate(bufferSize);
        var sc = SocketChannel.open();
        sc.connect(server);
        buffer.put(UTF8_CHARSET.encode(request)).flip();
        sc.write(buffer);
        sc.shutdownOutput();
        buffer.clear();
        readFully(sc,buffer);
        sc.close();
        buffer.flip();
        return UTF8_CHARSET.decode(buffer).toString();
    }

    /**
     * This method: 
     * - connect to server 
     * - writes the bytes corresponding to request in UTF8 
     * - closes the write-channel to the server 
     * - reads and stores all bytes from server until read-channel is closed 
     * - return the corresponding string in UTF8
     *
     * @param request request
     * @param server server
     * @return the UTF8 string corresponding the full response of the server
     * @throws IOException IOException
     */

    public static String getUnboundedResponse(String request, SocketAddress server) throws IOException {
        // TODO
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        var sc = SocketChannel.open();
        sc.connect(server);
        buffer.put(UTF8_CHARSET.encode(request)).flip();
        sc.write(buffer);
        sc.shutdownOutput();

        while (readFully(sc, buffer)) {
            buffer = ByteBuffer.allocate(buffer.capacity() * 2);
        }
        buffer.flip();
        return UTF8_CHARSET.decode(buffer).toString();
    }

    /**
     * Fill the workspace with the Bytebuffer with bytes read from sc.
     *
     * @param sc sc
     * @param buffer buffer
     * @return false if read returned -1 at some point and true otherwise
     * @throws IOException IOException
     */
    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
        // TODO
        while (buffer.hasRemaining()) {
            if (sc.read(buffer) == -1) {
                logger.info("Connection closed for reading");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        var google = new InetSocketAddress("www.google.fr", 80);
        System.out.println(getFixedSizeResponse("GET / HTTP/1.1\r\nHost: www.google.fr\r\n\r\n", google, 512));
        System.out.println(getUnboundedResponse("GET / HTTP/1.1\r\nHost: www.google.fr\r\n\r\n", google));
    }
}
