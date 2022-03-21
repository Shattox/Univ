package fr.upem.net.tcp.nonblocking;

import java.nio.ByteBuffer;

public class MessageReader implements Reader<MessageReader.Message> {
    public static record Message(String login, String text) {
    }
    private final StringReader stringReader = new StringReader();
    private Message message;

    @Override
    public ProcessStatus process(ByteBuffer bb) {
        stringReader.process(bb);
        var login = stringReader.get();
        stringReader.reset();
        stringReader.process(bb);
        var text = stringReader.get();
        message = new Message(login, text);
        return ProcessStatus.DONE;
    }

    @Override
    public Message get() {
        return message;
    }

    @Override
    public void reset() {
    }
}
