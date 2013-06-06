package com.angelo.logging.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DBManagement;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.templete.Templetes;

public class LoggerAnalyster implements Runnable{
	private static final Logger LOG = LoggerFactory
			.getLogger(LoggerAnalyster.class);
	private DBManagement db = new DBManagement();
	
	public void run() {
		this.execute();
	}

	public void execute() {
		List<ExceptionFragment> fragments = null;
		while (true) {
			fragments = db.select();
			if(!fragments.isEmpty()){
				update(fragments);
				continue ;
			}
			try {
				Thread.sleep(300 * 1000);
			} catch (InterruptedException e) {
				LOG.error("" + e);
			}
		}
	}

	private Reports update(List<ExceptionFragment> allFragments) {
		List<Templete> templetes = Templetes.getInstance().getTempletes();
		Reports reports = new Reports();
		reports.report("Matching error records.");
		reports.report("Error records size: " + allFragments.size());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean update = false;
		for (int i = 0; i < allFragments.size(); i++) {
			ExceptionFragment fragment = allFragments.get(i);
			for (Templete templete : templetes) {
				if(templete.matches(fragment)){
					fragment.setTitle(templete.getTitle());
					fragment.setRCA(templete.getRCA());
					fragment.setReproduceSteps(templete.getReProduceSteps());
					update = true;
					reports.report(String.format("Deal with record: %s (%s), title: %s.", 
							fragment.getId(), fragment.getDate() == null ? null : dateFormat.format(fragment.getDate()), fragment.getTitle()));
					// to 
					fragment.setAnalysisCompleted(true);
					fragment.setMatched(true);
					db.update(fragment);
					db.analysize(templete, fragment);
					break;
				}
			}
			
			if(!update){
				reports.report(fragment.getId() + " is unknown.");
			}
			
			update = false;
			
			fragment.setMatched(true);
			db.update(fragment);
		}
		return reports;
	}
}
