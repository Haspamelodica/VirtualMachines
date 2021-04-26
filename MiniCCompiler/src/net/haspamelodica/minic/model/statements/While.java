package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.jump;
import static net.haspamelodica.cma.model.Opcode.jumpz;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.expressions.Expression;

public class While implements Statement
{
	private final Expression	condition;
	private final Statement		body;

	public While(Expression condition, Statement body)
	{
		this.condition = condition;
		this.body = body;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		Label A = assembler.labelNextInstruction("A");
		condition.appendCodeR(assembler, rho);
		Label B = assembler.createLabel("B");
		assembler.append(jumpz, B);
		body.appendCode(assembler, rho);
		assembler.append(jump, A);
		assembler.labelNextInstruction(B);
	}
	@Override
	public int maxStackSize(AddressEnvironment rho)
	{
		return Math.max(condition.maxStackSizeR(rho), body.maxStackSize(rho));
	}

	public Expression getCondition()
	{
		return condition;
	}
	public Statement getBody()
	{
		return body;
	}
}
