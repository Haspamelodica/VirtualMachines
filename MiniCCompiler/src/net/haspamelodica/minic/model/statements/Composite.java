package net.haspamelodica.minic.model.statements;

import java.util.List;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;

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
	public int maxStackSize(AddressEnvironment rho)
	{
		return statements.stream().mapToInt(s -> s.maxStackSize(rho)).max().orElse(0);
	}

	public List<Statement> getStatements()
	{
		return statements;
	}
}
