package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.slide;

import java.util.HashSet;
import java.util.Set;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class Let implements Expression
{
	private final String		name;
	private final Expression	variableValue;
	private final Expression	resultValue;

	public Let(String name, Expression variableValue, Expression resultValue)
	{
		this.name = name;
		this.variableValue = variableValue;
		this.resultValue = resultValue;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		Set<String> freeVariables = new HashSet<>();
		freeVariables.addAll(resultValue.getFreeVariables());
		//name is not free in resultValue...
		freeVariables.remove(name);
		//...but is free in variableValue.
		freeVariables.addAll(variableValue.getFreeVariables());
		return freeVariables;
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		int numToSlide = appendCodeVWithoutSlide(assembler, rho, stackDistance, cbv);
		assembler.append(slide, numToSlide);
	}
	private int appendCodeVWithoutSlide(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		variableValue.appendCodeCOrV(assembler, rho, stackDistance, cbv);

		AddressEnvironment rhoInner = rho.withLocalVariable(name, stackDistance + 1);
		int stackDistanceInner = stackDistance + 1;

		//Optimization for nested Lets
		if(resultValue instanceof Let)
			return 1 + ((Let) resultValue).appendCodeVWithoutSlide(assembler, rhoInner, stackDistanceInner, cbv);

		resultValue.appendCodeV(assembler, rhoInner, stackDistanceInner, cbv);
		return 1;
	}

	public String getName()
	{
		return name;
	}
	public Expression getVariableValue()
	{
		return variableValue;
	}
	public Expression getResultValue()
	{
		return resultValue;
	}
}
