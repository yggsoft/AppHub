package com.angelo.logging;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.angelo.logging.ExtractErrorLog;

public class ExtractErrorLogTest {

	@Test
	public void fileOneToOneTest() {
		ExtractErrorLog errorLog = new ExtractErrorLog();
		File inDir = new File("C:/Users/ggyang/Desktop/LogFiles");
		errorLog.setLogFilesDir(inDir);
		File outDir = new File("C:/Users/ggyang/Desktop/LogFiles_Results");
		errorLog.setOutPut(outDir);
		
		try {
			errorLog.execute();
		} catch (IOException e) {
			Assert.assertEquals(true, false);
		}
	}

	// @Test
	public void fileManyToOneTest() {
	}

}
