package com.anlohse.zelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * This is the abstract class for bundles of files.
 * 
 * @author Alan N. Lohse
 *
 */
public abstract class FileBundle {
	
	public void put(File file) {
		try {
			put(file.getName(), new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new ZelperException("The file "+file.getName()+" is not valid.",e);
		}
	}
	
	public abstract void put(String name, byte[] data);
	
	public void put(String name, InputStream in) {
		try {
			put(name,readData(in));
		} catch (IOException e) {
			throw new ZelperException("An error has occurred while reading file "+name,e);
		}
	}

	public abstract InputStream get(String name);
	
	public byte[] read(String name) {
		InputStream in = get(name);
		try {
			if (in != null) {
				byte[] data = readData(in);
				in.close();
				return data;
			}
			return null;
		} catch (IOException e) {
			throw new ZelperException("An error has occurred while reading file "+name,e);
		}
	}
	
	public abstract boolean remove(String name);

	public abstract Enumeration<String> enumFiles();
	
	public abstract Enumeration<String> enumFiles(String subFolder);
	
	public abstract void clear();
	
	protected byte[] readData(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = in.read(buf)) > 0) {
			bos.write(buf,0,len);
		}
		return bos.toByteArray();
	}

	public abstract void close();
	
}
