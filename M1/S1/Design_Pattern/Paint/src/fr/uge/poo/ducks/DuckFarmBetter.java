package fr.uge.poo.ducks;

import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;

public class DuckFarmBetter {
    private static Duck setName(String name, Duck duck) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Duck) duck.getClass().getDeclaredConstructors()[1].newInstance(name);
    }

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var serviceLoader = ServiceLoader.load(Duck.class);

        for (var duck: serviceLoader) {
            System.out.println(setName("Riri", duck).quack());
            System.out.println(setName("Fifi", duck).quack());
            System.out.println(setName("Loulou", duck).quack());
        }
    }
}
