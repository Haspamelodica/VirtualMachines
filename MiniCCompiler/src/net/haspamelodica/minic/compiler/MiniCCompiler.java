package net.haspamelodica.minic.compiler;

import static net.haspamelodica.cma.model.Opcode.alloc;
import static net.haspamelodica.cma.model.Opcode.call;
import static net.haspamelodica.cma.model.Opcode.enter;
import static net.haspamelodica.cma.model.Opcode.halt;
import static net.haspamelodica.cma.model.Opcode.loadc;
import static net.haspamelodica.cma.model.Opcode.mark;
import static net.haspamelodica.cma.model.Opcode.return_;
import static net.haspamelodica.cma.model.Opcode.slide;

import java.util.List;
import java.util.stream.Collectors;

import net.haspamelodica.cma.model.debugging.CMaProgramForDebugging;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.model.Function;
import net.haspamelodica.minic.model.MiniCProgram;
import net.haspamelodica.minic.model.Parameter;
import net.haspamelodica.minic.model.VariableDeclaration;
import net.haspamelodica.minic.model.statements.Statement;
import net.haspamelodica.minic.model.types.FunctionType;
import net.haspamelodica.minic.model.types.Type;

public class MiniCCompiler
{
	private final MiniCProgram	minicProgram;
	private final Assembler		assembler;

	private AddressEnvironment rho;

	private MiniCCompiler(MiniCProgram minicProgram)
	{
		this.minicProgram = minicProgram;
		this.assembler = new Assembler();
		this.rho = new AddressEnvironment();
	}

	private CMaProgramForDebugging getCMaProgram()
	{
		return assembler.assemble();
	}

	private void compile()
	{
		int globalVariablesSize = compileGlobalVariables();

		Label mainLabel = assembler.createLabel("_main");
		assembler.append(enter, globalVariablesSize + 4);
		assembler.append(alloc, globalVariablesSize + 1);
		assembler.append(mark);
		assembler.append(loadc, mainLabel);
		assembler.append(call);
		assembler.append(slide, globalVariablesSize);
		assembler.append(halt);

		for(Function function : minicProgram.getFunctions())
		{
			Label functionLabel = function.getName().equals("main") ? mainLabel : assembler.createLabel("_" + function.getName());
			List<Type> parameterTypes = function.getFormalParameters().stream().map(Parameter::getType).collect(Collectors.toList());
			rho = rho.withGlobalVariable(function.getName(), new FunctionType(parameterTypes, function.getReturnType()), functionLabel);
			assembler.labelNextInstruction(functionLabel);
			compile(function, rho);
		}
	}
	private int compileGlobalVariables()
	{
		int nextVariableOffset = 1;
		for(VariableDeclaration globalVariable : minicProgram.getGlobalVariables())
		{
			int sizePerName = globalVariable.getType().size();
			for(String name : globalVariable.getNames())
			{
				rho = rho.withGlobalVariable(name, globalVariable.getType(), nextVariableOffset);
				nextVariableOffset += sizePerName;
			}
		}
		return nextVariableOffset - 1;
	}
	private void compile(Function function, AddressEnvironment rho)
	{
		AddressEnvironment rhoInFunction = calculateRhoInFunction(function, rho);

		assembler.append(enter, function.localVariableSize() + function.maxStackSize(rhoInFunction));
		assembler.append(alloc, function.localVariableSize());
		for(Statement statement : function.getStatements())
			statement.appendCode(assembler, rhoInFunction);
		assembler.append(return_);
	}

	private AddressEnvironment calculateRhoInFunction(Function function, AddressEnvironment rho)
	{
		AddressEnvironment rhoInFunction = rho;

		int lastParameterOffset = -2;
		for(Parameter parameter : function.getFormalParameters())
		{
			lastParameterOffset -= parameter.getType().size();
			rhoInFunction = rhoInFunction.withLocalVariable(parameter.getName(), parameter.getType(), lastParameterOffset);
		}

		int nextVariableOffset = 1;
		for(VariableDeclaration variableDeclaration : function.getLocalVariables())
		{
			int size = variableDeclaration.getType().size();
			for(String name : variableDeclaration.getNames())
			{
				rhoInFunction = rhoInFunction.withLocalVariable(name, variableDeclaration.getType(), nextVariableOffset);
				nextVariableOffset += size;
			}
		}

		return rhoInFunction;
	}

	public static CMaProgramForDebugging compile(MiniCProgram minicProgram)
	{
		MiniCCompiler compiler = new MiniCCompiler(minicProgram);
		compiler.compile();
		return compiler.getCMaProgram();
	}
}
