package com.angelo.logging.logger;

import java.util.Date;

public class LoggerFile {
	private int id;
	private String fileName;
	private Date whichDay;
	private Date importDay;
	
	public LoggerFile(String fileName) {
		this.fileName = fileName;
	}

	public LoggerFile(String fileName, Date whichDay, Date importDay) {
		this.fileName = fileName;
		this.whichDay = whichDay;
		this.importDay = importDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getWhichDay() {
		return whichDay;
	}

	public void setWhichDay(Date whichDay) {
		this.whichDay = whichDay;
	}

	public Date getImportDay() {
		return importDay;
	}

	public void setImportDay(Date importDay) {
		this.importDay = importDay;
	}
}
