package net.haspamelodica.minic.model.expressions;

import static net.haspamelodica.cma.model.Opcode.jump;
import static net.haspamelodica.cma.model.Opcode.jumpz;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.Assembler.Label;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;
import net.haspamelodica.minic.compiler.exeptions.CompilerException;
import net.haspamelodica.minic.model.types.PrimitiveType;
import net.haspamelodica.minic.model.types.Type;

public class Conditional implements Expression
{
	private final Expression	condition;
	private final Expression	onTrue;
	private final Expression	onFalse;

	public Conditional(Expression condition, Expression onTrue, Expression onFalse)
	{
		this.condition = condition;
		this.onTrue = onTrue;
		this.onFalse = onFalse;
	}

	@Override
	public Type getType(AddressEnvironment rho, boolean check)
	{
		//TODO don't let the onTrue type determine the result type
		Type onTrueType = onTrue.getType(rho, check);
		if(check)
		{
			if(!condition.getType(rho, check).isAssignableTo(PrimitiveType.int_))
				throw new CompilerException("Conditional's condition type not assignable to int");
			Type onFalseType = onFalse.getType(rho, check);
			if(!onFalseType.isAssignableTo(onTrueType))
				throw new CompilerException("Conditional's onFalse type not assignable to onTrue type");
		}
		return onTrueType;
	}
	@Override
	public void appendCodeR(Assembler assembler, AddressEnvironment rho)
	{
		condition.appendCodeR(assembler, rho);
		Label false_ = assembler.createLabel("false");
		assembler.append(jumpz, false_);
		onTrue.appendCodeR(assembler, rho);
		Label end = assembler.createLabel("end");
		assembler.append(jump, end);
		assembler.labelNextInstruction(false_);
		onFalse.appendCodeR(assembler, rho);
		assembler.labelNextInstruction(end);
	}
	@Override
	public int maxStackSizeR(AddressEnvironment rho)
	{
		return Math.max(condition.maxStackSizeR(rho), Math.max(onTrue.maxStackSizeR(rho), onFalse.maxStackSizeR(rho)));
	}

	public Expression getCondition()
	{
		return condition;
	}
	public Expression getOnTrue()
	{
		return onTrue;
	}
	public Expression getOnFalse()
	{
		return onFalse;
	}
}
