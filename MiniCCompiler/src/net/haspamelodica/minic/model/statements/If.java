package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.jumpz;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.model.expressions.Expression;

public class If implements Statement
{
	private final Expression	condition;
	private final Statement		thenBranch;

	public If(Expression condition, Statement thenBranch)
	{
		this.condition = condition;
		this.thenBranch = thenBranch;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		condition.appendCodeR(assembler, rho);
		Label A = assembler.createLabel("A");
		assembler.append(jumpz, A);
		thenBranch.appendCode(assembler, rho);
		assembler.labelNextInstruction(A);
	}
	@Override
	public int maxStackSize()
	{
		return Math.max(condition.maxStackSizeR(), thenBranch.maxStackSize());
	}

	public Expression getCondition()
	{
		return condition;
	}
	public Statement getThenBranch()
	{
		return thenBranch;
	}
}
