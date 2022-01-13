package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Triviale {
    public static void main(String[] args) {
        HashMap<Integer,Long> timers = new HashMap<>();
        var scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            var line = scan.nextLine();
            if (line.equals("quit")){
                break;
            }
            Optional<STPCommand> answer = STPParser.parse(line);
            if (answer.isEmpty()){
                System.out.println("Pas compris");
                continue;
            }
            STPCommand cmd = answer.get();
            var visitor = new Visitor();
            cmd.accept(visitor);
        }
    }
}
