package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.*;

import java.util.Optional;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        var visitor = new TreatmentVisitor();

        while(scan.hasNextLine()){
            var line = scan.nextLine();
            if (line.equals("quit")){
                visitor.quit();
                break;
            }
            Optional<STPCommand> answer = STPParser.parse(line);
            if (answer.isEmpty()){
                System.out.println("Unrecognized command");
                continue;
            }
            STPCommand cmd = answer.get();
            cmd.accept(visitor);
        }
    }
}
