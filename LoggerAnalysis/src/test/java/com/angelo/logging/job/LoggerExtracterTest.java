package com.angelo.logging.job;

import java.io.File;

import org.junit.Test;

import com.angelo.logging.util.Constants;

public class LoggerExtracterTest {

	@Test
	public void test() {
		LoggerExtracter extracter = new LoggerExtracter(new File(Constants.getInstance().getInDir()));
		extracter.run();
	}

}
