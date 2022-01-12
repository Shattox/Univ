package com.evilcorp.stp.stphipster;

public record StopTimerCmd(int timerId) implements STPCommand {

    public StopTimerCmd(int timerId){
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
