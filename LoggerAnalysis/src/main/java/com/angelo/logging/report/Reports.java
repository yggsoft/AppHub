package com.angelo.logging.report;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.util.Constants;

public class Reports {
	private static final Logger LOG = LoggerFactory.getLogger(Reports.class);
	private StringBuilder messages;

	public Reports() {
		this.messages = new StringBuilder();
	}

	public void report(String msg) {
		report(msg, Level.INFO);
	}
	
	public void report(String msg, Level level) {
		LOG.info(msg);
		messages.append(msg);
		messages.append(Constants.LINE_SEPRATOR);
	}

	public String report() {
		return messages.toString();
	}
}
