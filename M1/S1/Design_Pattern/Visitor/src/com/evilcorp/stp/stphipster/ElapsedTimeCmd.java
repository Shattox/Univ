package com.evilcorp.stp.stphipster;

import java.util.List;
import java.util.Objects;

public record ElapsedTimeCmd(List<Integer> timers) implements STPCommand {

    public ElapsedTimeCmd(List<Integer> timers) {
        Objects.requireNonNull(timers);
        this.timers = List.copyOf(timers);
    }

    public List<Integer> getTimers() {
        return timers;
    }

    @Override
    public void accept(ParserProcessor parserProcessor) {
        parserProcessor.process(this);
    }
}
