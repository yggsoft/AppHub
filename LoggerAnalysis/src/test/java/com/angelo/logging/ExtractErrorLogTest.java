package com.angelo.logging;

import java.io.File;

import org.junit.Test;

import com.angelo.logging.job.LoggerExtracter;

public class ExtractErrorLogTest {

	@Test
	public void filesOneToOneTest() {
		LoggerExtracter errorLog = new LoggerExtracter(new File("C:\\Documents and Settings\\Angelo\\Desktop\\LogFiles"));
		File outDir = new File(Thread.currentThread().getContextClassLoader().getResource("LogFiles_Results").getPath());
		errorLog.setOutPut(outDir);
		
//		try {
			errorLog.run();
//		} catch (IOException e) {
//			Assert.assertEquals(true, false);
//		}
	}

//	@Test
	public void filesManyToOneTest() {
		
	}
	
//	@Test
	public void april() {
		String name = "April";
		LoggerExtracter errorLog = new LoggerExtracter(new File("C:/Users/ggyang/Desktop/AllLogger/"+name));
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
//	@Test
	public void march() {
		String name = "March";
		LoggerExtracter errorLog = new LoggerExtracter(new File("C:/Users/ggyang/Desktop/AllLogger/"+name));
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
//	@Test
	public void feb() {
		String name = "Feb";
		LoggerExtracter errorLog = new LoggerExtracter(new File("C:/Users/ggyang/Desktop/AllLogger/"+name));
		File outDir = new File("C:/Users/ggyang/Desktop/AllLogger/"+name+"_Results");
		errorLog.setOutPut(outDir);
		
//			errorLog.execute();
	}
	
}
