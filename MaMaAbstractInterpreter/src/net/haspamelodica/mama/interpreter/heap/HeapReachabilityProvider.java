package net.haspamelodica.mama.interpreter.heap;

import java.util.stream.Stream;

public interface HeapReachabilityProvider 
{
	public Stream<HeapObject> getReachableObjects();
}
