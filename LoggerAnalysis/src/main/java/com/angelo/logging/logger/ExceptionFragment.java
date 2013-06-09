package com.angelo.logging.logger;

import java.util.Date;
import java.util.List;

import com.angelo.logging.templete.Templete;

public class ExceptionFragment {
	private String id;
	private String context;
	private String detailMessages;
	private Date date;
	private boolean analysisCompleted;
	private boolean isMatched;
	private String logfileId;
	
	private List<Templete> templete;
	
	public ExceptionFragment() {
	}
	
	public ExceptionFragment(String context, String detailMessages) {
		this.context = context;
		this.detailMessages = detailMessages;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetailMessages() {
		return detailMessages;
	}
	
	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public void setDetailMessages(String detailMessages) {
		this.detailMessages = detailMessages;
	}

	public boolean isAnalysisCompleted() {
		return analysisCompleted;
	}


	public void setAnalysisCompleted(boolean analysisCompleted) {
		this.analysisCompleted = analysisCompleted;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Templete> getTemplete() {
		return templete;
	}

	public void setTemplete(List<Templete> templete) {
		this.templete = templete;
	}

	public boolean isMatched() {
		return isMatched;
	}

	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}

	public String getLogfileId() {
		return logfileId;
	}

	public void setLogfileId(String logfileId) {
		this.logfileId = logfileId;
	}
	
	
}
