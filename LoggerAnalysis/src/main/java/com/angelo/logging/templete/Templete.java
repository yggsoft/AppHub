package com.angelo.logging.templete;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.angelo.logging.logger.ExceptionFragment;

public class Templete {
	private int id;
	private int priority;
	private String category;
	private String name;
	private String title;
	private String RCA;
	private String reProduceSteps;
	private String templete;
	private Date timestamp;
	private Pattern pattern;
	private boolean ignore;
	
	private List<Rule> rules;
	
	public Templete(String title, String rCA, String reProduceSteps,
			String templete) {
		this.title = title;
		RCA = rCA;
		this.reProduceSteps = reProduceSteps;
		this.templete = templete;
		this.pattern = Pattern.compile(this.templete, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	}
	
	public Templete(String title, String rCA, String reProduceSteps,
			String templete, boolean ignore) {
		 this.title = title;
		RCA = rCA;
		this.reProduceSteps = reProduceSteps;
		this.templete = templete;
		this.ignore = ignore;
		this.pattern = Pattern.compile(this.templete, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	}
	
	public Templete(String templete) {
		super();
		this.templete = templete;
		this.pattern = Pattern.compile(this.templete, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean matches(ExceptionFragment exceptionFragment) {
		// return pattern.matcher(exceptionFragment.getDetailMessages()).find();
		
		for (Rule rule : rules) {
			if(rule.matches(exceptionFragment.getDetailMessages())) return true;
		}
		return false;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ingore) {
		this.ignore = ingore;
	}
	
}
