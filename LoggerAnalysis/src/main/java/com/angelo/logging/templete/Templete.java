package com.angelo.logging.templete;

import java.util.Date;
import java.util.List;

import com.angelo.logging.logger.ExceptionFragment;

public class Templete {
	private String id;
	private int priority;
	private String category;
	private String name;
	private String title;
	private String RCA;
	private String reProduceSteps;
	private Date timestamp;
	private boolean ignore;
	private List<Rule> rules;

	public Templete() {
	}

	public Templete(String title, String rCA, String reProduceSteps,
			boolean ignore) {
		this.title = title;
		this.RCA = rCA;
		this.reProduceSteps = reProduceSteps;
		this.ignore = ignore;
	}

	public boolean matches(ExceptionFragment exceptionFragment) {
		for (Rule rule : rules) {
			if (rule.matches(exceptionFragment.getDetailMessages()))
				return true;
		}
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

}
