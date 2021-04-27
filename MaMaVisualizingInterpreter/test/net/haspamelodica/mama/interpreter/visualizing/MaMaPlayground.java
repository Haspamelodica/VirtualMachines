package net.haspamelodica.mama.interpreter.visualizing;

import static net.haspamelodica.mama.model.Opcode.loadc;
import static net.haspamelodica.mama.model.Opcode.mkbasic;
import static net.haspamelodica.mama.model.Opcode.mkvec;

import java.util.List;

import net.haspamelodica.mama.model.Instruction;
import net.haspamelodica.mama.model.MaMaProgram;

public class MaMaPlayground
{
	public static void main(String[] args)
	{
		MaMaProgram program = new MaMaProgram(List.of(
				new Instruction(loadc, 123), new Instruction(mkbasic),
				new Instruction(loadc, 456), new Instruction(mkbasic),
				new Instruction(loadc, 789), new Instruction(mkbasic),
				new Instruction(mkvec, 3)));
		MaMaVisualizer visualizer = new MaMaVisualizer(program);
		visualizer.run();
	}
}
