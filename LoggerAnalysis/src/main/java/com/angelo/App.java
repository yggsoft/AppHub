package com.angelo;

import java.io.File;
import java.io.IOException;

import com.angelo.logging.ExtractErrorLog;

public class App {
	public static void main(String[] args) {
		ExtractErrorLog errorLog = new ExtractErrorLog();
		String inDir = args[0];
		String outDir = args[1];
		errorLog.setLogFilesDir(new File(inDir));
		errorLog.setOutPut(new File(outDir));
		try {
			errorLog.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
