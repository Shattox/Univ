package com.evilcorp.stp.stphipster;

public record StartTimerCmd(int timerId) implements STPCommand {

    public StartTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    @Override
    public void accept(ParserProcessor parserProcessor) {
        parserProcessor.process(this);
    }
}
