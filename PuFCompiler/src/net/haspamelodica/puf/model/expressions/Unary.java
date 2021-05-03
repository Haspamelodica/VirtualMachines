package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.mkbasic;
import static net.haspamelodica.mama.model.Opcode.neg;
import static net.haspamelodica.mama.model.Opcode.not;

import java.util.Set;

import net.haspamelodica.mama.model.Opcode;
import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

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
	public Set<String> getFreeVariables()
	{
		return rhs.getFreeVariables();
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		rhs.appendCodeB(assembler, rho, stackDistance, cbv);
		assembler.append(operation.getOpcode());
		assembler.append(mkbasic);
	}
	@Override
	public void appendCodeB(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		rhs.appendCodeB(assembler, rho, stackDistance, cbv);
		assembler.append(operation.getOpcode());
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
