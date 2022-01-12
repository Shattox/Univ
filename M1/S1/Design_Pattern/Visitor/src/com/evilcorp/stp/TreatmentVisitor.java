package com.evilcorp.stp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TreatmentVisitor implements  STPVisitor {
    private final HashMap<Integer, Long> timers = new HashMap<>();
    private final ArrayList<STPObserver> observers = new ArrayList<>();

    public TreatmentVisitor() {
        register(new CommandObserver());
        register(new ChronoTimerObserver());
    }

    @Override
    public void visit(ElapsedTimeCmd elapsedTimeCmd) {
        notifyCommand(elapsedTimeCmd);
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
    public void visit(HelloCmd helloCmd) {
        notifyCommand(helloCmd);
        System.out.println("Hello the current date is " + LocalDateTime.now());
    }

    @Override
    public void visit(StartTimerCmd startTimerCmd) {
        notifyCommand(startTimerCmd);
        var timerId = startTimerCmd.getTimerId();
        if (timers.get(timerId)!=null){
            System.out.println("Timer "+timerId+" was already started");
        }
        var currentTime =  System.currentTimeMillis();
        timers.put(timerId,currentTime);
        System.out.println("Timer "+timerId+" started");
    }

    @Override
    public void visit(StopTimerCmd stopTimerCmd) {
        notifyCommand(stopTimerCmd);
        var timerId = stopTimerCmd.getTimerId();
        var startTime = timers.get(timerId);
        if (startTime == null){
            System.out.println("Timer "+timerId+" was never started");
        }
        var currentTime =  System.currentTimeMillis();
        System.out.println("Timer "+timerId+" was stopped after running for "+(currentTime-startTime)+"ms");
        timers.put(timerId,null);
    }

    public void quit() {
        notifyQuitCommand();
    }

    public void register(STPObserver stpObserver) {
        observers.add(Objects.requireNonNull(stpObserver));
    }

    public void notifyCommand(STPCommand stpCommand) {
        for (var observer: observers) {
            observer.onCommand(this, stpCommand);
        }
    }

    public void notifyQuitCommand() {
        for (var observer: observers) {
            observer.onQuitCommand(this);
        }
    }

    public interface STPObserver {
        default void onCommand(TreatmentVisitor treatmentVisitor, STPCommand stpCommand) {}
        default void onQuitCommand(TreatmentVisitor treatmentVisitor) {}
    }

    static class CommandObserver implements STPObserver {
        private final Map<String, Integer> counters = new HashMap<>();

        @Override
        public void onCommand(TreatmentVisitor treatmentVisitor, STPCommand stpCommand) {
            var commandName = stpCommand.getName();
            var counter = counters.get(commandName) != null ? counters.get(commandName) : 0;
            counters.put(commandName, counter + 1);
        }

        @Override
        public void onQuitCommand(TreatmentVisitor treatmentVisitor) {
            counters.forEach((name, count) -> System.out.println(name + " : " + count));
        }
    }

    static class ChronoTimerObserver implements STPObserver {
        private double averageTimer;

        @Override
        public void onCommand(TreatmentVisitor treatmentVisitor, STPCommand stpCommand) {
            var total = 0L;
            if (treatmentVisitor.timers.get(stpCommand.getTimerId()) != null) {
                total = System.currentTimeMillis() - treatmentVisitor.timers.get(stpCommand.getTimerId());
            }
            averageTimer += total;
        }

        @Override
        public void onQuitCommand(TreatmentVisitor treatmentVisitor) {
            averageTimer /= treatmentVisitor.timers.size();
            System.out.println("Average timer : " + averageTimer + "ms");
        }
    }
}
