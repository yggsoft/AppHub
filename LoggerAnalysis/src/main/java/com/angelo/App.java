package com.angelo;

import java.io.File;

import com.angelo.logging.job.LoggerAnalyster;
import com.angelo.logging.job.LoggerExtracter;
import com.angelo.logging.util.Constants;

public class App {
	public static void main(String[] args) {
	    System.setProperty("log4j.configuration", "conf/log4j.properties");
	    
		StartH2.main(new String[]{"-tcpAllowOthers"});
		
		if(args.length > 0 && args[0].equalsIgnoreCase("init")){
			Initialize.main(null);
		}
		
		LoggerExtracter errorLog = new LoggerExtracter();
		errorLog.setLogFilesDir(new File(Constants.getInstance().getInDir()));
		errorLog.setOutPut(new File(Constants.getInstance().getOutLoggerFileDir()));
		
		Thread extractThread = new Thread(errorLog);
		extractThread.setName("Extract");
		Thread analysisThread = new Thread(new LoggerAnalyster());
		analysisThread.setName("Analysis");
		
		extractThread.start();
		analysisThread.start();
	}
}
