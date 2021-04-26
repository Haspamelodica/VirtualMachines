package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.neg;
import static net.haspamelodica.cma.model.Opcode.not;

import net.haspamelodica.cma.model.Opcode;
import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

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
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		rhs.appendCodeR(assembler, rho);
		assembler.append(operation.getOpcode());
	}
	@Override
	public int maxStackSizeR()
	{
		return rhs.maxStackSizeR();
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
