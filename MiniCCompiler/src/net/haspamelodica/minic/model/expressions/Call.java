package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.call;
import static net.haspamelodica.cma.model.Opcode.loadc;
import static net.haspamelodica.cma.model.Opcode.mark;
import static net.haspamelodica.cma.model.Opcode.slide;

import java.util.List;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public class Call implements Expression
{
	private final Expression		calledFunction;
	private final List<Expression>	arguments;

	public Call(Expression calledFunction, List<Expression> arguments)
	{
		this.calledFunction = calledFunction;
		this.arguments = List.copyOf(arguments);
	}

	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		if(arguments.size() == 0)
			assembler.append(loadc, 0);
		for(int i = arguments.size() - 1; i >= 0; i --)
			arguments.get(i).appendCodeR(assembler, rho);
		assembler.append(mark);
		calledFunction.appendCodeR(assembler, rho);
		assembler.append(call);
		if(arguments.size() != 0)
			assembler.append(slide, arguments.size() - 1);
	}
	@Override
	public int maxStackSizeR()
	{
		int maxArgsStackSize = arguments.stream().mapToInt(Expression::maxStackSizeR).max().orElse(1);
		return maxArgsStackSize + 2 + Math.max(calledFunction.maxStackSizeR(), 1 + 1);//TODO is this correct?
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
