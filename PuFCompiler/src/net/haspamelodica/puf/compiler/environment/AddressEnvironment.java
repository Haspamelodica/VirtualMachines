package net.haspamelodica.puf.compiler.environment;

import java.util.HashMap;
import java.util.Map;

import net.haspamelodica.puf.compiler.Assembler;

public class AddressEnvironment
{
	private final Map<String, TaggedVariable> variables;

	public AddressEnvironment(Map<String, TaggedVariable> variables)
	{
		this.variables = Map.copyOf(variables);
	}
	public AddressEnvironment()
	{
		this.variables = Map.of();
	}

	public TaggedVariable getVariable(String name)
	{
		return variables.get(name);
	}
	public void appendGetvar(Assembler assembler, String name, int stackDistance)
	{
		getVariable(name).appendGetvar(assembler, stackDistance);
	}

	public AddressEnvironment withLocalVariable(String name, int offset)
	{
		return withVariable(name, Tag.LOCAL, offset);
	}
	public AddressEnvironment withGlobalVariable(String name, int address)
	{
		return withVariable(name, Tag.GLOBAL, address);
	}
	public AddressEnvironment withVariable(String name, Tag tag, int offsetOrAddress)
	{
		return withVariable(name, new TaggedVariable(tag, offsetOrAddress));
	}
	public AddressEnvironment withVariable(String name, TaggedVariable variable)
	{
		Map<String, TaggedVariable> newVariables = new HashMap<>(variables);
		newVariables.put(name, variable);
		return new AddressEnvironment(newVariables);
	}
}
