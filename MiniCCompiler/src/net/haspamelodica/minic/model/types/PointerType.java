package net.haspamelodica.minic.model.types;

public class PointerType implements Type
{
	private final Type referencedType;

	public PointerType(Type referencedType)
	{
		this.referencedType = referencedType;
	}

	@Override
	public boolean isAssignableTo(Type other)
	{
		if(!(other instanceof PointerType))
			return false;
		return referencedType.isAssignableTo(((PointerType) other).referencedType);
	}
	@Override
	public int size()
	{
		return 1;
	}

	public Type getReferencedType()
	{
		return referencedType;
	}
}