package net.haspamelodica.minic.model.expressions;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.Type;

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
	public Type getType(AddressEnvironment rho, boolean check)
	{
		Type lhsType = lhs.getType(rho, check);
		if(check)
		{
			//TODO check if the lhs type is something that actually can be assigned to
			if(!rhs.getType(rho, check).isAssignableTo(lhsType))
				throw new CompilerException("Assignment's RHS type is not assignable to LHS type");
		}
		return lhsType;
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		rhs.appendCodeR(assembler, rho);
		lhs.appendCodeStore(assembler, rho);
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return Math.max(rhs.maxStackSizeR(rho), 1 + lhs.maxStackSizeStore());
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
