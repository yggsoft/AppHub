package com.angelo.logging.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.Job;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.util.Constants;

public class RetryAnalyster implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(RetryAnalyster.class);
	private DataCentre db = new DataCentre();
	private Job job;
	private AnalysterService analysterService = new AnalysterService();

	public void run() {
		this.execute();
	}

	public void execute() {
		LOG.info(RetryAnalyster.class.getSimpleName() + " is running...");
		List<ExceptionFragment> fragments = null;
		job = db.getRetryJob(RetryAnalyster.class.getSimpleName());
		if(job == null) {
			job = db.initRestryJob(RetryAnalyster.class.getSimpleName());
		}
		
		if(job == null){
			throw new RuntimeException("initialize Retry job fialure.");
		}
			
		while (true) {
			fragments = db.getRetryAnalysizedFragments(job);
			if(!fragments.isEmpty()){
				update(fragments);
				continue;
			}
			try {
				Thread.sleep(Constants.JOB_SLEEP);
				job = db.nextRetry(job);
			} catch (InterruptedException e) {
				LOG.error("thread error " + e);
			}
		}
	}

	private Reports update(List<ExceptionFragment> fragments) {
		Reports reports = new Reports();
		reports.report("Analysizing records.");
		reports.report("Records size: " + fragments.size());
		
		List<Templete> templetes = db.getNewTempletes(null);
		
		for (int i = 0; i < fragments.size(); i++) {
			ExceptionFragment fragment = fragments.get(i);
//			List<Templete> templetes = db.getNewTempletes(fragment);
			analysterService.analysize(reports, templetes, fragment, job);
		}
		return reports;
	}
}
