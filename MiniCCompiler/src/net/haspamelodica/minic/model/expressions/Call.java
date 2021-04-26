package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.call;
import static net.haspamelodica.cma.model.Opcode.loadc;
import static net.haspamelodica.cma.model.Opcode.mark;
import static net.haspamelodica.cma.model.Opcode.slide;

import java.util.List;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.FunctionType;
import net.haspamelodica.minic.model.types.PointerType;
import net.haspamelodica.minic.model.types.Type;

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
	public Type getType(AddressEnvironment rho, boolean check)
	{
		if(check)
			arguments.forEach(e -> e.getType(rho, check));
		Type calledFunctionType = calledFunction.getType(rho, check);
		if(calledFunctionType instanceof FunctionType)
			return ((FunctionType) calledFunctionType).getReturnType();
		if(calledFunctionType instanceof PointerType)
		{
			Type referencedType = ((PointerType) calledFunctionType).getReferencedType();
			if(referencedType instanceof FunctionType)
				return ((FunctionType) referencedType).getReturnType();
		}
		throw new CompilerException("Call to a type that is neither a function nor a function pointer");
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
	public int maxStackSizeR(AddressEnvironment rho)
	{
		int maxStackSizeDuringArgs = 0;
		int argsStackSize = arguments.size() == 0 ? 1 : 0; //if there are no arguments, we still allocate space for the return value
		for(int i = arguments.size() - 1; i >= 0; i --)
		{
			maxStackSizeDuringArgs = Math.max(maxStackSizeDuringArgs, argsStackSize + arguments.get(i).maxStackSizeR(rho));
			argsStackSize += arguments.get(i).getType(rho, false).size();
		}
		return Math.max(maxStackSizeDuringArgs, argsStackSize + 2 + calledFunction.maxStackSizeR(rho));
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
