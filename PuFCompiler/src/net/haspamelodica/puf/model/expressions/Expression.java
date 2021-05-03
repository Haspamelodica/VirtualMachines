package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.getbasic;

import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;
import net.haspamelodica.puf.compiler.exception.CompilerException;

public interface Expression
{
	public Set<String> getFreeVariables();
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv);
	public default void appendCodeB(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		appendCodeV(assembler, rho, stackDistance, cbv);
		assembler.append(getbasic);
	}
	public default void appendCodeC(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		throw new CompilerException("codeC unimplemented");
	}
	public default void appendCodeCOrV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv){
		if(cbv)
			appendCodeV(assembler, rho, stackDistance, cbv);
		else
			appendCodeC(assembler, rho, stackDistance, cbv);
	}
}
