package com.angelo.logging;

import java.io.File;

import org.junit.Test;

import com.angelo.logging.job.LoggerExtracter;

public class ExtractErrorLogTest {

//	@Test
	public void filesOneToOneTest() {
		LoggerExtracter errorLog = new LoggerExtracter();
		File inDir = new File(Thread.currentThread().getContextClassLoader().getResource("LogFile").getPath());
		errorLog.setLogFilesDir(inDir);
		File outDir = new File(Thread.currentThread().getContextClassLoader().getResource("LogFiles_Results").getPath());
		errorLog.setOutPut(outDir);
		
//		try {
//			errorLog.execute();
//		} catch (IOException e) {
//			Assert.assertEquals(true, false);
//		}
	}

//	@Test
	public void filesManyToOneTest() {
		
	}
	
	@Test
	public void april() {
		String name = "April";
		LoggerExtracter errorLog = new LoggerExtracter();
		File inDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
//	@Test
	public void march() {
		String name = "March";
		LoggerExtracter errorLog = new LoggerExtracter();
		File inDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
	@Test
	public void feb() {
		String name = "Feb";
		LoggerExtracter errorLog = new LoggerExtracter();
		File inDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name);
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
}
