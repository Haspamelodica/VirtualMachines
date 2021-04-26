package net.haspamelodica.minic.compiler;

import static net.haspamelodica.minic.model.expressions.Binary.BinaryOp.LESS_OR_EQUAL;
import static net.haspamelodica.minic.model.expressions.Binary.BinaryOp.MINUS;
import static net.haspamelodica.minic.model.expressions.Binary.BinaryOp.PLUS;
import static net.haspamelodica.minic.model.expressions.Binary.BinaryOp.TIMES;
import static net.haspamelodica.minic.model.types.PrimitiveType.int_;

import java.util.List;

import net.haspamelodica.cma.model.debugging.CMaProgramForDebugging;
import net.haspamelodica.minic.model.Function;
import net.haspamelodica.minic.model.MiniCProgram;
import net.haspamelodica.minic.model.Parameter;
import net.haspamelodica.minic.model.VariableDeclaration;
import net.haspamelodica.minic.model.expressions.Assignment;
import net.haspamelodica.minic.model.expressions.Binary;
import net.haspamelodica.minic.model.expressions.Call;
import net.haspamelodica.minic.model.expressions.Constant;
import net.haspamelodica.minic.model.expressions.Variable;
import net.haspamelodica.minic.model.statements.ExpressionStatement;
import net.haspamelodica.minic.model.statements.IfElse;
import net.haspamelodica.minic.model.statements.Return;

public class TestMiniCCompiler
{
	public static void main(String[] args)
	{
		MiniCProgram program = new MiniCProgram(List.of(), List.of(
				new Function("fac", List.of(new Parameter(int_, "x")), List.of(), List.of(
						new IfElse(new Binary(new Variable("x"), LESS_OR_EQUAL, new Constant(0)), new Return(new Constant(1)),
								new Return(new Binary(new Variable("x"), TIMES, new Call(new Variable("fac"),
										List.of(new Binary(new Variable("x"), MINUS, new Constant(1)))))))),
						int_),
				new Function("main", List.of(), List.of(new VariableDeclaration(int_, List.of("n"))), List.of(
						new ExpressionStatement(new Assignment(new Variable("n"), new Binary(new Call(new Variable("fac"),
								List.of(new Constant(2))), PLUS, new Call(new Variable("fac"), List.of(new Constant(1))))))),
						int_)));
		CMaProgramForDebugging assembly = MiniCCompiler.compile(program);
		System.out.println(assembly);
	}
}
