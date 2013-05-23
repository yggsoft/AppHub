package com.app.hub;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager {
	private final Logger logger = LoggerFactory.getLogger(FileManager.class);

	private File sourceFile;
	private File destFile;
	private final static String NO_SOURCE = "Source file or directory does not exist.";
	private final static String NO_DEST = "Destination file or directory does not exist.";
	private final static String SOURCE_IS_FILE = "Source directory is a file";
	private final static String DEST_IS_FILE = "Destination directory is a file";

	private FileSynchronizationHander hander;

	public FileManager(String source, String destination) throws NoDirException {
		this(new File(source), new File(destination));
	}

	public FileManager(File sourceFile, File destFile) throws NoDirException {
		this.sourceFile = sourceFile;
		this.destFile = destFile;
		check(sourceFile, destFile);
	}

	private void check(File sourceFile2, File destFile2) throws NoDirException {
		if (!this.sourceFile.exists()) {
			System.out.println(NO_SOURCE);
			logger.info(NO_SOURCE);
			throw new NoDirException(NO_SOURCE);
		}

		if (this.sourceFile.isFile()) {
			throw new NoDirException(SOURCE_IS_FILE);
		}

		if (!this.destFile.exists()) {
			System.out.println(NO_DEST);
			logger.info(NO_DEST);
			throw new NoDirException(NO_DEST);
		}

		if (this.destFile.isFile()) {
			throw new NoDirException(DEST_IS_FILE);
		}
	}

	public void synchronize() {

		Dir srcDir = new Dir(this.sourceFile);
		Dir destDir = new Dir(this.destFile);

		hander = new FileSynchronizationHander(
				srcDir, destDir);
		hander.synchronize();
	}

	public List<File> synchronizedFile() {
		return hander.synchronizedFiles();
	}
}
