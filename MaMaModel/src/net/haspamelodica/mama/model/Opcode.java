package net.haspamelodica.mama.model;

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
	pushloc(true),
	pushglob(true),
	slide(true),

	//control flow
	jump(true),
	jumpz(true),

	//functions
	mark(true),
	apply,
	targ(true),
	return_(true, "return"),

	//closures
	eval,

	//heap
	getbasic,
	mkbasic,
	mkvec(true),
	mkfunval(true);

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

	@Override
	public String toString()
	{
		return getOpcodeName();
	}
}