package com.angelo.logging.job;

import java.io.File;

import org.junit.Test;

public class LoggerExtracterTest {

	@Test
	public void test() {
		LoggerExtracter extracter = new LoggerExtracter(new File("C:\\Users\\ggyang\\Desktop\\LoggerFiles\\SourceDir"));
		extracter.run();
	}

}
