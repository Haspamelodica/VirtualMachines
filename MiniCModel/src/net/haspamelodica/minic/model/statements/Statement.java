package net.haspamelodica.minic.model.statements;

public interface Statement
{
	public <P, R> R accept(StatementVisitor<P, R> visitor, P argument);
	public default <R> R accept(StatementVisitor<Void, R> visitor)
	{
		return accept(visitor, null);
	}
}
