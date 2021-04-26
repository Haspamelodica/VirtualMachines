package net.haspamelodica.minic.model.statements;

import java.util.List;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public class Composite implements Statement
{
	private final List<Statement> statements;

	public Composite(List<Statement> statements)
	{
		this.statements = statements;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		for(Statement statement : statements)
			statement.appendCode(assembler, rho);
	}
	@Override
	public int maxStackSize()
	{
		return statements.stream().mapToInt(Statement::maxStackSize).max().orElse(0);
	}

	public List<Statement> getStatements()
	{
		return statements;
	}
}
