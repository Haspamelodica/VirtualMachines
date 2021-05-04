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
				add();
				return true;
			case mul:
				mul();
				return true;
			case loadc:
				loadc(instruction.getImmediate());
				return true;
			case pushloc:
				pushloc(instruction.getImmediate());
				return true;
			case pushglob:
				pushglob(instruction.getImmediate());
				return true;
			case slide:
				slide(instruction.getImmediate());
				return true;
			case alloc:
				alloc(instruction.getImmediate());
				return true;
			case jump:
				jump(instruction.getImmediate());
				return true;
			case mark:
				mark(instruction.getImmediate());
				return true;
			case apply:
				apply();
				return true;
			case targ:
				targ(instruction.getImmediate());
				return true;
			case return_:
				return_(instruction.getImmediate());
				return true;
			case rewrite:
				rewrite(instruction.getImmediate());
				return true;
			case getbasic:
				getbasic();
				return true;
			case mkbasic:
				mkbasic();
				return true;
			case mkvec:
				mkvec(instruction.getImmediate());
				return true;
			case mkfunval:
				mkfunval(instruction.getImmediate());
				return true;
			default:
				throw new InterpreterException("Unimplemented opcode: " + instruction.getOpcode());
		}
	}

	private void add()
	{
		stack.pushBasic(stack.popBasic() + stack.popBasic());
	}
	private void mul()
	{
		stack.pushBasic(stack.popBasic() * stack.popBasic());
	}
	private void loadc(int value)
	{
		stack.pushBasic(value);
	}
	private void pushloc(int offsetFromStackPointer)
	{
		stack.pushHeapReference(stack.getHeapReferenceRelative(offsetFromStackPointer));
	}
	private void pushglob(int index)
	{
		stack.pushHeapReference(getGlobalPointer().checkVector().get(index));
	}
	private void slide(int numberOfValuesToPop)
	{
		HeapObject remainingValue = stack.popHeapReference();
		stack.popMultiple(numberOfValuesToPop);
		stack.pushHeapReference(remainingValue);
	}
	private void alloc(int numberOfVariables)
	{
		for(int i = 0; i < numberOfVariables; i ++)
			stack.pushHeapReference(heap.createClosure(-1, null));
	}
	private void jump(int target)
	{
		setCodePointer(target);
	}
	private void mark(int returnAddress)
	{
		stack.pushHeapReference(getGlobalPointer());
		stack.pushBasic(getFramePointer());//TODO keep the information that this value represents a stack pointer
		stack.pushBasic(returnAddress);//TODO keep the information that this value represents a code pointer
		setFramePointer(stack.getStackPointer());
	}
	private void apply()
	{
		Function calledFunction = stack.popHeapReference().checkFunction();
		setGlobalPointer(calledFunction.getGlobalPointer());
		setCodePointer(calledFunction.getCodePointer());
		Vector arguments = calledFunction.getArgumentPointer().checkVector();
		for(int i = 0; i < arguments.getLength(); i ++)
			stack.pushHeapReference(arguments.get(i));
	}
	private void targ(int parameterCount)
	{
		int argumentCount = stack.getStackPointer() - getFramePointer();
		if(argumentCount < parameterCount)
		{
			HeapObject argumentPointer = createVector(argumentCount);
			stack.pushHeapReference(heap.createFunction(getCodePointer() - 1, argumentPointer, getGlobalPointer()));
			popenv();
		}
	}
	private void return_(int parameterCount)
	{
		if(stack.getStackPointer() - getFramePointer() == parameterCount + 1)
			popenv();
		else
		{
			slide(parameterCount);
			apply();
		}
	}
	private void rewrite(int offsetFromStackPointer)
	{
		stack.getHeapReferenceRelative(offsetFromStackPointer).setContent(stack.popHeapReference().getContent());
	}
	private void getbasic()
	{
		stack.pushBasic(stack.popHeapReference().checkBasicValue().getValue());
	}
	private void mkbasic()
	{
		stack.pushHeapReference(heap.createBasic(stack.popBasic()));
	}
	private void mkvec(int valueCount)
	{
		stack.pushHeapReference(createVector(valueCount));
	}
	private void mkfunval(int bodyCodePointer)
	{
		stack.pushHeapReference(heap.createFunction(bodyCodePointer, heap.createVector(List.of()), stack.popHeapReference()));
	}
	private void popenv()
	{
		HeapObject returnValue = stack.popHeapReference();
		stack.popMultiple(stack.getStackPointer() - getFramePointer());
		setCodePointer(stack.popBasic());
		setFramePointer(stack.popBasic());
		setGlobalPointer(stack.popHeapReference());
		stack.pushHeapReference(returnValue);
	}

	private HeapObject createVector(int valueCount)
	{
		HeapObject[] referencedObjects = new HeapObject[valueCount];
		for(int i = valueCount - 1; i >= 0; i --)
			referencedObjects[i] = stack.popHeapReference();
		return heap.createVector(List.of(referencedObjects));
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
