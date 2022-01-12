package com.evilcorp.stp;

public interface STPVisitor {
    void visit(ElapsedTimeCmd elapsedTimeCmd);
    void visit(HelloCmd helloCmd);
    void visit(StartTimerCmd startTimerCmd);
    void visit(StopTimerCmd stopTimerCmd);
}
