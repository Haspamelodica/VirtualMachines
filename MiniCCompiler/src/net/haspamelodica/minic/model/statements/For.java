package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.jump;
import static net.haspamelodica.cma.model.Opcode.jumpz;
import static net.haspamelodica.cma.model.Opcode.pop;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.expressions.Expression;

public class For implements Statement
{
	private final Expression	initializer;
	private final Expression	condition;
	private final Expression	incrementer;
	private final Statement		body;

	public For(Expression initializer, Expression condition, Expression incrementer, Statement body)
	{
		this.initializer = initializer;
		this.condition = condition;
		this.incrementer = incrementer;
		this.body = body;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		initializer.appendCodeR(assembler, rho);
		assembler.append(pop);
		Label A = assembler.labelNextInstruction("A");
		condition.appendCodeR(assembler, rho);
		Label B = assembler.createLabel("B");
		assembler.append(jumpz, B);
		body.appendCode(assembler, rho);
		incrementer.appendCodeR(assembler, rho);
		assembler.append(pop);
		assembler.append(jump, A);
		assembler.labelNextInstruction(B);
	}
	@Override
	public int maxStackSize(AddressEnvironment rho)
	{
		return Math.max(initializer.maxStackSizeR(rho), Math.max(condition.maxStackSizeR(rho), Math.max(incrementer.maxStackSizeR(rho),
				body.maxStackSize(rho))));
	}

	public Expression getInitializer()
	{
		return initializer;
	}
	public Expression getCondition()
	{
		return condition;
	}
	public Expression getIncrementer()
	{
		return incrementer;
	}
	public Statement getBody()
	{
		return body;
	}
}
