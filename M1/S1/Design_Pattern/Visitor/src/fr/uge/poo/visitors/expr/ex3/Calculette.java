package fr.uge.poo.visitors.expr.ex3;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Calculette {
    public static void main(String[] args) {
        Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        var expr = Expr.parseExpr(it);

        var evalVisitor = new ExprVisitor<Integer, Object>();
        evalVisitor
                .when(BinOp.class, (binOp, context) -> binOp
                        .operator()
                        .applyAsInt(
                                evalVisitor.visit(binOp.left(), null),
                                evalVisitor.visit(binOp.right(), null)
                        )
                )
                .when(Value.class, (value, context) -> value.value());
        System.out.println(evalVisitor.visit(expr, null));

        var toString = new ExprVisitor<Object, StringBuilder>();
        toString
                .when(BinOp.class, (binOp, context) -> {
                    context.append('(');
                    toString.visit(binOp.left(), context);
                    context.append(' ').append(binOp.opName()).append(' ');
                    toString.visit(binOp.right(), context);
                    context.append(')');
                    return null;
                })
                .when(Value.class, (value, context) -> {
                    context.append(value.value());
                    return null;
                });
        var sb = new StringBuilder();
        toString.visit(expr, sb);
        System.out.println(sb);
    }
}
