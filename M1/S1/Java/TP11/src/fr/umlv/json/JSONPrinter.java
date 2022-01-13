package fr.umlv.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JSONPrinter {
    private static final ClassValue<List<Function<Record, String>>> CACHE =
            new ClassValue<>() {
                protected List<Function<Record, String>> computeValue(Class<?> type) {
                    return Arrays.stream(type.getRecordComponents())
                            .<Function<Record, String>>map(component -> {
                                var key = "\"" + name(component) + "\":";
                                var accesor = component.getAccessor();
                                return record -> key + escape(invoke(record, accesor));
                            })
                            .toList();
                }
            };

    public static String toJSON(Record record) {
        return CACHE.get(record.getClass()).stream()
                .map(function -> function.apply(record))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private static Object invoke(Record record, Method accessor) {
        try {
            return accessor.invoke(record);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("method not accessible", e);
        } catch (InvocationTargetException e) {
            var cause = e.getCause();
            switch (cause) {
                case RuntimeException exception -> throw exception;
                case Error error -> throw error;
                default -> throw new UndeclaredThrowableException(e);
            }
        }
    }

    private static String escape(Object o) {
        return o instanceof String ? "\"" + o + "\"": "" + o;
    }

    private static String name(RecordComponent recordComponent) {
        var name = recordComponent.getName();
        var annotation = recordComponent.getAnnotation(JSONProperty.class);
        if (annotation == null) {
            return name;
        }
        var value = annotation.value();
        return value.isEmpty() ?
                name.replace('_', '-') :
                value;
    }

    public static void main(String[] args) {
        var person = new Person("John", "Doe");
        System.out.println(toJSON(person));

        var alien = new Alien(100, "Saturn");
        System.out.println(toJSON(alien));
    }
}
