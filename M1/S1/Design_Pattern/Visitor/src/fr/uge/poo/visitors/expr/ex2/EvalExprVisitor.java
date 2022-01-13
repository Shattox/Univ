package fr.uge.poo.visitors.expr.ex2;

public class EvalExprVisitor implements ExprVisitor<Integer> {

    @Override
    public Integer visitValue(Value value) {
        return value.getValue();
    }

    @Override
    public Integer visitBinOp(BinOp binOp) {
        return binOp.getOperator()
                .applyAsInt(binOp.getLeft().accept(this), binOp.getRight().accept(this));
    }
}
