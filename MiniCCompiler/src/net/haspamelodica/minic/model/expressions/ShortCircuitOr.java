package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.dup;
import static net.haspamelodica.cma.model.Opcode.jumpz;
import static net.haspamelodica.cma.model.Opcode.not;
import static net.haspamelodica.cma.model.Opcode.pop;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.PrimitiveType;
import net.haspamelodica.minic.model.types.Type;

public class ShortCircuitOr implements Expression
{
	private final Expression	lhs;
	private final Expression	rhs;

	public ShortCircuitOr(Expression lhs, Expression rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		if(check)
			if(!lhs.getType(rho, check).isAssignableTo(PrimitiveType.int_))
				throw new CompilerException("Binary's LHS type not assignable to int");
			else if(!rhs.getType(rho, check).isAssignableTo(PrimitiveType.int_))
				throw new CompilerException("Binary's RHS type not assignable to int");
		return PrimitiveType.int_;
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		lhs.appendCodeR(assembler, rho);
		assembler.append(dup);
		assembler.append(not);
		Label A = assembler.createLabel("A");
		assembler.append(jumpz, A);
		assembler.append(pop);
		rhs.appendCodeR(assembler, rho);
		assembler.labelNextInstruction(A);
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return Math.max(lhs.maxStackSizeR(rho), Math.max(2, rhs.maxStackSizeR(rho)));
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
