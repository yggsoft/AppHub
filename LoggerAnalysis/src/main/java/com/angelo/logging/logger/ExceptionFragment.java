package com.angelo.logging.logger;

import java.util.Date;
import java.util.List;

import com.angelo.logging.templete.Templete;

public class ExceptionFragment {
	private String id;
	private String title;
	private String RCA;
	private String reproduceSteps;
	private String rootException;
	private String context;
	private String detailMessages;
	private Date date;
	private boolean analysisCompleted;
	private boolean isMatched;
	private boolean ignore;
	
	private String logfileId;
	
	private List<Templete> templete;
	
	public ExceptionFragment() {
	}
	
	public ExceptionFragment(int index, String RCA, String reproduceSteps,
			String rootException, String context, String detailMessages) {
		this.RCA = RCA;
		this.reproduceSteps = reproduceSteps;
		this.rootException = rootException;
		this.context = context;
		this.detailMessages = detailMessages;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRCA() {
		return RCA;
	}

	public void setRCA(String rCA) {
		RCA = rCA;
	}

	public String getReproduceSteps() {
		return reproduceSteps;
	}

	public void setReproduceSteps(String reproduceSteps) {
		this.reproduceSteps = reproduceSteps;
	}

	public String getRootException() {
		return rootException;
	}

	public void setRootException(String rootException) {
		this.rootException = rootException;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String getLogfileId() {
		return logfileId;
	}

	public void setLogfileId(String logfileId) {
		this.logfileId = logfileId;
	}
	
	
}
