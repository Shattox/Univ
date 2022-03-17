package fr.upem.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.logging.Logger;

public class ServerSumOneShot {

	private static final int BUFFER_SIZE = 2 * Integer.BYTES;
	private static final Logger logger = Logger.getLogger(ServerSumOneShot.class.getName());

	private final ServerSocketChannel serverSocketChannel;
	private final Selector selector;

	public ServerSumOneShot(int port) throws IOException {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(port));
		selector = Selector.open();
	}

	public void launch() throws IOException {
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (!Thread.interrupted()) {
			Helpers.printKeys(selector); // for debug
			System.out.println("Starting select");
			selector.select(this::treatKey);
			System.out.println("Select finished");
		}
	}

	private void treatKey(SelectionKey key) {
		Helpers.printSelectedKey(key); // for debug
		if (key.isValid() && key.isAcceptable()) {
			try {
				doAccept(key); // Stop server, remonter uncheckedException
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		if (key.isValid() && key.isWritable()) {
			try {
				doWrite(key); // Close connexion with client
			} catch (IOException e) {
				silentlyClose(key);
			}
		}
		if (key.isValid() && key.isReadable()) {
			try {
				doRead(key); // Close connexion with client
			} catch (IOException e) {
				silentlyClose(key);
			}
		}
	}

	private void doAccept(SelectionKey key) throws IOException {
		// TODO
		var sc = serverSocketChannel.accept();
		if (sc == null) {
			return;
		}
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUFFER_SIZE));
	}

	private void doRead(SelectionKey key) throws IOException {
		// TODO
		var sc = (SocketChannel) key.channel();
		var buffer = (ByteBuffer) key.attachment();
		if (sc.read(buffer) == -1) {
			silentlyClose(key);
			return;
		}
		if (buffer.hasRemaining()) {
			return;
		}
		buffer.flip();
		var sum = buffer.getInt() + buffer.getInt();
		buffer.clear().putInt(sum);
		key.interestOps(SelectionKey.OP_WRITE);
	}

	private void doWrite(SelectionKey key) throws IOException {
		// TODO
		var sc = (SocketChannel) key.channel();
		var buffer = (ByteBuffer) key.attachment();
		buffer.flip();
		sc.write(buffer);
		if (buffer.hasRemaining()) {
			buffer.compact();
			return;
		}
		buffer.clear();
		key.interestOps(SelectionKey.OP_READ);
	}

	private void silentlyClose(SelectionKey key) {
		var sc = (Channel) key.channel();
		try {
			sc.close();
		} catch (IOException e) {
			// ignore exception
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		new ServerSumOneShot(Integer.parseInt(args[0])).launch();
	}

	private static void usage() {
		System.out.println("Usage : ServerSumOneShot port");
	}
}