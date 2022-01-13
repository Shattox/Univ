package com.evilcorp.stp.stphipster;

public interface ParserProcessor {
    void process(HelloCmd helloCmd);
    void process(ElapsedTimeCmd elapsedTimeCmd);
    void process(StartTimerCmd startTimerCmd);
    void process(StopTimerCmd stopTimerCmd);

    default public void process(STPCommand stpCommand){
        switch (stpCommand) {
            case HelloCmd helloCmd -> process(helloCmd);
            case ElapsedTimeCmd elapsedTimeCmd -> process(elapsedTimeCmd);
            case StartTimerCmd startTimerCmd -> process(startTimerCmd);
            case StopTimerCmd stopTimerCmd -> process(stopTimerCmd);
        }
    }
}
