package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.eval;

import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class Variable implements Expression
{
	private final String name;

	public Variable(String name)
	{
		this.name = name;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		return Set.of(name);
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		rho.appendGetvar(assembler, name, stackDistance);
		if(!cbv)
			assembler.append(eval);
	}

	public String getName()
	{
		return name;
	}
}
