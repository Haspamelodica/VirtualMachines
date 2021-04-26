package net.haspamelodica.minic.compiler.environment;

import java.util.HashMap;
import java.util.Map;

import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.model.types.Type;

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

	public AddressEnvironment withLocalVariable(String name, Type type, int address)
	{
		return withVariable(name, Tag.LOCAL, type, address);
	}
	public AddressEnvironment withLocalVariable(String name, Type type, Label address)
	{
		return withVariable(name, Tag.LOCAL, type, address);
	}
	public AddressEnvironment withGlobalVariable(String name, Type type, int address)
	{
		return withVariable(name, Tag.GLOBAL, type, address);
	}
	public AddressEnvironment withGlobalVariable(String name, Type type, Label address)
	{
		return withVariable(name, Tag.GLOBAL, type, address);
	}
	public AddressEnvironment withVariable(String name, Tag tag, Type type, int address)
	{
		return withVariable(name, new TaggedVariable(tag, type, address));
	}
	public AddressEnvironment withVariable(String name, Tag tag, Type type, Label address)
	{
		return withVariable(name, new TaggedVariable(tag, type, address));
	}
	public AddressEnvironment withVariable(String name, TaggedVariable variable)
	{
		Map<String, TaggedVariable> newVariables = new HashMap<>(variables);
		newVariables.put(name, variable);
		return new AddressEnvironment(newVariables);
	}
}
