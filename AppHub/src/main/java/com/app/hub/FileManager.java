package com.app.hub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager {
	private final Logger logger = LoggerFactory.getLogger(FileManager.class);

	private File sourceFile;
	private File destFile;
	private final static String NO_SOURCE = "Source file or directory does not exist."; 
	private final static String NO_DEST = "Destination file or directory does not exist."; 
	private final static String SRC_IS_DIR = "Source file is directory."; 
	private final static String DIR_IS_FILE = "Destination file is not directory."; 

	// private FileInputStream is;
	// private FileOutputStream os;

	public FileManager(String source, String destination) {
		this(new File(source), new File(destination));
	}

	public FileManager(File sourceFile, File destFile) {
		this.sourceFile = sourceFile;
		this.destFile = destFile;
	}

	public void synchronize() {
		if(!this.sourceFile.exists()){
			System.out.println(NO_SOURCE);
			logger.info(NO_SOURCE);
			return ;
		}
		
		if(!this.destFile.exists()){
			System.out.println(NO_DEST);
			logger.info(NO_DEST);
			return ;
		}
		
		if(this.sourceFile.isFile() && this.destFile.isDirectory()){
			try {
				copyFileToDirectory(this.sourceFile, this.destFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		if(this.sourceFile.isFile() && this.destFile.isFile()){
//			synchronizeFile(this.sourceFile, this.destFile);
//			return ;
//		}
		
//		if(this.s){
//			
//		}
		
//		if(this.sourceFile.isFile()){
//			
//		}
		
//		List<File> pendingFiles = this.getPendingFiles();
//		for (File file : pendingFiles) {
//			copy(file);
//		}
	}

	private void copyFileToDirectory(File sourceFile, File destDir) throws FileNotFoundException{
		if(!sourceFile.isFile()){
			throw new RuntimeException(SRC_IS_DIR);
		}
		if(destDir.isFile()){
			throw new RuntimeException(DIR_IS_FILE);
		}
		
		String destFilePath = destDir.getAbsolutePath() + File.pathSeparator + sourceFile.getName();
		InputStream is = null;
		OutputStream os = null;
		byte[] bytes = new byte[1024];
		try {
			is = new FileInputStream(sourceFile);
			os = new FileOutputStream(new File(destFilePath));
			while(is.read(bytes) > 0){
				os.write(bytes);
			}
		} catch (FileNotFoundException e) {
			close(is, os);
			throw e;
		} catch (IOException e) {
			close(is, os);
		}finally{
			close(is, os);
		}
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

	private void synchronizeFile(File sourceFile, File destFile) {
		// TODO Auto-generated method stub
		
	}

	private void copy(File file) {
		String sourcePath = this.sourceFile.getAbsolutePath();
		String destPath = this.destFile.getAbsolutePath();
		String currentPath = file.getAbsolutePath();
		
		String relativePath = currentPath.split(sourcePath)[1];
		
		
		File fromDir = null;
		File toDir = null;
				
		copy(file, fromDir, toDir);
	}

	private void copy(File file, File fromDir, File toDir) {
		// TODO Auto-generated method stub
		
	}

	public List<File> getPendingFiles() {
		List<File> sourceFiles = this.getSourceFiles();
		List<File> pendingFiles = new ArrayList<File>();

		for (File file : sourceFiles) {
			if(isFileChanged(file)){
				pendingFiles.add(file);
			}
		}
		return pendingFiles;
	}

	private boolean isFileChanged(File file) {
		List<File> destFiles = this.getDestFiles();
		String destFilePath = this.destFile.getAbsolutePath() + File.pathSeparator + file.getName();
		File destFile = null;
		for (File f : destFiles) {
			if(f.getAbsolutePath().equalsIgnoreCase(destFilePath)){
				destFile = f;
			}
		}
		
		if(destFile == null){
			return true;
		}
		
		return equalsWithMD5(file, destFile);
	}

	private boolean equalsWithMD5(File file, File f) {
		if(f == null){
			return false;
		}
		
		getFileMD5(file);
		// TODO Auto-generated method stub
		return false;
	}

	private List<File> getDestFiles() {
		List<File> destFiles = new ArrayList<File>();
		if(this.destFile.isDirectory()){
			destFiles.addAll(getFilesInDir(this.destFile));
		}
		return destFiles;
	}

	private Collection<? extends File> getFilesInDir(File dir) {
		List<File> files = new ArrayList<File>();
		File[] dirFiles = dir.listFiles();
		for (File file : dirFiles) {
			if(file.isFile()){
				files.add(file);
				continue;
			}
			getFilesInDir(dir);
		}
		return files;
	}

	private List<File> getSourceFiles() {
		List<File> sourceFiles = new ArrayList<File>();
		if(this.sourceFile.isFile()){
			sourceFiles.add(this.sourceFile);
		}
		return sourceFiles;
	}

	public List<File> syschronizedFiles() {
		// TODO Auto-generated method stub
		return null;
	}
}
