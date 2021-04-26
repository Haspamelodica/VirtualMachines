package net.haspamelodica.minic.model;

import java.util.List;

public class MiniCProgram
{
	private final List<Function> functions;

	public MiniCProgram(List<Function> functions)
	{
		this.functions = List.copyOf(functions);
	}

	public List<Function> getFunctions()
	{
		return functions;
	}
}
