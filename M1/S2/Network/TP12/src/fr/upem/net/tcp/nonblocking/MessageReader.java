package fr.upem.net.tcp.nonblocking;

import java.nio.ByteBuffer;

public class MessageReader implements Reader<MessageReader.Message> {
    public record Message(String login, String text) {
    }

    private enum State {
        DONE, ERROR, WAITING_LOGIN, WAITING_TEXT
    }

    private final StringReader stringReader = new StringReader();
    private State state = State.WAITING_LOGIN;
    private Message message;

    @Override
    public ProcessStatus process(ByteBuffer buffer) {
        if (state == State.DONE || state == State.ERROR) {
            throw new IllegalStateException();
        }
        // Process login
        var subProcessStatus = subProcess(buffer, State.WAITING_LOGIN, State.WAITING_TEXT);
        if (subProcessStatus != ProcessStatus.DONE) {
            return subProcessStatus;
        }
        var login = stringReader.get();
        stringReader.reset();
        // Process text
        subProcessStatus = subProcess(buffer, State.WAITING_TEXT, State.DONE);
        if (subProcessStatus != ProcessStatus.DONE) {
            return subProcessStatus;
        }
        var text = stringReader.get();
        stringReader.reset();
        message = new Message(login, text);
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
        state = State.WAITING_LOGIN;
        stringReader.reset();
    }

    private ProcessStatus subProcess(ByteBuffer buffer, State expectedState, State nextState) {
        if (state == expectedState) {
            var processStatus = stringReader.process(buffer);
            if (processStatus != ProcessStatus.DONE) {
                return processStatus;
            }
            state = nextState;
        }
        return ProcessStatus.DONE;
    }
}
