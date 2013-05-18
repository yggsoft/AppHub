package com.app.hub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dir {
	private File dir;

	public Dir(File dir) {
		this.dir = dir;
	}

	public String getAbsolutePath() {
		return this.dir.getAbsolutePath();
	}

	public List<File> getAllFiles() {
		List<File> files = new ArrayList<File>();
		File[] filesAndDirectories = this.dir.listFiles();
		for (File file : filesAndDirectories) {
			if (file.isFile()) {
				files.add(file);
			} else {
				files.addAll(new Dir(file).getAllFiles());
			}
		}
		return files;
	}
}
