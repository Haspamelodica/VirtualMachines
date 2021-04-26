package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.pop;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.expressions.Expression;

public class ExpressionStatement implements Statement
{
	private final Expression expression;

	public ExpressionStatement(Expression expression)
	{
		this.expression = expression;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		expression.appendCodeR(assembler, rho);
		assembler.append(pop);
	}
	@Override
	public int maxStackSize(AddressEnvironment rho)
	{
		return expression.maxStackSizeR(rho);
	}

	public Expression getExpression()
	{
		return expression;
	}
}
