package com.evilcorp.stp.stphipster;

public sealed interface STPCommand permits ElapsedTimeCmd, HelloCmd, StartTimerCmd, StopTimerCmd {
     void accept(ParserProcessor parserProcessor);
}
