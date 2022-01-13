package com.evilcorp.stp;

public class Visitor implements STPVisitor {

    @Override
    public void visit(ElapsedTimeCmd elapsedTimeCmd) {
        System.out.println("non implémenté");
    }

    @Override
    public void visit(HelloCmd helloCmd) {
        System.out.println("Au revoir");
    }

    @Override
    public void visit(StartTimerCmd startTimerCmd) {
        System.out.println("non implémenté");
    }

    @Override
    public void visit(StopTimerCmd stopTimerCmd) {
        System.out.println("non implémenté");
    }
}
