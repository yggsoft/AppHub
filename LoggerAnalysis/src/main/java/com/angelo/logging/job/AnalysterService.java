package com.angelo.logging.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Level;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.Job;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;

public class AnalysterService{
	private DataCentre db = new DataCentre();
	
	public void analysize(Reports reports, List<Templete> templetes,
			ExceptionFragment fragment) {
		analysize(reports, templetes, fragment, null);
	}
	
	public void analysize(Reports reports, List<Templete> templetes,
			ExceptionFragment fragment, Job job) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fragmentDate = fragment.getDate() == null ? null : dateFormat.format(fragment.getDate());
		
		for (Templete templete : templetes) {
			if(templete.matches(fragment)){
				
				if (!db.completeAnalysis(templete, fragment, job)) {
					reports.report(String.format(
							"complete analysis failed: %s (%s).",
							fragment.getId(), fragmentDate), Level.ERROR);
				} else {
					reports.report(String.format("deal with record: %s (%s).",
							fragment.getId(), fragmentDate), Level.INFO);
				}
				// match once.
				return ;
			}
		}
		
		
		if (db.skipAnalysis(fragment, job)) {
			reports.report(fragment.getId() + "(" + fragmentDate + ")"
					+ " is unknown.", Level.INFO);
		} else {
			reports.report(fragment.getId() + "(" + fragmentDate + ")"
					+ " is unknown, and skip analysis failed.", Level.ERROR);
		}
	}
}
