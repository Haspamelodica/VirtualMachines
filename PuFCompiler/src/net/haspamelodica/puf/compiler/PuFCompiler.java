package net.haspamelodica.puf.compiler;

import static net.haspamelodica.mama.model.Opcode.halt;

import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;
import net.haspamelodica.puf.compiler.environment.AddressEnvironment;
import net.haspamelodica.puf.model.PuFProgram;

public class PuFCompiler
{
	public static MaMaProgramForDebugging compile(PuFProgram program, boolean cbv)
	{
		Assembler assembler = new Assembler();
		program.getOutermostExpression().appendCodeV(assembler, new AddressEnvironment(), 0, cbv);
		assembler.append(halt);
		return assembler.assemble();
	}

	private PuFCompiler()
	{}
}
