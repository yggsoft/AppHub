package com.angelo.logging.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.templete.Templetes;
import com.angelo.logging.util.Constants;

public class LoggerAnalyster implements Runnable{
	private static final Logger LOG = LoggerFactory
			.getLogger(LoggerAnalyster.class);
	private DataCentre db = new DataCentre();
	private AnalysterService analysterService = new AnalysterService();
	
	public void run() {
		this.execute();
	}

	public void execute() {
		LOG.info(LoggerAnalyster.class.getSimpleName() + " is running...");
		List<ExceptionFragment> fragments = null;
		while (true) {
			fragments = db.getUnAnalysizedFragments();
			if(!fragments.isEmpty()){
				update(fragments);
				continue ;
			}
			try {
				Thread.sleep(Constants.JOB_SLEEP);
			} catch (InterruptedException e) {
				LOG.error("thread error " + e);
			}
		}
	}

	private Reports update(List<ExceptionFragment> fragments) {
		Reports reports = new Reports();
		reports.report("Analysizing records.");
		reports.report("Records size: " + fragments.size());
		
		List<Templete> templetes = Templetes.getInstance().getTempletes();
		for (int i = 0; i < fragments.size(); i++) {
			analysterService.analysize(reports, templetes, fragments.get(i));
		}
		return reports;
	}
}
