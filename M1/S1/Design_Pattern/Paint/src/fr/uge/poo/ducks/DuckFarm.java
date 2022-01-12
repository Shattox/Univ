package fr.uge.poo.ducks;

import java.util.ServiceLoader;

public class DuckFarm {
    public static void main(String[] args) {
        ServiceLoader<Duck> loader = ServiceLoader.load(Duck.class);

        loader.stream().forEach(duck -> {
            System.out.println(duck.get().quack());
        });
    }
}
