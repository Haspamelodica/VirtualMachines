package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.loadc;
import static net.haspamelodica.mama.model.Opcode.mkbasic;

import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class IntConstant implements Expression
{
	private final int value;

	public IntConstant(int value)
	{
		this.value = value;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		return Set.of();
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		assembler.append(loadc, value);
		assembler.append(mkbasic);
	}
	@Override
	public void appendCodeB(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		assembler.append(loadc, value);
	}

	public int getValue()
	{
		return value;
	}
}
