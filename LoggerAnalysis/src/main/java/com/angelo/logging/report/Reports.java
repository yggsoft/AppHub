package com.angelo.logging.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.Constants;

public class Reports {
	private static final Logger LOG = LoggerFactory.getLogger(Reports.class);
	private StringBuilder messages;

	public Reports() {
		this.messages = new StringBuilder();
	}

	public void report(String msg) {
		LOG.info(msg);
		messages.append(msg);
		messages.append(Constants.LINE_SEPRATOR);
	}

	public String report() {
		return messages.toString();
	}
}
