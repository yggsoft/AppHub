package com.app.hub;

public class FileSynchronizationHander {

	private Dir srcDir;
	private Dir destDir;

	public FileSynchronizationHander(Dir srcDir, Dir destDir) {
		this.srcDir = srcDir;
		this.destDir = destDir;
	}

	public void synchronize() {
		TransferMapping mapping = new TransferMapping(this.srcDir, this.destDir);
		mapping.synchronize();
	}

}
