package net.haspamelodica.minic.model.statements;

import static net.haspamelodica.cma.model.Opcode.return_;
import static net.haspamelodica.cma.model.Opcode.storer;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.model.expressions.Expression;

public class Return implements Statement
{
	private final Expression returnValue;

	public Return(Expression returnValue)
	{
		this.returnValue = returnValue;
	}

	@Override
	public void appendCode(Assembler assembler, AddressEnvironment rho)
	{
		returnValue.appendCodeR(assembler, rho);
		assembler.append(storer, -3);
		assembler.append(return_);
	}
	@Override
	public int maxStackSize()
	{
		return returnValue.maxStackSizeR();
	}

	public Expression getReturnValue()
	{
		return returnValue;
	}
}
