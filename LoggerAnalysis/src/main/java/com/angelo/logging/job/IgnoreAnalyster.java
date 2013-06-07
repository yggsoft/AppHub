package com.angelo.logging.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;

public class IgnoreAnalyster implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(IgnoreAnalyster.class);
	private DataCentre db = new DataCentre();

	public void run() {
		this.execute();
	}

	public void execute() {
		List<ExceptionFragment> fragments = null;
		while (true) {
			fragments = db.selectNotMatchAndIgnoreEx();
			if(!fragments.isEmpty()){
				update(fragments);
				continue;
			}
			try {
				Thread.sleep(300 * 1000);
			} catch (InterruptedException e) {
				LOG.error("" + e);
			}
		}
	}

	private Reports update(List<ExceptionFragment> fragments) {
		List<Templete> templetes = db.getIgnoreTempletes();
		Reports reports = new Reports();
		reports.report("Matching error records.");
		reports.report("Error records size: " + fragments.size());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean update = false;
		for (int i = 0; i < fragments.size(); i++) {
			ExceptionFragment fragment = fragments.get(i);
			for (Templete templete : templetes) {
				if(templete.matches(fragment)){
					fragment.setTitle(templete.getTitle());
					fragment.setRCA(templete.getRCA());
					fragment.setReproduceSteps(templete.getReProduceSteps());
					update = true;
					reports.report(String.format(fragment.getId() + ": Deal with record: %s (%s), title: %s.", 
							fragment.getId(), fragment.getDate() == null ? null : dateFormat.format(fragment.getDate()), fragment.getTitle()));
					// to 
					fragment.setAnalysisCompleted(true);
					fragment.setMatched(true);
					fragment.setIgnore(true);
					db.skipAnalysis(fragment);
					db.completeAnalysis(templete, fragment);
					break;
				}
			}
			
			if(!update){
				reports.report(fragment.getId() + " is unknown.");
			}
			
			update = false;
			
			fragment.setMatched(true);
			db.skipAnalysis(fragment);
		}
		return reports;
	}
}
