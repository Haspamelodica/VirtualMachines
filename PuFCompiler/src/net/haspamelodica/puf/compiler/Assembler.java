package net.haspamelodica.puf.compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.haspamelodica.mama.model.Opcode;
import net.haspamelodica.mama.model.debugging.DebuggingLabel;
import net.haspamelodica.mama.model.debugging.InstructionForDebugging;
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;

public class Assembler
{

	private final List<InstructionForDebugging>	instructions;
	private final List<LabelArgInstruction>		labelArgInstructions;
	private final Set<Label>					allLabels;
	private final Set<String>					usedLabelNames;

	public Assembler()
	{
		this.instructions = new ArrayList<>();
		this.labelArgInstructions = new ArrayList<>();
		this.allLabels = new HashSet<>();
		this.usedLabelNames = new HashSet<>();
	}

	public void append(InstructionForDebugging instruction)
	{
		instructions.add(instruction);
	}
	public void append(Opcode opcode)
	{
		append(new InstructionForDebugging(opcode));
	}
	public void append(Opcode opcode, int argument)
	{
		append(new InstructionForDebugging(opcode, argument));
	}
	public void append(Opcode opcode, Label argument)
	{
		//fail-fast
		if(!opcode.hasImmediate())
			throw new IllegalArgumentException(opcode.getOpcodeName() + " doesn't have an immediate");
		int insertionPoint = getNextInstructionAddress();
		instructions.add(null);
		labelArgInstructions.add(new LabelArgInstruction(insertionPoint, opcode, argument));
	}

	public Label labelNextInstruction(String name)
	{
		Label label = createLabel(name);
		labelNextInstruction(label);
		return label;
	}
	public void labelNextInstruction(Label label)
	{
		label.setTarget(getNextInstructionAddress());
	}
	public Label createLabel(String name)
	{
		Label label = new Label(makeUniqueLabelName(name));
		allLabels.add(label);
		return label;
	}

	private int getNextInstructionAddress()
	{
		return instructions.size();
	}

	private String makeUniqueLabelName(String name)
	{
		if(usedLabelNames.add(name))
			return name;
		for(int i = 0;; i ++)
		{
			String candidate = name + "_" + i;
			if(usedLabelNames.add(candidate))
				return candidate;
		}
	}

	public MaMaProgramForDebugging assemble()
	{
		for(LabelArgInstruction labelArgInstr : labelArgInstructions)
		{
			DebuggingLabel debuggingLabelArg = labelArgInstr.getLabelArgument().asDebuggingLabel();
			InstructionForDebugging instruction = new InstructionForDebugging(labelArgInstr.getOpcode(), debuggingLabelArg);
			instructions.set(labelArgInstr.getInsertionPoint(), instruction);
		}
		return new MaMaProgramForDebugging(instructions, allLabels.stream().map(Label::asDebuggingLabel).collect(Collectors.toSet()));
	}

	public static class Label
	{
		private final String	name;
		private int				target;
		private DebuggingLabel	asDebuggingLabel;

		private Label(String name)
		{
			this.name = name;
			this.target = -1;
		}

		public String getName()
		{
			return name;
		}

		public void setTarget(int target)
		{
			if(target < 0)
				throw new IllegalArgumentException("Target illegal");
			if(this.target != -1)
				throw new IllegalStateException("Label already inserted");
			this.target = target;
		}
		private int getTarget()
		{
			if(this.target == -1)
				throw new IllegalStateException("Label not yet inserted");
			return target;
		}
		public DebuggingLabel asDebuggingLabel()
		{
			if(asDebuggingLabel != null)
				return asDebuggingLabel;
			return asDebuggingLabel = new DebuggingLabel(getName(), getTarget());
		}
	}

	private static class LabelArgInstruction
	{
		private final int		insertionPoint;
		private final Opcode	opcode;
		private final Label		argument;

		public LabelArgInstruction(int insertionPoint, Opcode opcode, Label argument)
		{
			this.insertionPoint = insertionPoint;
			this.opcode = opcode;
			this.argument = argument;
		}

		public int getInsertionPoint()
		{
			return insertionPoint;
		}
		public Opcode getOpcode()
		{
			return opcode;
		}
		public Label getLabelArgument()
		{
			return argument;
		}
	}
}
