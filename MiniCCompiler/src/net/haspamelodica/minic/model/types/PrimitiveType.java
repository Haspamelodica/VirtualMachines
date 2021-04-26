package net.haspamelodica.minic.model.types;

public enum PrimitiveType implements Type
{
	int_("int");

	private final String typeName;

	private PrimitiveType(String typeName)
	{
		this.typeName = typeName;
	}

	@Override
	public int size()
	{
		return 1;
	}

	public String getTypeName()
	{
		return typeName;
	}
}
