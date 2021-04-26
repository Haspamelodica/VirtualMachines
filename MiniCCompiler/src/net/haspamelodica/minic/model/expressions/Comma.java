package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.pop;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.types.Type;

public class Comma implements Expression
{
	private final Expression	lhs;
	private final Expression	rhs;

	public Comma(Expression lhs, Expression rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		if(check)
			lhs.getType(rho, check);
		return rhs.getType(rho, check);
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		lhs.appendCodeR(assembler, rho);
		assembler.append(pop);
		rhs.appendCodeR(assembler, rho);
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return Math.max(lhs.maxStackSizeR(rho), rhs.maxStackSizeR(rho));
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
