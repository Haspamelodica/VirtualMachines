package net.haspamelodica.minic.compiler;

import static net.haspamelodica.cma.model.Opcode.loada;
import static net.haspamelodica.cma.model.Opcode.loadc;
import static net.haspamelodica.cma.model.Opcode.loadr;
import static net.haspamelodica.cma.model.Opcode.loadrc;
import static net.haspamelodica.cma.model.Opcode.storea;
import static net.haspamelodica.cma.model.Opcode.storer;

import net.haspamelodica.cma.model.Opcode;
import net.haspamelodica.minic.compiler.AddressEnvironment.Tag;
import net.haspamelodica.minic.compiler.Assembler.Label;

public class TaggedVariable
{
	private final Tag	tag;
	private final int	constantAddress;
	private final Label	labelAddress;

	public TaggedVariable(Tag tag, int constantAddress)
	{
		this.tag = tag;
		this.constantAddress = constantAddress;
		this.labelAddress = null;
	}
	public TaggedVariable(Tag tag, Label labelAddress)
	{
		this.tag = tag;
		this.constantAddress = -1;
		this.labelAddress = labelAddress;
	}

	public void appendCodeR(Assembler assembler)
	{
		appendOpcodeWithArgument(assembler, switch(tag)
		{
			case GLOBAL -> loada;
			case LOCAL -> loadr;
		});
	}
	public void appendCodeL(Assembler assembler)
	{
		appendOpcodeWithArgument(assembler, switch(tag)
		{
			case GLOBAL -> loadc;
			case LOCAL -> loadrc;
		});
	}
	public void appendStore(Assembler assembler)
	{
		appendOpcodeWithArgument(assembler, switch(tag)
		{
			case GLOBAL -> storea;
			case LOCAL -> storer;
		});
	}
	private void appendOpcodeWithArgument(Assembler assembler, Opcode opcode)
	{
		if(hasLabelAddress())
			assembler.append(opcode, getLabelAddress());
		else
			assembler.append(opcode, getConstantAddress());
	}

	public Tag getTag()
	{
		return tag;
	}
	public boolean hasLabelAddress()
	{
		return labelAddress != null;
	}
	public int getConstantAddress()
	{
		if(hasLabelAddress())
			throw new IllegalStateException("This tagged variable has a label address");
		return constantAddress;
	}
	public Label getLabelAddress()
	{
		if(!hasLabelAddress())
			throw new IllegalStateException("This tagged variable has a constant address");
		return labelAddress;
	}
}