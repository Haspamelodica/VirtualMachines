package net.haspamelodica.puf.model.expressions;

import static net.haspamelodica.mama.model.Opcode.alloc;
import static net.haspamelodica.mama.model.Opcode.rewrite;
import static net.haspamelodica.mama.model.Opcode.slide;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.haspamelodica.puf.compiler.Assembler;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;

public class Letrec implements Expression
{
	private final List<VariableDeclaration>	variableDeclarations;
	private final Expression				body;

	public Letrec(List<VariableDeclaration> variableDeclarations, Expression body)
	{
		this.variableDeclarations = List.copyOf(variableDeclarations);
		this.body = body;
	}

	@Override
	public Set<String> getFreeVariables()
	{
		Set<String> freeVariables = variableDeclarations.stream().map(VariableDeclaration::getValue).map(Expression::getFreeVariables)
				.flatMap(Set::stream).collect(Collectors.toCollection(HashSet::new));
		freeVariables.addAll(body.getFreeVariables());
		variableDeclarations.stream().map(VariableDeclaration::getName).forEach(freeVariables::remove);
		return freeVariables;
	}
	@Override
	public void appendCodeV(Assembler assembler, AddressEnvironment rho, int stackDistance, boolean cbv)
	{
		int numberOfVariables = variableDeclarations.size();
		int stackDistanceInner = stackDistance + numberOfVariables;
		AddressEnvironment rhoInner = rho;
		for(int i = 0; i < numberOfVariables; i ++)
			rhoInner = rhoInner.withLocalVariable(variableDeclarations.get(i).getName(), stackDistance + i + 1);

		assembler.append(alloc, numberOfVariables);
		for(int iFromSize = numberOfVariables, iFromZero = 0; iFromSize > 0; iFromSize --, iFromZero ++)
		{
			variableDeclarations.get(iFromZero).getValue().appendCodeCOrV(assembler, rhoInner, stackDistanceInner, cbv);
			assembler.append(rewrite, iFromSize);
		}
		body.appendCodeV(assembler, rhoInner, stackDistanceInner, cbv);
		assembler.append(slide, numberOfVariables);
	}

	public List<VariableDeclaration> getVariableDeclarations()
	{
		return variableDeclarations;
	}
	public Expression getBody()
	{
		return body;
	}

	public static class VariableDeclaration
	{
		private final String		name;
		private final Expression	value;

		public VariableDeclaration(String name, Expression value)
		{
			this.name = name;
			this.value = value;
		}

		public String getName()
		{
			return name;
		}
		public Expression getValue()
		{
			return value;
		}
	}
}
