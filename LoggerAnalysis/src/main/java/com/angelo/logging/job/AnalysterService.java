package com.angelo.logging.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;

public class AnalysterService{
	private DataCentre db = new DataCentre();
	
	public void analysize(Reports reports, List<Templete> templetes,
			ExceptionFragment fragment) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Templete templete : templetes) {
			if(templete.matches(fragment)){
				
				if(!db.completeAnalysis(templete, fragment)) {
					reports.report(String.format("complete analysis failed: %s (%s).",
							fragment.getId(), 
							fragment.getDate() == null ? null : dateFormat.format(fragment.getDate())));
				}else {
					reports.report(String.format("deal with record: %s (%s).",
							fragment.getId(), 
							fragment.getDate() == null ? null : dateFormat.format(fragment.getDate())));
				}
				// match once.
				return ;
			}
		}
		
		
		if(db.skipAnalysis(fragment)) {
			reports.report(fragment.getId() + "("+fragment.getDate()+")" + " is unknown.");
		}else {
			reports.report(fragment.getId() + "("+fragment.getDate()+")" + " is unknown, and skip analysis failed.");
		}
	}
}
