package fr.inria.diverse.noveltytesting.coverage;

import java.util.HashMap;
import java.util.Map;

/**
 * A class loader that loads classes from in-memory data.
 * 
 * @author mboussaa
 * 
 */

public class MemoryClassLoader extends ClassLoader {

	public Map<String, byte[]> definitions = new HashMap<String, byte[]>();

	public void addDefinition(final String name, final byte[] bytes) {
		definitions.put(name, bytes);
	}

	@Override
	protected Class<?> loadClass(final String name, final boolean resolve)
			throws ClassNotFoundException {
		 byte[] bytes = definitions.get(name);
		if (bytes != null) {
			return defineClass(name, bytes, 0, bytes.length);
		}
		return super.loadClass(name, resolve);
	}

}