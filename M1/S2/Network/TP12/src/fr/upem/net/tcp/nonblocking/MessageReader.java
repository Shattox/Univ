package fr.upem.net.tcp.nonblocking;

import java.nio.ByteBuffer;

public class MessageReader implements Reader<MessageReader.Message> {
    public record Message(String login, String text) {
    }

    private enum State {
        DONE, WAITING, ERROR
    }

    private final StringReader stringReader = new StringReader();
    private State state = State.WAITING;
    private Message message;

    @Override
    public ProcessStatus process(ByteBuffer bb) {
        var processStatus = stringReader.process(bb);
        if (processStatus != ProcessStatus.DONE) {
            return processStatus;
        }
        var login = stringReader.get();
        stringReader.reset();
        processStatus = stringReader.process(bb);
        if (processStatus != ProcessStatus.DONE) {
            return processStatus;
        }
        var text = stringReader.get();
        message = new Message(login, text);
        state = State.DONE;
        return ProcessStatus.DONE;
    }

    @Override
    public Message get() {
        if (state != State.DONE) {
            throw new IllegalStateException();
        }
        return message;
    }

    @Override
    public void reset() {
        state = State.WAITING;
        stringReader.reset();
    }
}
