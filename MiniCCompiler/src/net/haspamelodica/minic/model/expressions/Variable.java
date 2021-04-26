package net.haspamelodica.minic.model.expressions;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.environment.TaggedVariable;
import net.haspamelodica.minic.model.types.Type;

public class Variable implements Expression
{
	private final String name;

	public Variable(String name)
	{
		this.name = name;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		return getVariable(rho).getType();
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		getVariable(rho).appendCodeR(assembler);
	}
	@Override
	public void appendCodeL(Assembler assembler, AddressEnvironment rho)
	{
		getVariable(rho).appendCodeL(assembler);
	}
	@Override
	public void appendCodeStore(Assembler assembler, AddressEnvironment rho)
	{
		getVariable(rho).appendStore(assembler);
	}
	private TaggedVariable getVariable(AddressEnvironment rho)
	{
		return rho.getVariable(getName());
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return 1;
	}
	@Override
	public int maxStackSizeL()
	{
		return 1;
	}
	@Override
	public int maxStackSizeStore()
	{
		return 0;
	}

	public String getName()
	{
		return name;
	}
}
