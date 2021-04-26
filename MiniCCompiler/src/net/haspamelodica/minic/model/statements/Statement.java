package net.haspamelodica.minic.model.statements;

import net.haspamelodica.minic.compiler.Assembler;
import net.haspamelodica.minic.compiler.environment.AddressEnvironment;

public interface Statement
{
	public void appendCode(Assembler assembler, AddressEnvironment rho);
	public int maxStackSize(AddressEnvironment rho);
}
