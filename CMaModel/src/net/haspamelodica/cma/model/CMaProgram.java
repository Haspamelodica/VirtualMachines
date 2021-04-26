package net.haspamelodica.cma.model;

import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public String toString()
	{
		return instructions.stream().map(Instruction::toString).collect(Collectors.joining("\n"));
	}
}
