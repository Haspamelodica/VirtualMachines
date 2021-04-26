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
	public boolean isAssignableTo(Type other)
	{
		if(!(other instanceof FunctionType))
			return false;
		FunctionType otherF = (FunctionType) other;
		if(!returnType.isAssignableTo(otherF.getReturnType()))
			return false;
		if(parameterTypes.size() != otherF.parameterTypes.size())
			return false;
		for(int i = 0; i < parameterTypes.size(); i ++)
			if(!otherF.parameterTypes.get(i).isAssignableTo(parameterTypes.get(i)))
				return false;
		return true;
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
