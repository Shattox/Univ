package fr.uge.poo.visitors.expr.ex2;

public class ToStringVisitor implements ExprVisitor<String> {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public String visitValue(Value value) {
        return sb.append(value.getValue()).toString();
    }

    @Override
    public String visitBinOp(BinOp binOp) {
        sb.append("(");
        binOp.getLeft().accept(this);
        sb.append(" ").append(binOp.getOpName()).append(" ");
        binOp.getRight().accept(this);
        sb.append(")");
        return sb.toString();
    }
}
