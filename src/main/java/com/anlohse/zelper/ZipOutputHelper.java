package com.anlohse.zelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputHelper {

	ZipOutputStream zos;

	public ZipOutputHelper(ZipOutputStream in) {
		super();
		this.zos = in;
	}

	public ZipOutputHelper(OutputStream in) {
		super();
		this.zos = new ZipOutputStream(in);
	}

	public ZipOutputHelper(File file) throws FileNotFoundException {
		super();
		this.zos = new ZipOutputStream(new FileOutputStream(file));
	}
	
	public ZipOutputHelper(String filename) throws FileNotFoundException {
		this(new File(filename));
	}
	
	public void compress(File folder) throws IOException {
		FolderFileBundle fft = new FolderFileBundle(folder);
		compress(fft);
	}

	public void compress(FileBundle files) throws IOException {
		Enumeration<String> names = files.enumFiles();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			ZipEntry e = new ZipEntry(name);
			zos.putNextEntry(e);
			InputStream in = files.get(name);
			ZipInputHelper.move(zos, in);
			in.close();
			zos.closeEntry();
		}
		zos.finish();
		zos.flush();
		zos.close();
	}

    /**
     * Sets the compression level.
     * The default setting is DEFAULT_COMPRESSION.
     * @param level the compression level (0-9)
     * @exception IllegalArgumentException if the compression level is invalid
	 * @see java.util.zip.ZipOutputStream#setLevel(int)
     */
	public void setLevel(int level) {
		zos.setLevel(level);
	}

	/**
     * Sets the default compression method.
     * @param method the default compression method
     * @exception IllegalArgumentException if the specified compression method
     *		  is invalid
	 * @see java.util.zip.ZipOutputStream#setMethod(int)
	 */
	public void setMethod(int method) {
		zos.setMethod(method);
	}
	
}
