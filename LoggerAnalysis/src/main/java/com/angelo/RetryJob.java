package com.angelo;

import com.angelo.logging.job.RetryAnalyster;

public class RetryJob {
	public static void main(String[] args) {
		RetryAnalyster retryAnalyster = new RetryAnalyster();
		retryAnalyster.run();
	}
}
