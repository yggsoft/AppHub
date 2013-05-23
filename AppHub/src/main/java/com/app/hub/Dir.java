package com.app.hub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dir {
	private File dir;
	private List<File> emptyDIRs;

	public Dir(File dir) {
		this.dir = dir;
		this.emptyDIRs = new ArrayList<File>();
	}

	public String getAbsolutePath() {
		return this.dir.getAbsolutePath();
	}

	public List<File> getAllFiles() {
		List<File> files = new ArrayList<File>();
		File[] filesAndDirectories = this.dir.listFiles();
		if(filesAndDirectories.length == 0){
			this.emptyDIRs.add(this.dir);
		}
		for (File file : filesAndDirectories) {
			if (file.isFile()) {
				files.add(file);
			} else {
				Dir subDir = new Dir(file);
				files.addAll(subDir.getAllFiles());
				this.emptyDIRs.addAll(subDir.getEmptyDIRs());
			}
		}
		return files;
	}

	public List<File> getEmptyDIRs() {
		return emptyDIRs;
	}
}
