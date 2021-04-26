package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.store;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.Type;

public interface Expression
{
	public Type getType(AddressEnvironment rho, boolean check);

	public void appendCodeR(Assembler assembler, AddressEnvironment rho);
	public default void appendCodeL(Assembler assembler, AddressEnvironment rho)
	{
		throw new CompilerException("This expression does not have an L-value");
	}
	public default void appendCodeStore(Assembler assembler, AddressEnvironment rho)
	{
		appendCodeL(assembler, rho);
		//TODO what about types with size != 1?
		assembler.append(store);
	}

	public int maxStackSizeR(AddressEnvironment rho);
	public default int maxStackSizeL()
	{
		throw new CompilerException("This expression does not have an L-value");
	}
	public default int maxStackSizeStore()
	{
		return maxStackSizeL();
	}
}
