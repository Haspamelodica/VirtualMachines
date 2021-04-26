package net.haspamelodica.minic.model.expressions;

public class Variable implements Expression
{
	private final String name;

	public Variable(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public <P, R> R accept(ExpressionVisitor<P, R> visitor, P argument)
	{
		return visitor.visit(this, argument);
	}
}
