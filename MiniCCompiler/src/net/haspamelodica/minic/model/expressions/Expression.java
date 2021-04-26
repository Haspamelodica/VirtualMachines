package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.store;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;

public interface Expression
{
	public void appendCodeR(Assembler assembler, AddressEnvironment rho);
	public default void appendCodeL(Assembler assembler, AddressEnvironment rho)
	{
		throw new CompilerException("This expression does not have an L-value");
	}
	public default void appendCodeStore(Assembler assembler, AddressEnvironment rho)
	{
		appendCodeL(assembler, rho);
		assembler.append(store);
	}

	public int maxStackSizeR();
	public default int maxStackSizeL()
	{
		throw new CompilerException("This expression does not have an L-value");
	}
	public default int maxStackSizeStore()
	{
		return maxStackSizeL();
	}
}
