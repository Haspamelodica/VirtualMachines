package net.haspamelodica.minic.model;

import java.util.List;

import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.statements.Statement;
import net.haspamelodica.minic.model.types.Type;

public class Function
{
	private final String					name;
	private final List<Parameter>			formalParameters;
	private final List<VariableDeclaration>	localVariables;
	private final List<Statement>			statements;
	private final Type						returnType;

	public Function(String name, List<Parameter> formalParameters, List<VariableDeclaration> localVariables, List<Statement> statements, Type returnType)
	{
		this.name = name;
		this.formalParameters = formalParameters;
		this.localVariables = localVariables;
		this.statements = statements;
		this.returnType = returnType;
	}

	public int localVariableSize()
	{
		return localVariables.stream().mapToInt(VariableDeclaration::size).sum();
	}
	public int maxStackSize(AddressEnvironment rho)
	{
		return statements.stream().mapToInt(s -> s.maxStackSize(rho)).max().orElse(0);
	}

	public String getName()
	{
		return name;
	}
	public List<Parameter> getFormalParameters()
	{
		return formalParameters;
	}
	public List<VariableDeclaration> getLocalVariables()
	{
		return localVariables;
	}
	public List<Statement> getStatements()
	{
		return statements;
	}
	public Type getReturnType()
	{
		return returnType;
	}
}
