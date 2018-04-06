package com.anlohse.zelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class FolderFileBundle extends FileBundle {

	File folder;

	public FolderFileBundle(File folder) {
		this.folder = folder;
		if (!folder.exists())
			folder.mkdirs();
	}

	public FolderFileBundle() {
		this(new File(System.getProperty("java.io.tmpdir") + File.pathSeparator + "FolderFileTree" + System.currentTimeMillis()));
	}

	public FolderFileBundle(String path) {
		this(new File(path));
	}

	@Override
	public void put(String name, byte[] data) {
		try {
			File file = new File(folder,name);
			File parent = file.getParentFile();
			if (!parent.exists()) parent.mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			throw new ZelperException("Error adding a file "+name,e);
		}
	}

	@Override
	public InputStream get(String name) {
		try {
			return new FileInputStream(new File(folder,name));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	@Override
	public boolean remove(String name) {
		return new File(folder,name).delete();
	}

	@Override
	public Enumeration<String> enumFiles() {
		List<String> files = new ArrayList<String>();
		addFiles(folder,files);
		final Iterator<String> it = files.iterator();
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
		List<String> files = new ArrayList<String>();
		File ffolder = new File(folder,subFolder);
		if (!ffolder.isDirectory()) throw new IllegalArgumentException(subFolder + " is not a folder.");
		addFiles(ffolder,files);
		final Iterator<String> it = files.iterator();
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			public String nextElement() {
				return it.next();
			}
		};
	}

	private void addFiles(File parent, List<String> files) {
		File[] children = parent.listFiles();
		for (File file : children) {
			if (file.isDirectory())
				addFiles(file,files);
			else {
				String name = file.getAbsolutePath().substring(folder.getAbsolutePath().length()+1);
				files.add(name);
			}
		}
	}

	@Override
	public void clear() {
		File[] files = folder.listFiles();
		for (File file : files) {
			removeFile(file);
		}
	}

	private void removeFile(File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				removeFile(child);
			}
		}
		file.delete();
	}

	public void delete() {
		clear();
		folder.delete();
	}

	@Override
	public void close() {
	}
}
