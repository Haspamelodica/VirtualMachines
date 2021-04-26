package net.haspamelodica.minic.model.types;

import java.util.List;

public class FunctionType implements Type
{
	private final List<Type>	parameterTypes;
	private final Type			returnType;

	public FunctionType(List<Type> parameterTypes, Type returnType)
	{
		this.parameterTypes = List.copyOf(parameterTypes);
		this.returnType = returnType;
	}

	@Override
	public int size()
	{
		throw new IllegalArgumentException("A function type does not have a size");
	}

	public List<Type> getParameterTypes()
	{
		return parameterTypes;
	}
	public Type getReturnType()
	{
		return returnType;
	}
}
