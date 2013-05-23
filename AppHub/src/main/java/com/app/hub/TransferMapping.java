package com.app.hub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferMapping {

	private Dir srcDir;
	private Dir destDir;
	private Map<File, File> filesMap;
	private Map<File, File> dirsMap;
	private List<File> synchronizedFiles;
	private List<File> failureFiles;

	public TransferMapping(Dir srcDir, Dir destDir) {
		this.srcDir = srcDir;
		this.destDir = destDir;
		this.filesMap = filesMapping(srcDir, destDir);
		this.dirsMap = dirsMapping(srcDir, destDir);
		this.synchronizedFiles = new ArrayList<File>();
		this.failureFiles = new ArrayList<File>();
	}

	private Map<File, File> dirsMapping(Dir srcDir2, Dir destDir2) {
		Map<File, File> map = new HashMap<File, File>();
		List<File> srcFiles = srcDir.getEmptyDIRs();
		for (File sf : srcFiles) {
			File targetFile = target(sf);
			if(!targetFile.exists()){
				map.put(sf, targetFile);
				continue;
			}
		}
		return map;
	}

	private Map<File, File> filesMapping(Dir srcDir, Dir destDir) {
		Map<File, File> map = new HashMap<File, File>();
		List<File> srcFiles = srcDir.getAllFiles();
		for (File sf : srcFiles) {
			File targetFile = target(sf);
			if(!targetFile.exists()){
				map.put(sf, targetFile);
				continue;
			}
			FileMD5 srcFileMd5 = new FileMD5(sf);
			FileMD5 destFileMd5 = new FileMD5(targetFile);
			if(!srcFileMd5.equals(destFileMd5)){
				map.put(sf, targetFile);
				continue;
			}
			
		}
		return map;
	}

	private File target(File file) {
		String filePath = file.getAbsolutePath();
		String srcDirPath = this.srcDir.getAbsolutePath();
		String destDirPath = this.destDir.getAbsolutePath();
		return new File(filePath.replace(srcDirPath, destDirPath));
	}

	public void synchronize() {
		copy();
		delete();
	}

	private void delete() {
		// TODO Auto-generated method stub
		
	}

	public void copy() {
		copyFiles();
		copyDirs();
	}

	private void copyDirs() {
		for (Map.Entry<File, File> entry : dirsMap.entrySet()) {
			entry.getValue().mkdir();
		}
	}

	private void copyFiles() {
		for (Map.Entry<File, File> entry : filesMap.entrySet()) {
			if(copy(entry.getKey(), entry.getValue())){
				synchronizedFiles.add(entry.getKey());
			}else{
				failureFiles.add(entry.getKey());
			}
		}
	}
	
	private boolean copy(File from, File to) {
		File toFileDir = to.getParentFile();
		if(!toFileDir.exists()){
			toFileDir.mkdirs();
		}
		
		boolean success = true;
		InputStream is = null;
		OutputStream os = null;
		byte[] bytes = new byte[1024];
		int i;
		try {
			is = new FileInputStream(from);
			os = new FileOutputStream(to);
			while((i = is.read(bytes)) > 0){
				os.write(bytes, 0, i);
			}
		} catch (FileNotFoundException e) {
			close(is, os);
			e.printStackTrace();
			success = false;
			
		} catch (IOException e) {
			close(is, os);
			e.printStackTrace();
			success = false;
		}finally{
			close(is, os);
		}
		return success;
	}
	
	private void close(InputStream is, OutputStream os) {
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<File> synchronizedFiles() {
		return synchronizedFiles;
	}
}
