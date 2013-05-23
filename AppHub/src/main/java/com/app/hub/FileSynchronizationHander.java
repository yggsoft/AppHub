package com.app.hub;

import java.io.File;
import java.util.List;

public class FileSynchronizationHander {

	private Dir srcDir;
	private Dir destDir;
	private TransferMapping mapping;

	public FileSynchronizationHander(Dir srcDir, Dir destDir) {
		this.srcDir = srcDir;
		this.destDir = destDir;
	}

	public void synchronize() {
		mapping = new TransferMapping(this.srcDir, this.destDir);
		mapping.synchronize();
	}

	public List<File> synchronizedFiles() {
		return mapping.synchronizedFiles();
	}

}
