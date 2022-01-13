package com.evilcorp.stp;

public class HelloCmd implements STPCommand {

    @Override
    public int getTimerId() {
        return -1;
    }

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public void accept(STPVisitor stpVisitor) {
        stpVisitor.visit(this);
    }
}
