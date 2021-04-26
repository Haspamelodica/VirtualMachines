package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.jump;
import static net.haspamelodica.cma.model.Opcode.jumpz;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.expressions.Expression;

public class DoWhile implements Statement
{
	private final Expression	condition;
	private final Statement		body;

	public DoWhile(Expression condition, Statement body)
	{
		this.condition = condition;
		this.body = body;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		Label loop = assembler.labelNextInstruction("loop");
		body.appendCode(assembler, rho);
		condition.appendCodeR(assembler, rho);
		Label end = assembler.createLabel("end");
		assembler.append(jumpz, end);
		assembler.append(jump, loop);
		assembler.labelNextInstruction(end);
	}
	@Override
	public int maxStackSize(AddressEnvironment rho)
	{
		return Math.max(body.maxStackSize(rho), condition.maxStackSizeR(rho));
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
