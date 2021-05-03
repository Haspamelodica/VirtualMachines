package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.jump;
import static net.haspamelodica.mama.model.Opcode.jumpz;

import java.util.HashSet;
import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.Assembler.Label;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class Conditional implements Expression
{
	private final Expression	condition;
	private final Expression	onTrue;
	private final Expression	onFalse;

	public Conditional(Expression condition, Expression onTrue, Expression onFalse)
	{
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		Set<String> freeVariables = new HashSet<>();
		freeVariables.addAll(condition.getFreeVariables());
		freeVariables.addAll(onTrue.getFreeVariables());
		freeVariables.addAll(onFalse.getFreeVariables());
		return freeVariables;
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		condition.appendCodeB(assembler, rho, stackDistance, cbv);
		Label A = assembler.createLabel("A");
		assembler.append(jumpz, A);
		onTrue.appendCodeV(assembler, rho, stackDistance, cbv);
		Label B = assembler.createLabel("B");
		assembler.append(jump, B);
		assembler.labelNextInstruction(A);
		onFalse.appendCodeV(assembler, rho, stackDistance, cbv);
		assembler.labelNextInstruction(B);
	}
	@Override
	public void appendCodeB(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		condition.appendCodeB(assembler, rho, stackDistance, cbv);
		Label A = assembler.createLabel("A");
		assembler.append(jumpz, A);
		onTrue.appendCodeB(assembler, rho, stackDistance, cbv);
		Label B = assembler.createLabel("B");
		assembler.append(jump, B);
		assembler.labelNextInstruction(A);
		onFalse.appendCodeB(assembler, rho, stackDistance, cbv);
		assembler.labelNextInstruction(B);
	}

	public Expression getCondition()
	{
		return condition;
	}
	public Expression getOnTrue()
	{
		return onTrue;
	}
	public Expression getOnFalse()
	{
		return onFalse;
	}
}
