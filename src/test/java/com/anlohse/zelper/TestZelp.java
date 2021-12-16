package com.anlohse.zelper;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import junit.framework.Assert;


public class TestZelp {

	@Test
	public void testFolderFileTree() {
		FolderFileBundle fft = new FolderFileBundle("temp");
		fft.put("src/test.txt", "Test file.".getBytes());
		Assert.assertEquals(fft.read("src/test.txt").length, 10);
		fft.clear();
		Assert.assertEquals(fft.read("src/test.txt"), null);
		fft.delete();
	}

	@Test
	public void testMemoryFileTree() throws IOException {
		MemoryFileBundle mft = new MemoryFileBundle();
		mft.put("src/test.txt", "Test file.".getBytes());
		Assert.assertNotNull(mft.get("src/test.txt"));
		Assert.assertEquals(mft.read("src/test.txt").length, 10);
		ZipOutputHelper zoh = new ZipOutputHelper("temp1.zip");
		zoh.compress(mft);
		mft.clear();
		Assert.assertNull(mft.read("src/test.txt"));
		Assert.assertNull(mft.get("src/test.txt"));
	}

	@Test
	public void testZip() throws IOException {
		FolderFileBundle mft = new FolderFileBundle("src");
		ZipOutputHelper zoh = new ZipOutputHelper("temp.zip");
		zoh.compress(mft);
	}
	
	@Test
	public void testUnZip() throws IOException {
		ZipInputHelper zoh = new ZipInputHelper("temp.zip");
		FileBundle folder = zoh.extractToFolder(new File("out"));
		System.out.println(folder.enumFiles());
	}
	
}
