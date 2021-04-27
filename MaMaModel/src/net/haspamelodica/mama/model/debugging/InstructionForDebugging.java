package net.haspamelodica.mama.model.debugging;

import net.haspamelodica.mama.model.Instruction;
import net.haspamelodica.mama.model.Opcode;

public class InstructionForDebugging extends Instruction
{
	private final DebuggingLabel labelArgument;

	public InstructionForDebugging(Opcode opcode)
	{
		super(opcode);
		this.labelArgument = null;
	}
	public InstructionForDebugging(Opcode opcode, int immediate)
	{
		super(opcode, immediate);
		this.labelArgument = null;
	}
	public InstructionForDebugging(Opcode opcode, DebuggingLabel labelArgument)
	{
		super(opcode, labelArgument.getTarget());
		this.labelArgument = labelArgument;
	}

	public boolean hasLabelArgument()
	{
		return labelArgument != null;
	}
	public DebuggingLabel getLabelArgument()
	{
		if(labelArgument == null)
			throw new IllegalStateException("This instruction's argument is not a label");
		return labelArgument;
	}

	@Override
	public String toString()
	{
		return getOpcode() + (getOpcode().hasImmediate() ? " " + (hasLabelArgument() ? getLabelArgument() : getImmediate()) : "");
	}
}
