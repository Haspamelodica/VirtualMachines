package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.jump;
import static net.haspamelodica.mama.model.Opcode.mkfunval;
import static net.haspamelodica.mama.model.Opcode.mkvec;
import static net.haspamelodica.mama.model.Opcode.return_;
import static net.haspamelodica.mama.model.Opcode.targ;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.Assembler.Label;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class FunctionDefinition implements Expression
{
	private final List<String>	parameterNames;
	private final Expression	body;
	public FunctionDefinition(List<String> parameterNames, Expression body)
	{
		this.parameterNames = List.copyOf(parameterNames);
		this.body = body;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		Set<String> freeVariables = new HashSet<>(body.getFreeVariables());
		freeVariables.removeAll(parameterNames);
		return freeVariables;
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		AddressEnvironment rhoInner = rho;

		//add parameters to rho
		for(int parameterIndex = 0; parameterIndex < parameterNames.size(); parameterIndex ++)
			rhoInner = rhoInner.withLocalVariable(parameterNames.get(parameterIndex), -parameterIndex);

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
		Label A = assembler.createLabel("A");

		//create function object
		assembler.append(mkfunval, A);
		Label B = assembler.createLabel("B");
		assembler.append(jump, B);

		//function body code
		assembler.labelNextInstruction(A);
		assembler.append(targ, parameterNames.size());
		body.appendCodeV(assembler, rhoInner, 0, cbv);
		assembler.append(return_, parameterNames.size());

		//continue
		assembler.labelNextInstruction(B);
	}

	public List<String> getParameterNames()
	{
		return parameterNames;
	}
	public Expression getBody()
	{
		return body;
	}
}
