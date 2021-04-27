package net.haspamelodica.mama.interpreter;

import java.util.List;

import net.haspamelodica.mama.interpreter.exceptions.InterpreterException;
import net.haspamelodica.mama.interpreter.heap.Heap;
import net.haspamelodica.mama.interpreter.heap.HeapObjectRef;
import net.haspamelodica.mama.interpreter.stack.Stack;
import net.haspamelodica.mama.model.Instruction;
import net.haspamelodica.mama.model.MaMaProgram;

public class AbstractMaMaInterpreter
{
	private final MaMaProgram program;

	private final Stack	stack;
	private final Heap	heap;
	private int			currentCodePointer;

	protected AbstractMaMaInterpreter(MaMaProgram program, Stack stack, Heap heap)
	{
		this.program = program;
		this.stack = stack;
		this.heap = heap;
	}

	public void run()
	{
		reset();
		while(step());
	}
	public void reset()
	{
		stack.clear();
		heap.clear();
		currentCodePointer = 0;
	}
	public boolean step()
	{
		int executedInstrPointer = currentCodePointer ++;
		if(executedInstrPointer < 0 || executedInstrPointer >= program.getLength())
			throw new InterpreterException("Trying to execute out-of-bounds instruction: " + executedInstrPointer);
		return exec(executedInstrPointer, program.getInstructionAt(executedInstrPointer));
	}

	private boolean exec(int executedInstrPointer, Instruction instruction)
	{
		execHook(executedInstrPointer, instruction);
		switch(instruction.getOpcode())
		{
			case loadc:
				stack.pushBasic(instruction.getImmediate());
				return true;
			case getbasic:
				stack.pushBasic(stack.popHeapReference().getContent().checkBasicValue().getValue());
				return true;
			case mkbasic:
				stack.pushHeapReference(heap.createBasic(stack.popBasic()));
				return true;
			case mkvec:
				HeapObjectRef[] referencedObjects = new HeapObjectRef[instruction.getImmediate()];
				for(int i = instruction.getImmediate() - 1; i >= 0; i --)
					referencedObjects[i] = stack.popHeapReference();
				stack.pushHeapReference(heap.createVector(List.of(referencedObjects)));
				return true;
			default:
				throw new InterpreterException("Unimplemented opcode: " + instruction.getOpcode());
		}
	}

	/**
	 * This hook is called just before an instruction is executed.<br>
	 * The default implementation does nothing.
	 */
	protected void execHook(int executedInstrPointer, Instruction instruction)
	{}
}
