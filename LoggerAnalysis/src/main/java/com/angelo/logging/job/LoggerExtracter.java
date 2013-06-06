package com.angelo.logging.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.db.DBManagement;
import com.angelo.logging.io.LoggerFileReader;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.LoggerFile;
import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.templete.Templetes;
import com.angelo.logging.util.Constants;


public class LoggerExtracter implements Runnable{
	private static final Logger LOG = LoggerFactory.getLogger(LoggerExtracter.class);
	private File inDir;
	private File outDir;
	private DBManagement db = new DBManagement();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private List<LoggerFile> completedFiles;
	
	public void run() {
		LOG.info("Running...");
		LOG.info("Scanning directory: " + this.inDir.getAbsolutePath());
		while(true) {
			File[] files = this.inDir.listFiles();
			LOG.info("Scan log files...");
			LOG.info("Total Files: " + files.length);
			
			if(files.length > 0){
				this.execute(files);
			}
			
			try {
				Thread.sleep(300 * 1000);
			} catch (InterruptedException e) {
				LOG.error("Scan log files" + e);
			}
		}
	}
	
	public void execute(File[] files){
		completedFiles = db.getLoggerFiles();
		
		for (File file : files) {
			if(imported(file)) {
				LOG.info("Had imported log file: " + file.getName());
				move(file, new File(Constants.getInstance().getImportedDir()));
				continue ;
			}
			
			LOG.info("Parsing log file: " + file.getName());
			List<ExceptionFragment> allFragments = new ArrayList<ExceptionFragment>();
			try {
				allFragments.addAll(new LoggerFileReader(file).read());
			} catch (FileNotFoundException e) {
				LOG.error("Logger file not found: " + e);
			} catch (IOException e) {
				LOG.error("Parsing log file: " + e);
				move(file, new File(Constants.getInstance().getErrorDir()));
			}
			LOG.info("Complete parsing log file: " + file.getName());
			move(file, new File(Constants.getInstance().getArchiveDir()));
			
			updateExceptionDate(allFragments);
			
//			Reports reports = update(allFragments);
//			
//			File wFile = new File(this.outDir.getAbsolutePath()
//					+ File.separator + file.getName() + ".summary");
//			// produce summary files.
//			new TextFileWriter(wFile, reports.report()).print();
//			
//			wFile = new File(this.outDir.getAbsolutePath()
//					+ File.separator + file.getName() + ".exceptions");
//			
//			//produce details files.
//			new TextFileWriter(wFile, new ExceptionFragmentFormatter(allFragments).format()).print();
			insertDB(allFragments);
			
			Date fileDate = null;
			String date = null;
			try {
				date = file.getName().replace("cvmedhome.log.", "").trim();
				fileDate = df.parse(date);
			} catch (ParseException e) {
				LOG.error(String.format("Parsing date: %s, %s", date, e));
			}
			
			LoggerFile loggerFile = new LoggerFile(file.getName(), fileDate, new Date());
			db.insert(loggerFile);
			completedFiles.add(loggerFile);
		}
	}
	
	private boolean move(File file, File destDir) {
		if(!destDir.exists()){
			destDir.mkdirs();
		}
		
		File to = new File(destDir.getAbsolutePath() + File.separator + file.getName());
		
		boolean success = true;
		InputStream is = null;
		OutputStream os = null;
		byte[] bytes = new byte[1024];
		int i;
		try {
			is = new FileInputStream(file);
			os = new FileOutputStream(to);
			while((i = is.read(bytes)) > 0){
				os.write(bytes, 0, i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
			
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
		}finally{
			close(is, os);
		}
		if(success){
			file.delete();
		}
		return success;
	}
	
	private void close(InputStream is, OutputStream os) {
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean imported(File file) {
		for (LoggerFile loggerFile : completedFiles) {
			if(loggerFile.getFileName().equalsIgnoreCase(file.getName())){
				return true;
			}
		}
		return false;
	}

	private void insertDB(List<ExceptionFragment> fragments){
		for (ExceptionFragment fragment: fragments) {
			db.insert(fragment);
		}
	}
	
	private void updateExceptionDate(List<ExceptionFragment> allFragments) {
		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ExceptionFragment exceptionFragment : allFragments) {
			Matcher m =  p.matcher(exceptionFragment.getDetailMessages());
			if(m.find()){
				String dateString = m.group();
				Date date = null;
				try {
					date = dateFormat.parse(dateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				exceptionFragment.setDate(date);
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
							fragment.getId(), dateFormat.format(fragment.getDate()), fragment.getTitle()));
					// to 
					break;
				}
			}
			
			if(!update){
				reports.report(fragment.getId() + " is unknown.");
			}
			
			update = false;
		}
		return reports;
	}

	public void setLogFilesDir(File inDir) {
		this.inDir = inDir;
	}

	public void setOutPut(File outDir) {
		this.outDir = outDir;
	}

}
