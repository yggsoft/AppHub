package com.angelo.logging.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	private static final Logger LOG = LoggerFactory.getLogger(Constants.class);
	public static final String LINE_SEPRATOR = System.getProperty("line.separator");
	public static final int JOB_SLEEP = 30 * 1000;
	public static final int DB_RESULTSET_SIZE = 100;
	
	private final int rowValitility;
	
	private final String h2url;
	
	private static Constants constants = new Constants();
	private final String inDir;
	private final String outLoggerFileDir;
	private final boolean reportFileEnabled;
	private final String archiveDir;
	private final String importedDir;
	private final String errorDir;
	
	private Constants() {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/configure.properties"));
		} catch (IOException e) {
			LOG.error("initialize constants: " + e);
		}
		
		inDir = properties.get("logger.in.dir").toString();
		archiveDir = properties.get("logger.archive.dir").toString();
		importedDir = properties.get("logger.imported.dir").toString();
		errorDir = properties.get("logger.error.dir").toString();
		
		reportFileEnabled = Boolean.valueOf(properties.get("logger.report.file.enabled").toString()).booleanValue();
		outLoggerFileDir = properties.get("logger.out.dir").toString();
		h2url = properties.get("db.url").toString();
		rowValitility = Integer.valueOf(properties.get("row.volatility").toString()).intValue();
	}
	
	public static Constants getInstance(){
		return constants;
	}

	public int getRowValitility() {
		return rowValitility;
	}

	public String getH2url() {
		return h2url;
	}

	public String getInDir() {
		return inDir;
	}

	public String getOutLoggerFileDir() {
		return outLoggerFileDir;
	}

	public boolean isReportFileEnabled() {
		return reportFileEnabled;
	}

	public String getArchiveDir() {
		return archiveDir;
	}

	public String getImportedDir() {
		return importedDir;
	}

	public String getErrorDir() {
		return errorDir;
	}
	
}
