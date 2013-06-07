package com.angelo.logging.templete;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.util.Constants;

public class Templetes {
	private static final Logger LOG = LoggerFactory.getLogger(Templetes.class);
	private List<Templete> templetes;
	private String templetesInfo;
	
	private static Templetes t = new Templetes();
	private DataCentre db = new DataCentre();
	
	private Templetes() {
		if(templetes == null){
			templetes = db.getTemplates();
		}
	}

	public static Templetes getInstance(){
		return t;
	}
	
	public List<Templete> getTempletes() {
		LOG.info(getTempletesInfo());
		return templetes;
	}
	
	public String getTempletesInfo(){
		if(templetesInfo != null){
			return templetesInfo;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("Avaibale templetes:");
		for (int i = 0; i < templetes.size(); i++) {
			builder.append(Constants.LINE_SEPRATOR);
			builder.append(i + "   "+templetes.get(i).getTitle());
		}
		
		templetesInfo = builder.toString();
		return templetesInfo;
	}
}
