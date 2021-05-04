package net.haspamelodica.mama.model;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	//variables
	loadc(true),
	pushloc(true),
	pushglob(true),
	slide(true),
	alloc(true),

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
	update,
	rewrite(true),

	//heap
	getbasic,
	mkbasic,
	mkclos(true),
	mkfunval(true),
	mkvec(true);

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

	private static final Map<String, Opcode> opcodesByName = Stream.of(values())
			.collect(Collectors.toUnmodifiableMap(Opcode::getOpcodeName, Function.identity()));

	public static Opcode forOpcodeName(String opcodeName)
	{
		return Objects.requireNonNull(opcodesByName.get(opcodeName), "Unknown opcode: " + opcodeName);
	}
}