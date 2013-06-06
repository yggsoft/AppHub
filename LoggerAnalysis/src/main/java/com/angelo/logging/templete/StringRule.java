package com.angelo.logging.templete;

public class StringRule implements Rule {
	private int id;
	private int templeteId;
	private String feature;
	
	public StringRule() {
	}
	
	public StringRule(String feature) {
		this.feature = feature;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFeature() {
		return feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
	}
	
	public int getTempleteId() {
		return templeteId;
	}

	public void setTempleteId(int templeteId) {
		this.templeteId = templeteId;
	}

	public boolean matches(String input) {
		if(feature == null && feature.length() == 0 ){
			return false;
		}
		
		String[] features = feature.split(";");
		for (String f : features) {
			if(feature.length() == 0) continue;
			if(!input.contains(f)) return false;
		}
		return true;
	}
}
