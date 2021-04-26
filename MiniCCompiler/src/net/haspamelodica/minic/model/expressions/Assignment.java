package net.haspamelodica.minic.model.expressions;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public class Assignment implements Expression
{
	private final Expression	lhs;
	private final Expression	rhs;

	public Assignment(Expression lhs, Expression rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		rhs.appendCodeR(assembler, rho);
		lhs.appendCodeStore(assembler, rho);
	}
	@Override
	public int maxStackSizeR()
	{
		return Math.max(rhs.maxStackSizeR(), 1 + lhs.maxStackSizeStore());
	}

	public Expression getLhs()
	{
		return lhs;
	}
	public Expression getRhs()
	{
		return rhs;
	}
}
