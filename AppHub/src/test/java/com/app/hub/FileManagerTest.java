package com.app.hub;

import java.io.File;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

public class FileManagerTest {
	private static final String TEST_DATA_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testdata";

	//
	private static final String TEST_DATA_TEMPLATE1_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testDataTemplate";
	private static final String TEST_DATA_TEMPLATE2_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testDataTemplate2";
	private static final String TEST_DATA_TEMPLATE3_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testDataTemplate3";
	private static final String TEST_DATA_TEMPLATE4_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testDataTemplate4";

	private static final String SRC_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testdata\\src";
	private static final String DEST_DIR = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testdata\\dest";
	private static final String SRC_FILE = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testdata\\srcFile.txt";
	private static final String DEST_FILE = "D:\\git\\AppHub\\AppHub\\src\\test\\resource\\testdata\\destFile.txt";

	@Before
	public void setUp() throws Exception {
		clear(new File(TEST_DATA_DIR));
	}

	private void init(String testTemplate) {
		Dir src = new Dir(new File(TEST_DATA_TEMPLATE1_DIR));
		Dir dest = new Dir(new File(TEST_DATA_DIR));
		new TransferMapping(src, dest).copy();
	}

	private void clear(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		for (File f : file.listFiles()) {
			clear(f);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void synchronizeDirToDirWithTemplete1() {
		init(TEST_DATA_TEMPLATE1_DIR);
		FileManager fileManager = syschronize();
		List<File> synchronizedFiles = fileManager.synchronizedFile();

		assertThat(synchronizedFiles, allOf(hasSize(2)));
	}

	@Test
	public void synchronizeDirToDirWithTemplete2() {
		init(TEST_DATA_TEMPLATE2_DIR);
		FileManager fileManager = syschronize();
		List<File> synchronizedFiles = fileManager.synchronizedFile();

		assertThat(synchronizedFiles, allOf(hasSize(1)));
	}

	@Test
	public void synchronizeDirToDirWithTemplete3() {
		init(TEST_DATA_TEMPLATE3_DIR);
		FileManager fileManager = syschronize();
		List<File> synchronizedFiles = fileManager.synchronizedFile();

		assertThat(synchronizedFiles, allOf(hasSize(2)));
	}

	@Test
	public void synchronizeDirToDirWithTemplete4() {
		init(TEST_DATA_TEMPLATE4_DIR);
		FileManager fileManager = syschronize();

		List<File> synchronizedFiles = fileManager.synchronizedFile();
		assertThat(synchronizedFiles, allOf(hasSize(1)));
	}

	private FileManager syschronize() {
		FileManager fileManager = null;
		try {
			fileManager = new FileManager(SRC_DIR, DEST_DIR);
		} catch (NoDirException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		fileManager.synchronize();
		return fileManager;
	}

	@Test(expected = NoDirException.class)
	public void initializeWithFileToDir() throws NoDirException {
		buildFileManager(SRC_FILE, DEST_DIR);
	}

	@Test(expected = NoDirException.class)
	public void initializeWithFileToFile() throws NoDirException {
		buildFileManager(SRC_FILE, DEST_FILE);
	}

	@Test(expected = NoDirException.class)
	public void initializeWithDirToFile() throws NoDirException {
		buildFileManager(SRC_DIR, DEST_FILE);
	}

	private void buildFileManager(String srcDir, String destDir)
			throws NoDirException {
		init(TEST_DATA_TEMPLATE1_DIR);
		new FileManager(srcDir, destDir);
	}
}
