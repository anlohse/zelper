package com.anlohse.zelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipInputHelper {
	
	ZipInputStream zis;

	public ZipInputHelper(ZipInputStream in) {
		this.zis = in;
	}

	public ZipInputHelper(InputStream in) {
		this(new ZipInputStream(in, Charset.defaultCharset()));
	}

	public ZipInputHelper(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	public ZipInputHelper(String fileName) throws FileNotFoundException {
		this(new File(fileName));
	}

	public FileBundle extractToMemory() throws IOException {
		MemoryFileBundle ft = new MemoryFileBundle();
		return extracted(ft);
	}

	public FileBundle extracted(FileBundle fileBundle) throws IOException {
		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
			byte[] data = readData(ze);
			fileBundle.put(ze.getName(), data);
		}
		return fileBundle;
	}

	public FileBundle extractToFolder(File folder) throws IOException {
		FolderFileBundle ft = new FolderFileBundle(folder);
		return extracted(ft);
	}

	private byte[] readData(ZipEntry ze) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		move(bos,zis);
		return bos.toByteArray();
	}

	static void move(OutputStream bos, InputStream in) throws IOException {
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = in.read(buf)) > 0) {
			bos.write(buf,0,len);
		}
	}
	
	public void close() throws IOException {
		zis.close();
	}
	
}
