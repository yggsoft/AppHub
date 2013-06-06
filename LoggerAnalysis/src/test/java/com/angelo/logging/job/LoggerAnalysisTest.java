package com.angelo.logging.job;

import org.junit.Test;

public class LoggerAnalysisTest {

	@Test
	public void executeTest() {
		LoggerAnalyster analysis = new LoggerAnalyster();
		analysis.execute();
	}

}
