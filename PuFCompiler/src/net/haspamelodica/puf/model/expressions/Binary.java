package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.add;
import static net.haspamelodica.mama.model.Opcode.and;
import static net.haspamelodica.mama.model.Opcode.div;
import static net.haspamelodica.mama.model.Opcode.eq;
import static net.haspamelodica.mama.model.Opcode.geq;
import static net.haspamelodica.mama.model.Opcode.gr;
import static net.haspamelodica.mama.model.Opcode.le;
import static net.haspamelodica.mama.model.Opcode.leq;
import static net.haspamelodica.mama.model.Opcode.mkbasic;
import static net.haspamelodica.mama.model.Opcode.mod;
import static net.haspamelodica.mama.model.Opcode.mul;
import static net.haspamelodica.mama.model.Opcode.neq;
import static net.haspamelodica.mama.model.Opcode.or;
import static net.haspamelodica.mama.model.Opcode.sub;
import static net.haspamelodica.mama.model.Opcode.xor;

import java.util.HashSet;
import java.util.Set;

import net.haspamelodica.mama.model.Opcode;
import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

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
	public Set<String> getFreeVariables()
	{
		Set<String> freeVariables = new HashSet<>();
		freeVariables.addAll(lhs.getFreeVariables());
		freeVariables.addAll(rhs.getFreeVariables());
		return freeVariables;
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		lhs.appendCodeB(assembler, rho, stackDistance, cbv);
		rhs.appendCodeB(assembler, rho, stackDistance + 1, cbv);
		assembler.append(operation.getOpcode());
		assembler.append(mkbasic);
	}
	@Override
	public void appendCodeB(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		lhs.appendCodeB(assembler, rho, stackDistance, cbv);
		rhs.appendCodeB(assembler, rho, stackDistance + 1, cbv);
		assembler.append(operation.getOpcode());
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
