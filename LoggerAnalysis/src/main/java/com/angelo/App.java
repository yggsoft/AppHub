package com.angelo;

import java.io.File;

import com.angelo.logging.job.LoggerAnalyster;
import com.angelo.logging.job.LoggerExtracter;
import com.angelo.logging.job.RetryAnalyster;
import com.angelo.logging.util.Constants;

public class App {
	public static void main(String[] args) {
	    System.setProperty("log4j.configuration", "conf/log4j.properties");
	    
		StartH2.main(new String[]{"-tcpAllowOthers"});
		
		if(args.length > 0 && args[0].equalsIgnoreCase("init")){
			InitializeDatabase.main(null);
		}
		
		LoggerExtracter extracter = new LoggerExtracter(new File(Constants.getInstance().getInDir()));
		
		Thread extractThread = new Thread(extracter);
		Thread analysisThread = new Thread(new LoggerAnalyster());
		Thread retryThread = new Thread(new RetryAnalyster());
		extractThread.setName("Extracter");
		analysisThread.setName("Analyster");
		retryThread.setName("RetryAnalyster");
		
//		extractThread.start();
		analysisThread.start();
		retryThread.start();
	}
}
