package net.haspamelodica.mama;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.haspamelodica.mama.model.Opcode;
import net.haspamelodica.mama.model.debugging.DebuggingLabel;
import net.haspamelodica.mama.model.debugging.InstructionForDebugging;
import net.haspamelodica.mama.model.debugging.MaMaProgramForDebugging;

public class MaMaParser
{
	public static MaMaProgramForDebugging parse(Path path) throws IOException
	{
		try(BufferedReader in = Files.newBufferedReader(path))
		{
			return parse(in);
		}
	}
	public static MaMaProgramForDebugging parse(BufferedReader in) throws IOException
	{
		List<InstructionForDebugging> instructions = new ArrayList<>();
		Map<String, DebuggingLabel> labels = new HashMap<>();
		Set<LabelReference> labelReferences = new HashSet<>();
		for(;;)
		{
			String line = in.readLine();
			if(line == null)
				return createProgram(instructions, labels, labelReferences);

			line = line.replaceAll("//.*", "").strip();

			for(;;)
			{
				int indexOfColon = line.indexOf(':');
				if(indexOfColon == -1)
					break;

				String label = line.substring(0, indexOfColon).stripTrailing();//line already is stripped of leading whitespace
				labels.put(label, new DebuggingLabel(label, instructions.size()));
				line = line.substring(indexOfColon + 1).stripLeading();
			}

			if(line.isEmpty())
				continue;

			parseInstruction(line, instructions, labelReferences);
		}
	}
	private static void parseInstruction(String line, List<InstructionForDebugging> instructions, Set<LabelReference> labelReferences)
	{
		String[] parts = line.split("\\h+");
		Opcode opcode = Opcode.forOpcodeName(parts[0]);
		InstructionForDebugging instruction;
		if(opcode.hasImmediate())
		{
			if(parts.length != 2)
				if(parts.length < 2)
					throw new IllegalArgumentException("Expected an argument to " + opcode + ", but none was given");
				else
					throw new IllegalArgumentException("Expected exactly one argument to " + opcode + ", but more were given");
			String argument = parts[1];
			if(argument.matches("[0-9]+"))
				instruction = new InstructionForDebugging(opcode, Integer.parseInt(argument));
			else
			{
				instruction = null;
				labelReferences.add(new LabelReference(instructions.size(), opcode, argument));
			}
		} else
		{
			if(parts.length != 1)
				throw new IllegalArgumentException("Expected no arguments to " + opcode + ", but some were given");
			instruction = new InstructionForDebugging(opcode);
		}
		instructions.add(instruction);
	}
	private static MaMaProgramForDebugging createProgram(List<InstructionForDebugging> instructions,
			Map<String, DebuggingLabel> labels, Set<LabelReference> labelReferences)
	{
		for(LabelReference labelReference : labelReferences)
		{
			DebuggingLabel label = labels.get(labelReference.getLabel());
			if(label == null)
				throw new IllegalArgumentException("Unknown label: " + label);
			instructions.set(labelReference.getInsertionPoint(), new InstructionForDebugging(labelReference.getOpcode(), label));
		}
		return new MaMaProgramForDebugging(instructions, Set.copyOf(labels.values()));
	}

	private MaMaParser()
	{}

	private static class LabelReference
	{
		private final int		insertionPoint;
		private final Opcode	opcode;
		private final String	label;

		public LabelReference(int insertionPoint, Opcode opcode, String label)
		{
			this.insertionPoint = insertionPoint;
			this.opcode = opcode;
			this.label = label;
		}

		public int getInsertionPoint()
		{
			return insertionPoint;
		}
		public Opcode getOpcode()
		{
			return opcode;
		}
		public String getLabel()
		{
			return label;
		}
	}
}
