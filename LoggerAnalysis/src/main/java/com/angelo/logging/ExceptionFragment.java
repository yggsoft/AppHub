package com.angelo.logging;

public class ExceptionFragment {
	private int index;
	private String title;
	private String RCA;
	private String reproduceSteps;
	private String rootException;
	private String context;
	private String detailMessages;
	
	public ExceptionFragment(int index, String RCA, String reproduceSteps,
			String rootException, String context, String detailMessages) {
		super();
		this.index = index;
		this.RCA = RCA;
		this.reproduceSteps = reproduceSteps;
		this.rootException = rootException;
		this.context = context;
		this.detailMessages = detailMessages;
	}
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
	
}
