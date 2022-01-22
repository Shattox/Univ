package fr.upem.net.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.Charset;

public class ReadStandardInputWithEncoding {

	private static final int BUFFER_SIZE = 1024;

	private static void usage() {
		System.out.println("Usage: ReadStandardInputWithEncoding charset");
	}

	private static String stringFromStandardInput(Charset cs) throws IOException {
		var in = Channels.newChannel(System.in);
		var buffer = ByteBuffer.allocate(BUFFER_SIZE);
		var bytesRead = 0;

		while ((bytesRead += in.read(buffer)) != -1) {
			if (bytesRead >= buffer.capacity()) {
				var newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
				buffer.flip();
				newBuffer.put(buffer);
				buffer = newBuffer;
			}
		}
		buffer.flip();
		return cs.decode(buffer).toString();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		Charset cs = Charset.forName(args[0]);
		System.out.print(stringFromStandardInput(cs));
	}
}
