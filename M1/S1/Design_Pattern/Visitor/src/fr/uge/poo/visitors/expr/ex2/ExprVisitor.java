package fr.uge.poo.visitors.expr.ex2;

public interface ExprVisitor<T> {
    T visitValue(Value value);
    T visitBinOp(BinOp binOp);
}
