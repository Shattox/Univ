package fr.uge.poo.visitors.expr.ex2;

public class Value implements Expr {
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public<T> T accept(ExprVisitor<T> exprVisitor) {
        return exprVisitor.visitValue(this);
    }
}
