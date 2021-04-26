package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.add;
import static net.haspamelodica.cma.model.Opcode.and;
import static net.haspamelodica.cma.model.Opcode.div;
import static net.haspamelodica.cma.model.Opcode.eq;
import static net.haspamelodica.cma.model.Opcode.geq;
import static net.haspamelodica.cma.model.Opcode.gr;
import static net.haspamelodica.cma.model.Opcode.le;
import static net.haspamelodica.cma.model.Opcode.leq;
import static net.haspamelodica.cma.model.Opcode.mod;
import static net.haspamelodica.cma.model.Opcode.mul;
import static net.haspamelodica.cma.model.Opcode.neq;
import static net.haspamelodica.cma.model.Opcode.or;
import static net.haspamelodica.cma.model.Opcode.sub;
import static net.haspamelodica.cma.model.Opcode.xor;

import net.haspamelodica.cma.model.Opcode;
import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public class Binary implements Expression
{
	private final Expression	lhs;
	private final BinaryOp		operation;
	private final Expression	rhs;

	public Binary(Expression lhs, BinaryOp operation, Expression rhs)
	{
		this.lhs = lhs;
		this.operation = operation;
		this.rhs = rhs;
	}

	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		lhs.appendCodeR(assembler, rho);
		rhs.appendCodeR(assembler, rho);
		assembler.append(operation.getOpcode());
	}
	@Override
	public int maxStackSizeR()
	{
		return Math.max(lhs.maxStackSizeR(), 1 + rhs.maxStackSizeR());
	}

	public Expression getLhs()
	{
		return lhs;
	}
	public BinaryOp getOperation()
	{
		return operation;
	}
	public Expression getRhs()
	{
		return rhs;
	}

	public static enum BinaryOp
	{
		PLUS(add),
		MINUS(sub),
		TIMES(mul),
		DIVISION(div),
		MODULO(mod),
		AND(and),
		OR(or),
		XOR(xor),
		EQUAL(eq),
		INEQUAL(neq),
		LESS(le),
		LESS_OR_EQUAL(leq),
		GREATER(gr),
		GREATER_OR_EQUAL(geq);

		private final Opcode opcode;

		private BinaryOp(Opcode opcode)
		{
			this.opcode = opcode;
		}

		public Opcode getOpcode()
		{
			return opcode;
		}
	}
}
