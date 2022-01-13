package com.evilcorp.stp;

public interface STPCommand {
     int getTimerId();
     String getName();
     void accept(STPVisitor stpVisitor);
}
