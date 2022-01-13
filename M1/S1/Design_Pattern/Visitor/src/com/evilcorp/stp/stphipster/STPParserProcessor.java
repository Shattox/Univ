package com.evilcorp.stp.stphipster;

import java.time.LocalDateTime;
import java.util.HashMap;

public class STPParserProcessor implements ParserProcessor {
    private final HashMap<Integer, Long> timers = new HashMap<>();

    @Override
    public void process(HelloCmd helloCmd) {
        System.out.println("Hello the current date is " + LocalDateTime.now());
    }

    @Override
    public void process(ElapsedTimeCmd elapsedTimeCmd) {
        var currentTime =  System.currentTimeMillis();
        for(var timerId : elapsedTimeCmd.getTimers()){
            var startTime = timers.get(timerId);
            if (startTime==null){
                System.out.println("Unknown timer " + timerId);
                continue;
            }
            System.out.println("Elapsed time on timerId " + timerId + " : " + (currentTime-startTime) + "ms");
        }
    }

    @Override
    public void process(StartTimerCmd startTimerCmd) {
        var timerId = startTimerCmd.getTimerId();
        if (timers.get(timerId)!=null){
            System.out.println("Timer "+timerId+" was already started");
        }
        var currentTime =  System.currentTimeMillis();
        timers.put(timerId,currentTime);
        System.out.println("Timer "+timerId+" started");
    }

    @Override
    public void process(StopTimerCmd stopTimerCmd) {
        var timerId = stopTimerCmd.getTimerId();
        var startTime = timers.get(timerId);
        if (startTime == null){
            System.out.println("Timer "+timerId+" was never started");
        }
        var currentTime =  System.currentTimeMillis();
        System.out.println("Timer "+timerId+" was stopped after running for "+(currentTime-startTime)+"ms");
        timers.put(timerId,null);
    }
}
