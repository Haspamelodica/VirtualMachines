package net.haspamelodica.mama.model.debugging;

import java.util.List;
import java.util.Set;

import net.haspamelodica.mama.model.MaMaProgram;

public class MaMaProgramForDebugging extends MaMaProgram
{
	private final List<InstructionForDebugging>	instructionsForDebugging;
	private final Set<DebuggingLabel>			labels;

	public MaMaProgramForDebugging(List<InstructionForDebugging> instructions, Set<DebuggingLabel> labels)
	{
		//copyOf to avoid problems with generics
		super(List.copyOf(instructions));
		//yes, if instructions changes while this executes, problems will arise. That is not a big problem.
		this.instructionsForDebugging = List.copyOf(instructions);
		this.labels = Set.copyOf(labels);
	}

	public List<InstructionForDebugging> getInstructionsForDebugging()
	{
		return instructionsForDebugging;
	}
	public Set<DebuggingLabel> getLabels()
	{
		return labels;
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		for(int addr = 0; addr < instructionsForDebugging.size(); addr ++)
		{
			appendLabelsForAddr(result, addr);
			result.append(String.format("%4d", addr) + "\t" + instructionsForDebugging.get(addr) + "\n");
		}
		appendLabelsForAddr(result, instructionsForDebugging.size());

		//remove last \n
		result.setLength(result.length() - 1);
		return result.toString();
	}

	private void appendLabelsForAddr(StringBuilder result, int size)
	{
		labels.stream().filter(l -> l.getTarget() == size).forEach(l -> result.append(l.getName() + ":\n"));
	}
}
