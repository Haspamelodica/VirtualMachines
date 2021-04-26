package net.haspamelodica.minic.model.expressions;

public class Constant implements Expression
{
	private final int value;

	public Constant(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public <P, R> R accept(ExpressionVisitor<P, R> visitor, P argument)
	{
		return visitor.visit(this, argument);
	}
}
