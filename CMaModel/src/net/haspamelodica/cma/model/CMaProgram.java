package net.haspamelodica.cma.model;

import java.util.List;

public class CMaProgram
{
	private final List<Instruction> instructions;

	public CMaProgram(List<Instruction> instructions)
	{
		this.instructions = List.copyOf(instructions);
	}

	public List<Instruction> getInstructions()
	{
		return instructions;
	}
}
