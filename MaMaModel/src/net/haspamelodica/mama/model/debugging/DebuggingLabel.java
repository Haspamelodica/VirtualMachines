package net.haspamelodica.mama.model.debugging;

public class DebuggingLabel
{
	private final String	name;
	private final int		target;

	public DebuggingLabel(String name, int target)
	{
		this.name = name;
		this.target = target;
	}

	public String getName()
	{
		return name;
	}
	public int getTarget()
	{
		return target;
	}

	@Override
	public String toString()
	{
		return name + " <" + target + ">";
	}
}
