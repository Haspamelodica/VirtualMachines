package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.loadc;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public class Constant implements Expression
{
	private final int value;

	public Constant(int value)
	{
		this.value = value;
	}

	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		assembler.append(loadc, value);
	}
	@Override
	public int maxStackSizeR()
	{
		return 1;
	}


	public int getValue()
	{
		return value;
	}
}
