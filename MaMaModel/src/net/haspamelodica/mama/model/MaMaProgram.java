package net.haspamelodica.mama.model;

import java.util.List;
import java.util.stream.Collectors;

public class MaMaProgram
{
	private final List<Instruction> instructions;

	public MaMaProgram(List<Instruction> instructions)
	{
		this.instructions = List.copyOf(instructions);
	}

	public int getLength()
	{
		return instructions.size();
	}
	public Instruction getInstructionAt(int codePointer)
	{
		return instructions.get(codePointer);
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
