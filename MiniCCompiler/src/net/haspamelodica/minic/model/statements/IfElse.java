package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.jump;
import static net.haspamelodica.cma.model.Opcode.jumpz;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.model.expressions.Expression;

public class IfElse implements Statement
{
	private final Expression	condition;
	private final Statement		thenBranch;
	private final Statement		elseBranch;

	public IfElse(Expression condition, Statement thenBranch, Statement elseBranch)
	{
		this.condition = condition;
		this.thenBranch = thenBranch;
		this.elseBranch = elseBranch;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		condition.appendCodeR(assembler, rho);
		Label A = assembler.createLabel("A");
		assembler.append(jumpz, A);
		thenBranch.appendCode(assembler, rho);
		Label B = assembler.createLabel("B");
		assembler.append(jump, B);
		assembler.labelNextInstruction(A);
		elseBranch.appendCode(assembler, rho);
		assembler.labelNextInstruction(B);
	}
	@Override
	public int maxStackSize()
	{
		return Math.max(condition.maxStackSizeR(), Math.max(thenBranch.maxStackSize(), elseBranch.maxStackSize()));
	}

	public Expression getCondition()
	{
		return condition;
	}
	public Statement getThenBranch()
	{
		return thenBranch;
	}
	public Statement getElseBranch()
	{
		return elseBranch;
	}
}
