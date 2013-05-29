package com.angelo.logging.templete;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.angelo.logging.ExceptionFragment;

public class Templete {
	private String category;
	private String title;
	private String RCA;
	private String reProduceSteps;
	private String templete;

	public Templete(String title, String rCA, String reProduceSteps,
			String templete) {
		super();
		this.title = title;
		RCA = rCA;
		this.reProduceSteps = reProduceSteps;
		this.templete = templete;
	}
	
	public Templete(String templete) {
		super();
		this.templete = templete;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRCA() {
		return RCA;
	}

	public void setRCA(String rCA) {
		RCA = rCA;
	}

	public String getReProduceSteps() {
		return reProduceSteps;
	}

	public void setReProduceSteps(String reProduceSteps) {
		this.reProduceSteps = reProduceSteps;
	}

	public String getTemplete() {
		return templete;
	}

	public void setTemplete(String templete) {
		this.templete = templete;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean matches(ExceptionFragment exceptionFragment) {
		Pattern pattern = Pattern.compile(this.templete, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(exceptionFragment.getDetailMessages());
		return matcher.find();
	}

}
