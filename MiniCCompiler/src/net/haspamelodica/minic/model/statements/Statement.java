package net.haspamelodica.minic.model.statements;

import net.haspamelodica.minic.compiler.AddressEnvironment;
import net.haspamelodica.minic.compiler.Assembler;

public interface Statement
{
	public void appendCode(Assembler assembler, AddressEnvironment rho);
	public int maxStackSize();
}
