package com.anlohse.zelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MemoryFileBundle extends FileBundle {

	Map<String, byte[]> files;
	
	public MemoryFileBundle() {
		files = new HashMap<String, byte[]>();
	}

	@Override
	public void put(String name, byte[] data) {
		files.put(name,data);
	}

	@Override
	public InputStream get(String name) {
		byte[] data = files.get(name);
		if (data == null) return null;
		return new ByteArrayInputStream(data);
	}
	
	@Override
	public byte[] read(String name) {
		return files.get(name);
	}

	@Override
	public boolean remove(String name) {
		return files.remove(name) != null;
	}

	@Override
	public Enumeration<String> enumFiles() {
		final Iterator<String> it = files.keySet().iterator();
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			public String nextElement() {
				return it.next();
			}
		};
	}

	@Override
	public Enumeration<String> enumFiles(String subFolder) {
		List<String> list = new ArrayList<String>();
		for (String key : files.keySet())
			if (key.startsWith(subFolder+"/") || key.startsWith(subFolder+"\\"))
				list.add(key);
		final Iterator<String> it = list.iterator();
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			public String nextElement() {
				return it.next();
			}
		};
	}

	@Override
	public void clear() {
		files.clear();
	}

	@Override
	public void close() {
	}

}
