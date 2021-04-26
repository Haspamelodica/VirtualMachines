package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.loadc;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.types.PrimitiveType;
import net.haspamelodica.minic.model.types.Type;

public class IntConstant implements Expression
{
	private final int value;

	public IntConstant(int value)
	{
		this.value = value;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		return PrimitiveType.int_;
	}

	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		assembler.append(loadc, value);
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return 1;
	}


	public int getValue()
	{
		return value;
	}
}
