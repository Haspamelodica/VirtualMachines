package net.haspamelodica.mama.interpreter;

import java.util.List;

import net.haspamelodica.mama.interpreter.exceptions.InterpreterException;
import net.haspamelodica.mama.interpreter.heap.Function;
import net.haspamelodica.mama.interpreter.heap.Heap;
import net.haspamelodica.mama.interpreter.heap.HeapObject;
import net.haspamelodica.mama.interpreter.heap.Vector;
import net.haspamelodica.mama.interpreter.stack.Stack;
import net.haspamelodica.mama.model.Instruction;
import net.haspamelodica.mama.model.MaMaProgram;

public class AbstractMaMaInterpreter
{
	private final MaMaProgram program;

	private final Stack	stack;
	private final Heap	heap;
	private int			codePointer;
	private int			framePointer;
	private HeapObject	globalPointer;

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
		setCodePointer(0);
		setGlobalPointer(null);
	}

	public boolean step()
	{
		int executedInstrPointer = getCodePointer();
		setCodePointer(executedInstrPointer + 1);
		if(executedInstrPointer < 0 || executedInstrPointer >= program.getLength())
			throw new InterpreterException("Trying to execute out-of-bounds instruction: " + executedInstrPointer);
		return exec(executedInstrPointer, program.getInstructionAt(executedInstrPointer));
	}

	private boolean exec(int executedInstrPointer, Instruction instruction)
	{
		execHook(executedInstrPointer, instruction);
		switch(instruction.getOpcode())
		{
			case add:
				stack.pushBasic(stack.popBasic() + stack.popBasic());
				return true;
			case mul:
				stack.pushBasic(stack.popBasic() * stack.popBasic());
				return true;
			case loadc:
				stack.pushBasic(instruction.getImmediate());
				return true;
			case pushloc:
				stack.pushHeapReference(stack.getHeapReferenceRelative(instruction.getImmediate()));
				return true;
			case pushglob:
				stack.pushHeapReference(getGlobalPointer().checkVector().get(instruction.getImmediate()));
				return true;
			case slide:
				HeapObject remainingValue = stack.popHeapReference();
				stack.popMultiple(instruction.getImmediate());
				stack.pushHeapReference(remainingValue);
				return true;
			case mark:
				stack.pushHeapReference(getGlobalPointer());
				stack.pushBasic(getFramePointer());//TODO keep the information that this value represents a stack pointer
				stack.pushBasic(instruction.getImmediate());//TODO keep the information that this value represents a code pointer
				setFramePointer(stack.getStackPointer());
				return true;
			case apply:
				Function calledFunction = stack.popHeapReference().checkFunction();
				setGlobalPointer(calledFunction.getGlobalPointer());
				setCodePointer(calledFunction.getCodePointer());
				Vector arguments = calledFunction.getArgumentPointer().checkVector();
				for(int i = 0; i < arguments.getLength(); i ++)
					stack.pushHeapReference(arguments.get(i));
				return true;
			case getbasic:
				stack.pushBasic(stack.popHeapReference().checkBasicValue().getValue());
				return true;
			case mkbasic:
				stack.pushHeapReference(heap.createBasic(stack.popBasic()));
				return true;
			case mkvec:
				HeapObject[] referencedObjects = new HeapObject[instruction.getImmediate()];
				for(int i = instruction.getImmediate() - 1; i >= 0; i --)
					referencedObjects[i] = stack.popHeapReference();
				stack.pushHeapReference(heap.createVector(List.of(referencedObjects)));
				return true;
			case mkfunval:
				stack.pushHeapReference(heap.createFunction(instruction.getImmediate(), heap.createVector(List.of()), stack.popHeapReference()));
				return true;
			default:
				throw new InterpreterException("Unimplemented opcode: " + instruction.getOpcode());
		}
	}

	public int getCodePointer()
	{
		return codePointer;
	}
	public void setCodePointer(int codePointer)
	{
		this.codePointer = codePointer;
		codePointerHook(codePointer);
	}
	public int getFramePointer()
	{
		return framePointer;
	}
	public void setFramePointer(int framePointer)
	{
		this.framePointer = framePointer;
		framePointerHook(framePointer);
	}
	public HeapObject getGlobalPointer()
	{
		return globalPointer;
	}
	public void setGlobalPointer(HeapObject globalPointer)
	{
		this.globalPointer = globalPointer;
		globalPointerHook(globalPointer);
	}

	/**
	 * This hook is called just before an instruction is executed.<br>
	 * The default implementation does nothing.
	 */
	protected void execHook(int executedInstrPointer, Instruction instruction)
	{}

	/**
	 * This hook is called when the code pointer changes.<br>
	 * The default implementation does nothing.
	 */
	protected void codePointerHook(int newCodePointer)
	{}

	/**
	 * This hook is called when the frame pointer changes.<br>
	 * The default implementation does nothing.
	 */
	protected void framePointerHook(int newFramePointer)
	{}

	/**
	 * This hook is called when the global pointer changes.<br>
	 * The default implementation does nothing.
	 */
	protected void globalPointerHook(HeapObject newGlobalPointer)
	{}
}
