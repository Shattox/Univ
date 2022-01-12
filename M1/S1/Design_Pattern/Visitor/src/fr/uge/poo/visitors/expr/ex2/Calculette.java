package fr.uge.poo.visitors.expr.ex2;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Calculette {
    public static void main(String[] args) {
        var exprVisitor = new EvalExprVisitor();
        var toStringVisitor = new ToStringVisitor();

        Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        Expr expr = Expr.parseExpr(it);
        System.out.println(expr.accept(toStringVisitor));
        System.out.println(expr.accept(exprVisitor));
    }
}
