package net.haspamelodica.cma.model;

public class Instruction
{
	private final Opcode	opcode;
	private final int		immediate;

	public Instruction(Opcode opcode)
	{
		if(opcode.hasImmediate())
			throw new IllegalArgumentException(opcode.getOpcodeName() + " has an immediate, but none was given");
		this.opcode = opcode;
		this.immediate = -1;
	}
	public Instruction(Opcode opcode, int immediate)
	{
		if(!opcode.hasImmediate())
			throw new IllegalArgumentException(opcode.getOpcodeName() + " has no immediate, but one was given");
		this.opcode = opcode;
		this.immediate = immediate;
	}

	public Opcode getOpcode()
	{
		return opcode;
	}
	public int getImmediate()
	{
		if(!opcode.hasImmediate())
			throw new IllegalArgumentException(opcode.getOpcodeName() + " has no immediate");
		return immediate;
	}
}
