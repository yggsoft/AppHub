package com.angelo.logging;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.angelo.logging.ExtractErrorLog;

public class ExtractErrorLogTest {

//	@Test
	public void filesOneToOneTest() {
		ExtractErrorLog errorLog = new ExtractErrorLog();
		File inDir = new File(Thread.currentThread().getContextClassLoader().getResource("LogFiles").getPath());
		errorLog.setLogFilesDir(inDir);
		File outDir = new File(Thread.currentThread().getContextClassLoader().getResource("LogFiles_Results").getPath());
		errorLog.setOutPut(outDir);
		
		try {
			errorLog.execute();
		} catch (IOException e) {
			Assert.assertEquals(true, false);
		}
	}

//	@Test
	public void filesManyToOneTest() {
		
	}
	
	@Test
	public void april() {
		String name = "April";
		ExtractErrorLog errorLog = new ExtractErrorLog();
		File inDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
		try {
			errorLog.execute();
		} catch (IOException e) {
			Assert.assertEquals(true, false);
		}
	}
	
//	@Test
	public void march() {
		String name = "March";
		ExtractErrorLog errorLog = new ExtractErrorLog();
		File inDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
		try {
			errorLog.execute();
		} catch (IOException e) {
			Assert.assertEquals(true, false);
		}
	}
	
//	@Test
	public void feb() {
		String name = "April";
		ExtractErrorLog errorLog = new ExtractErrorLog();
		File inDir = new File("C:/Users/ggyang/Desktop/All logger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/All logger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
		try {
			errorLog.execute();
		} catch (IOException e) {
			Assert.assertEquals(true, false);
		}
	}
	
}
