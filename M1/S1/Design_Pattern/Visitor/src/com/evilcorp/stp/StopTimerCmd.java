package com.evilcorp.stp;

public class StopTimerCmd implements STPCommand {
    private int  timerId;

    public StopTimerCmd(int timerId){
        this.timerId = timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public void accept(STPVisitor stpVisitor) {
        stpVisitor.visit(this);
    }
}
