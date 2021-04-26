package net.haspamelodica.minic.model.expressions;

public class Unary implements Expression
{
	private final UnaryOp		operation;
	private final Expression	rhs;

	public Unary(UnaryOp operation, Expression rhs)
	{
		this.operation = operation;
		this.rhs = rhs;
	}

	public UnaryOp getOperation()
	{
		return operation;
	}
	public Expression getRhs()
	{
		return rhs;
	}

	@Override
	public <P, R> R accept(ExpressionVisitor<P, R> visitor, P argument)
	{
		return visitor.visit(this, argument);
	}

	public static enum UnaryOp
	{
		NEGATE,
		NOT;
	}
}
