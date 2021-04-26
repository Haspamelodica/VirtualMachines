package net.haspamelodica.minic.model.expressions;

public interface ExpressionVisitor<P, R>
{
	public R visit(Constant expression, P argument);
	public R visit(Variable expression, P argument);
	public R visit(Binary expression, P argument);
	public R visit(Unary expression, P argument);
}
