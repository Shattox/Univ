package com.evilcorp.stp;

public interface STPObserver {
    default void onCommand(TreatmentVisitor treatmentVisitor) {}
    default void onQuitCommand(TreatmentVisitor treatmentVisitor) {}
}
