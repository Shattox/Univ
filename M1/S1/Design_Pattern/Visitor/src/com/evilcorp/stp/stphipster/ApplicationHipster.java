package com.evilcorp.stp.stphipster;

import java.util.Optional;
import java.util.Scanner;

public class ApplicationHipster {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            var line = scan.nextLine();
            if (line.equals("quit")){
                break;
            }
            Optional<STPCommand> answer = STPParser.parse(line);
            var processor = new STPParserProcessor();
            processor.process(answer.get());
        }
    }
}
