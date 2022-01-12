package fr.uge.poo.visitors.expr.ex3;

import java.util.Objects;
import java.util.function.IntBinaryOperator;

public record BinOp(Expr left, Expr right,
                    String opName, IntBinaryOperator operator) implements Expr {
    public BinOp(Expr left, Expr right, String opName,
                 IntBinaryOperator operator) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        this.opName = Objects.requireNonNull(opName);
        this.operator = Objects.requireNonNull(operator);
    }
}
