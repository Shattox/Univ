package com.evilcorp.stp.stphipster;

public record HelloCmd() implements STPCommand {
    @Override
    public void accept(ParserProcessor parserProcessor) {
        parserProcessor.process(this);
    }
}
