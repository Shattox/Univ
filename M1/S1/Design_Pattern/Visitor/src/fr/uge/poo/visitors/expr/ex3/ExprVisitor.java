package fr.uge.poo.visitors.expr.ex3;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;

public class ExprVisitor<T, E> {
    private final HashMap<Class<? extends Expr>, BiFunction<Expr, E, T>> map = new HashMap<>();

    public T visit(Expr expr, E ctx) {
        return map.get(expr.getClass()).apply(expr, ctx);
    }

    public<U extends Expr> ExprVisitor<T, E> when(Class<U> type, BiFunction<U, E, T> function) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(map);
        map.put(type, (BiFunction<Expr, E, T>) function);
        return this;
    }
}
