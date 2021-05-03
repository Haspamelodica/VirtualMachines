package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.apply;
import static net.haspamelodica.mama.model.Opcode.mark;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.Assembler.Label;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class FunctionApplication implements Expression
{
	private final Expression		calledFunction;
	private final List<Expression>	arguments;

	public FunctionApplication(Expression calledFunction, List<Expression> arguments)
	{
		this.calledFunction = calledFunction;
		this.arguments = List.copyOf(arguments);
	}

	@Override
	public Set<String> getFreeVariables()
	{
		return Stream.concat(Stream.of(calledFunction), arguments.stream())
				.map(Expression::getFreeVariables)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		Label A = assembler.createLabel("A");
		assembler.append(mark, A);
		for(int indexFromSize = arguments.size() - 1, indexFrom0 = 0; indexFromSize >= 0; indexFromSize --, indexFrom0 ++)
			arguments.get(indexFromSize).appendCodeCOrV(assembler, rho, stackDistance + 3 + indexFrom0, cbv);
		calledFunction.appendCodeV(assembler, rho, stackDistance + 3 + arguments.size(), cbv);
		assembler.append(apply);
		assembler.labelNextInstruction(A);
	}

	public Expression getCalledFunction()
	{
		return calledFunction;
	}
	public List<Expression> getArguments()
	{
		return arguments;
	}
}
