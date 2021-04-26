package net.haspamelodica.minic.model.expressions;

public class Binary implements Expression
{
	private final Expression	lhs;
	private final BinaryOp		operation;
	private final Expression	rhs;

	public Binary(Expression lhs, BinaryOp operation, Expression rhs)
	{
		this.lhs = lhs;
		this.operation = operation;
		this.rhs = rhs;
	}

	public Expression getLhs()
	{
		return lhs;
	}
	public BinaryOp getOperation()
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

	public static enum BinaryOp
	{
		PLUS,
		MINUS,
		TIMES,
		DIVIDE,
		MODULO,
		AND,
		OR,
		XOR,
		EQUAL,
		INEQUAL,
		LESS,
		LESS_OR_EQUAL,
		GREATER,
		GREATER_OR_EQUAL;
	}
}
