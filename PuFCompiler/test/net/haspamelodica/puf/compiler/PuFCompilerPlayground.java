package net.haspamelodica.puf.compiler;

import java.util.List;

import net.haspamelodica.puf.compiler.environment.AddressEnvironment;
import net.haspamelodica.puf.model.expressions.Binary;
import net.haspamelodica.puf.model.expressions.Conditional;
import net.haspamelodica.puf.model.expressions.Binary.BinaryOp;
import net.haspamelodica.puf.model.expressions.Letrec.VariableDeclaration;
import net.haspamelodica.puf.model.expressions.Expression;
import net.haspamelodica.puf.model.expressions.FunctionApplication;
import net.haspamelodica.puf.model.expressions.FunctionDefinition;
import net.haspamelodica.puf.model.expressions.IntConstant;
import net.haspamelodica.puf.model.expressions.Let;
import net.haspamelodica.puf.model.expressions.Letrec;
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

		Expression letrecExample = new Letrec(List.of(new VariableDeclaration("f", new FunctionDefinition(List.of("x", "y"),
				new Conditional(new Binary(new Variable("y"), BinaryOp.LESS_OR_EQUAL, new IntConstant(1)), new Variable("x"),
						new FunctionApplication(new Variable("f"), List.of(new Binary(new Variable("x"), BinaryOp.TIMES, new Variable("y")),
								new Binary(new Variable("y"), BinaryOp.MINUS, new IntConstant(1)))))))),
				new FunctionApplication(new Variable("f"), List.of(new IntConstant(1))));
		assembler = new Assembler();
		letrecExample.appendCodeV(assembler, new AddressEnvironment(), 0, true);
		System.out.println("--- letrec example:");
		System.out.println(assembler.assemble());
	}
}
