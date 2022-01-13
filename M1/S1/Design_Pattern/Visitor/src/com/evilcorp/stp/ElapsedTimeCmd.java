package com.evilcorp.stp;

import java.util.List;
import java.util.Objects;

public class ElapsedTimeCmd implements STPCommand {
    private final List<Integer> timers;

    public ElapsedTimeCmd(List<Integer> timers) {
        Objects.requireNonNull(timers);
        this.timers = List.copyOf(timers);
    }

    public List<Integer> getTimers() {
        return timers;
    }

    @Override
    public int getTimerId() {
        return -1;
    }

    @Override
    public String getName() {
        return "elapsed";
    }

    @Override
    public void accept(STPVisitor stpVisitor) {
        stpVisitor.visit(this);
    }
}
