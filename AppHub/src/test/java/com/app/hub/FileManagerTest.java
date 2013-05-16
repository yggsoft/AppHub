package com.app.hub;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class FileManagerTest {

	@Test
	public void synchronizeWithFileAndDir() {
		FileManager fileManager = new FileManager(
				"D:\\git\\AppHub\\AppHub\\src\\test\\resource\\src\\src.txt",
				"D:\\git\\AppHub\\AppHub\\src\\test\\resource\\dest");
		List<File> pendingFiles = fileManager.getPendingFiles();
		fileManager.synchronize();
		List<File> syncFiles = fileManager.syschronizedFiles();
	}

	// @Test
	public void synchronizeWithDirAndDir() {
		FileManager fileManager = new FileManager(
				"D:\\git\\AppHub\\AppHub\\src\\test\\resource\\src",
				"D:\\git\\AppHub\\AppHub\\src\\test\\resource\\dest");
		List<File> pendingFiles = fileManager.getPendingFiles();
		fileManager.synchronize();
		List<File> syncFiles = fileManager.syschronizedFiles();
	}

}
