package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.getbasic;
import static net.haspamelodica.mama.model.Opcode.jump;
import static net.haspamelodica.mama.model.Opcode.mkclos;
import static net.haspamelodica.mama.model.Opcode.mkvec;
import static net.haspamelodica.mama.model.Opcode.update;

import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.Assembler.Label;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

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
		AddressEnvironment rhoInner = makeGlobalVector(assembler, rho, stackDistance, rho);
		Label A = assembler.createLabel("A");
		assembler.append(mkclos, A);
		Label B = assembler.createLabel("B");
		assembler.append(jump, B);

		assembler.labelNextInstruction(A);
		appendCodeV(assembler, rhoInner, 0, cbv);
		assembler.append(update);

		assembler.labelNextInstruction(B);
	}
	public default void appendCodeCOrV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		if(cbv)
			appendCodeV(assembler, rho, stackDistance, cbv);
		else
			appendCodeC(assembler, rho, stackDistance, cbv);
	}

	public default AddressEnvironment makeGlobalVector(Assembler assembler, AddressEnvironment rho, int stackDistance, AddressEnvironment rhoInner)
	{
		//add global variables to rho; create vector containing global variables
		Set<String> freeVariables = getFreeVariables();
		int globalVariableIndex = 0;
		for(String globalVariable : freeVariables)
		{
			rho.appendGetvar(assembler, globalVariable, stackDistance + globalVariableIndex);
			rhoInner = rhoInner.withGlobalVariable(globalVariable, globalVariableIndex);
			globalVariableIndex ++;
		}
		assembler.append(mkvec, globalVariableIndex);
		return rhoInner;
	}
}
