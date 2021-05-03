package net.haspamelodica.puf.compiler;

import java.util.List;

import net.haspamelodica.puf.compiler.environment.AddressEnvironment;
import net.haspamelodica.puf.model.expressions.Binary;
import net.haspamelodica.puf.model.expressions.Binary.BinaryOp;
import net.haspamelodica.puf.model.expressions.Expression;
import net.haspamelodica.puf.model.expressions.FunctionApplication;
import net.haspamelodica.puf.model.expressions.FunctionDefinition;
import net.haspamelodica.puf.model.expressions.IntConstant;
import net.haspamelodica.puf.model.expressions.Let;
import net.haspamelodica.puf.model.expressions.Variable;

public class PuFCompilerPlayground
{
	public static void main(String[] args)
	{
		Expression letExample = new Let("a", new IntConstant(19),
				new Let("b", new Binary(new Variable("a"), BinaryOp.TIMES, new Variable("a")),
						new Binary(new Variable("a"), BinaryOp.PLUS, new Variable("b"))));
		Assembler assembler = new Assembler();
		letExample.appendCodeV(assembler, new AddressEnvironment(), 0, true);
		System.out.println("--- let example:");
		System.out.println(assembler.assemble());

		Expression functionExample = new FunctionDefinition(List.of("b"), new Binary(new Variable("a"), BinaryOp.PLUS, new Variable("b")));
		assembler = new Assembler();
		functionExample.appendCodeV(assembler, new AddressEnvironment().withLocalVariable("a", 1), 1, false);
		System.out.println("--- function example:");
		System.out.println(assembler.assemble());

		Expression functionApplicationExample = new FunctionApplication(new Variable("f"), List.of(new IntConstant(42)));
		assembler = new Assembler();
		functionApplicationExample.appendCodeV(assembler, new AddressEnvironment().withLocalVariable("f", 2), 2, true);
		System.out.println("--- function application example:");
		System.out.println(assembler.assemble());

		Expression largerExample = new Let("a", new IntConstant(17),
				new Let("f", new FunctionDefinition(List.of("b"), new Binary(new Variable("a"), BinaryOp.PLUS, new Variable("b"))),
						new FunctionApplication(new Variable("f"), List.of(new IntConstant(42)))));
		assembler = new Assembler();
		largerExample.appendCodeV(assembler, new AddressEnvironment(), 0, true);
		System.out.println("--- larger example:");
		System.out.println(assembler.assemble());
	}
}
