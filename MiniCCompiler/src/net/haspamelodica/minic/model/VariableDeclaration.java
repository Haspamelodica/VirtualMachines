package net.haspamelodica.minic.model;

import java.util.List;

import net.haspamelodica.minic.model.types.Type;

public class VariableDeclaration
{
	private final Type			type;
	private final List<String>	names;

	public int size()
	{
		return type.size() * names.size();
	}

	public VariableDeclaration(Type type, List<String> names)
	{
		this.type = type;
		this.names = List.copyOf(names);
	}

	public Type getType()
	{
		return type;
	}
	public List<String> getNames()
	{
		return names;
	}
}
