package net.haspamelodica.puf.model;

import net.haspamelodica.puf.model.expressions.Expression;

public class PuFProgram
{
	private final Expression outermostExpression;

	public PuFProgram(Expression outermostExpression)
	{
		this.outermostExpression = outermostExpression;
	}

	public Expression getOutermostExpression()
	{
		return outermostExpression;
	}
}
