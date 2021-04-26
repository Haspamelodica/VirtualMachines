package net.haspamelodica.minic.model.expressions;

public interface Expression
{
	public <P, R> R accept(ExpressionVisitor<P, R> visitor, P argument);
	public default <R> R accept(ExpressionVisitor<Void, R> visitor)
	{
		return accept(visitor, null);
	}
}
