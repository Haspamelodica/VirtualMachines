package net.haspamelodica.minic.model;

import java.util.List;

public class MiniCProgram
{
	private final List<VariableDeclaration>	globalVariables;
	private final List<Function>			functions;

	public MiniCProgram(List<VariableDeclaration> globalVariables, List<Function> functions)
	{
		this.globalVariables = globalVariables;
		this.functions = functions;
	}

	public List<VariableDeclaration> getGlobalVariables()
	{
		return globalVariables;
	}
	public List<Function> getFunctions()
	{
		return functions;
	}
}
