package fr.upem.net.tcp.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HttpClient {
    private final static Logger logger = Logger.getLogger(HttpClient.class.getName());
    private final static int BUFFER_SIZE = 1024;
    private final static Charset UTF8 = StandardCharsets.UTF_8;
    private final static Charset ASCII = StandardCharsets.US_ASCII;

    private ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private SocketChannel sc;
    private String resource;
    private String server;

    public HttpClient(String server, String resource) {
        this.server = server;
        this.resource = resource;
    }

    private String GETRequest() {
        return "GET " + resource + " HTTP/1.1\r\n" + "Host: " + server + "\r\n" + "\r\n";
    }

    public HTTPReader connect() throws IOException {
        sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(server, 80));
        sc.write(ASCII.encode(GETRequest()));
        return  new HTTPReader(sc, buffer);
    }

    public String getResource() throws IOException {
        try {
            var httpReader = connect();
            var httpHeader = httpReader.readHeader();
            var code = httpHeader.getCode();
            if (code == 301 || code == 302) {
                var location = httpHeader.getFields().get("location");
                var url = new URL(location);
                this.server = url.getHost();
                this.resource = url.getPath();
                httpReader = connect();
                httpHeader = httpReader.readHeader();
            }
            if (httpHeader.isChunkedTransfer()) {
                buffer = httpReader.readChunks();
            } else {
                var contentLength = httpHeader.getContentLength();
                buffer = httpReader.readBytes(contentLength);
            }
            buffer.flip();
            var cs = httpHeader.getCharset();
            return cs.map(charset -> charset.decode(buffer).toString()).orElseGet(() -> UTF8.decode(buffer).toString());
        } finally {
            sc.close();
        }
    }

    private static void usage() {
        System.out.println("java fr.upem.net.tpc.http server resource");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            usage();
            return;
        }

        var server = args[0];
        var resource = args[1];

        var httpClient = new HttpClient(server, resource);
        System.out.println(httpClient.getResource());
    }
}
