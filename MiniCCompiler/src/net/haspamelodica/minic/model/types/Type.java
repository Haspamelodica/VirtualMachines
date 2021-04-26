package net.haspamelodica.minic.model.types;

public interface Type
{
	public int size();
	public boolean isAssignableTo(Type other);
}
