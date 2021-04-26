package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.neg;
import static net.haspamelodica.cma.model.Opcode.not;

import net.haspamelodica.cma.model.Opcode;
import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.PrimitiveType;
import net.haspamelodica.minic.model.types.Type;

public class Unary implements Expression
{
	private final UnaryOp		operation;
	private final Expression	rhs;

	public Unary(UnaryOp operation, Expression rhs)
	{
		this.operation = operation;
		this.rhs = rhs;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		if(check && !rhs.getType(rho, check).isAssignableTo(PrimitiveType.int_))
			throw new CompilerException("RHS type not assignable to int");
		return PrimitiveType.int_;
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		rhs.appendCodeR(assembler, rho);
		assembler.append(operation.getOpcode());
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return rhs.maxStackSizeR(rho);
	}

	public UnaryOp getOperation()
	{
		return operation;
	}
	public Expression getRhs()
	{
		return rhs;
	}

	public static enum UnaryOp
	{
		NEGATE(neg),
		NOT(not);

		private final Opcode opcode;

		private UnaryOp(Opcode opcode)
		{
			this.opcode = opcode;
		}

		public Opcode getOpcode()
		{
			return opcode;
		}
	}
}
