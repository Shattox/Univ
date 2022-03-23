package fr.upem.net.tcp.nonblocking;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringReader implements Reader<String> {
    private enum State {
        DONE, WAITING, ERROR, WAITING_SIZE
    }

    private final static int BUFFER_SIZE = 1024;

    private State state = State.WAITING_SIZE;
    private final ByteBuffer internalBuffer = ByteBuffer.allocate(BUFFER_SIZE); // write-mode
    private final Charset charset = StandardCharsets.UTF_8;
    private final IntReader intReader = new IntReader();
    private String message;

    @Override
    public ProcessStatus process(ByteBuffer buffer) {
        if (state == State.DONE || state == State.ERROR) {
            throw new IllegalStateException();
        }
        if (state == State.WAITING_SIZE) {
            var status = intReader.process(buffer);
            if (status != ProcessStatus.DONE) {
                return status;
            }
            state = State.WAITING;
        }
        try {
            buffer.flip();
            var msgSize = intReader.get();
            if (msgSize < 0 || msgSize > 1024) {
                return ProcessStatus.ERROR;
            }
            while (buffer.hasRemaining() && internalBuffer.hasRemaining() && internalBuffer.position() < msgSize) {
                internalBuffer.put(buffer.get());
            }
            if (internalBuffer.position() < msgSize) {
                return ProcessStatus.REFILL;
            }
        } finally {
            buffer.compact();
        }
        state = State.DONE;
        internalBuffer.flip();
        message = charset.decode(internalBuffer).toString();
        return ProcessStatus.DONE;
    }

    @Override
    public String get() {
        if (state != State.DONE) {
            throw new IllegalStateException();
        }
        return message;
    }

    @Override
    public void reset() {
        state = State.WAITING_SIZE;
        intReader.reset();
        internalBuffer.clear();
    }
}
