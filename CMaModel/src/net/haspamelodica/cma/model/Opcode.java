package net.haspamelodica.cma.model;

public enum Opcode
{
	//arithmetic
	add,
	sub,
	mul,
	div,
	mod,
	and,
	or,
	xor,
	neg,

	//conditionals
	eq,
	neq,
	le,
	leq,
	gr,
	geq,
	not,

	//data moving
	loadc(true),
	load,
	store,
	loada(true),
	storea(true),
	loadrc(true),
	loadr(true),
	storer(true),
	move(true),

	//stack manipulation
	pop,
	dup,

	//control flow
	jump(true),
	jumpz(true),
	jumpi(true),

	//functions
	mark,
	call,
	enter(true),
	alloc(true),
	return_("return"),
	slide,

	//heap
	new_("new"),

	//program exit
	halt;

	private final boolean	hasImmediate;
	private final String	opcodeName;

	private Opcode(String opcodeName)
	{
		this.hasImmediate = false;
		this.opcodeName = opcodeName;
	}
	private Opcode()
	{
		this.hasImmediate = false;
		this.opcodeName = name();
	}
	private Opcode(boolean hasImmediate, String opcodeName)
	{
		this.hasImmediate = hasImmediate;
		this.opcodeName = opcodeName;
	}
	private Opcode(boolean hasImmediate)
	{
		this.hasImmediate = hasImmediate;
		this.opcodeName = name();
	}

	public boolean hasImmediate()
	{
		return hasImmediate;
	}
	public String getOpcodeName()
	{
		return opcodeName;
	}
}