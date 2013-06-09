package com.angelo.logging.templete;

public class StringRule implements Rule {
	private String id;
	private String templeteId;
	private String feature;
	
	public StringRule() {
	}
	
	public StringRule(String feature) {
		this.feature = feature;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempleteId() {
		return templeteId;
	}

	public void setTempleteId(String templeteId) {
		this.templeteId = templeteId;
	}

	public String getFeature() {
		return feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
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
