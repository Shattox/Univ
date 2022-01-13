package com.evilcorp.stp;

public class StartTimerCmd implements STPCommand {
    private int timerId;

    public StartTimerCmd(int timerId){
        this.timerId = timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public void accept(STPVisitor stpVisitor) {
        stpVisitor.visit(this);
    }
}
