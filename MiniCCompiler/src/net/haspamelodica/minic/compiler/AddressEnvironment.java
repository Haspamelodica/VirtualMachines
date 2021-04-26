package net.haspamelodica.minic.compiler;

import java.util.HashMap;
import java.util.Map;

import net.haspamelodica.minic.compiler.Assembler.Label;

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

	public AddressEnvironment withLocalVariable(String name, int address)
	{
		return withVariable(name, Tag.LOCAL, address);
	}
	public AddressEnvironment withLocalVariable(String name, Label address)
	{
		return withVariable(name, Tag.LOCAL, address);
	}
	public AddressEnvironment withGlobalVariable(String name, int address)
	{
		return withVariable(name, Tag.GLOBAL, address);
	}
	public AddressEnvironment withGlobalVariable(String name, Label address)
	{
		return withVariable(name, Tag.GLOBAL, address);
	}
	public AddressEnvironment withVariable(String name, Tag tag, int address)
	{
		return withVariable(name, new TaggedVariable(tag, address));
	}
	public AddressEnvironment withVariable(String name, Tag tag, Label address)
	{
		return withVariable(name, new TaggedVariable(tag, address));
	}
	public AddressEnvironment withVariable(String name, TaggedVariable variable)
	{
		Map<String, TaggedVariable> newVariables = new HashMap<>(variables);
		newVariables.put(name, variable);
		return new AddressEnvironment(newVariables);
	}

	public static enum Tag
	{
		GLOBAL,
		LOCAL;
	}
}
