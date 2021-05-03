package net.haspamelodica.puf.compiler.environment;

import static net.haspamelodica.mama.model.Opcode.*;
import net.haspamelodica.puf.compiler.Assembler;

public class TaggedVariable
{
	private final Tag	tag;
	private final int	offsetOrAddress;

	public TaggedVariable(Tag tag, int offsetOrAddress)
	{
		this.tag = tag;
		this.offsetOrAddress = offsetOrAddress;
	}

	public void appendGetvar(Assembler assembler, int stackDistance)
	{
		switch(tag)
		{
			case LOCAL:
				assembler.append(pushloc, stackDistance - offsetOrAddress);
				break;
			case GLOBAL:
				assembler.append(pushglob, offsetOrAddress);
				break;
			default:
				throw new IllegalArgumentException("Unknown enum constant: " + tag);
		}
	}

	public Tag getTag()
	{
		return tag;
	}
	public int getOffsetOrAddress()
	{
		return offsetOrAddress;
	}
}