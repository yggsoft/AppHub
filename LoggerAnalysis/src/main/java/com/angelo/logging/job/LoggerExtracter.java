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

import com.angelo.logging.db.DataCentre;
import com.angelo.logging.io.LoggerFileReader;
import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.LoggerFile;
import com.angelo.logging.util.Constants;


public class LoggerExtracter implements Runnable{
	private static final Logger LOG = LoggerFactory.getLogger(LoggerExtracter.class);
	private File inDir;
	private File outDir;
	private DataCentre db = new DataCentre();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private List<LoggerFile> completedFiles;
	
	public LoggerExtracter(File inDir) {
		this.inDir = inDir;
	}
	
	public void run() {
		LOG.info(LoggerExtracter.class.getSimpleName() + " is running...");
		LOG.info("Scanning directory: " + this.inDir.getAbsolutePath());
		while(true) {
			File[] files = this.inDir.listFiles();
			LOG.info("Total Files: " + files.length);
			
			if(files.length > 0){
				this.execute(files);
			}
			
			try {
				Thread.sleep(Constants.JOB_SLEEP);
			} catch (InterruptedException e) {
				LOG.error("thread error " + e);
			}
		}
	}
	
	public void execute(File[] files){
		completedFiles = db.getLoggerFiles();
		
		for (File file : files) {
			String fileName = file.getName();
			if(imported(file)) {
				LOG.info("Had imported log file: " + fileName);
				if(!move(file, new File(Constants.getInstance().getImportedDir()))){
					LOG.error("Error move file({}) to imported directory.", fileName);
				}
				continue ;
			}
			
			LOG.info("Parsing log file: " + fileName);
			List<ExceptionFragment> fragments = new ArrayList<ExceptionFragment>();
			try {
				fragments.addAll(new LoggerFileReader(file).read());
			} catch (FileNotFoundException e) {
				LOG.error("Logger file({}) not found: " + e, fileName);
				continue;
			} catch (IOException e) {
				LOG.error("Parsing log file({}): " + e, fileName);
				if(!move(file, new File(Constants.getInstance().getErrorDir()))){
					LOG.error("Error move file({}) to error directory.", fileName);
				}
				continue;
			}
			
			updateFragmentDate(fragments);
			LoggerFile loggerFile = getLoggerFile(file);
			if(!db.extractLogger(loggerFile, fragments)){
				LOG.error("Error extract logger.");
				if(!move(file, new File(Constants.getInstance().getErrorDir()))){
					LOG.error("Error move file({}) to error directory.", fileName);
				}
				continue;
			}
			
			if(!move(file, new File(Constants.getInstance().getArchiveDir()))){
				LOG.error("Error move file({}) to archive directory.", fileName);
			}
			completedFiles.add(loggerFile);
			LOG.info("Complete parsing log file({}).", fileName);
			
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
		}
	}
	
	private LoggerFile getLoggerFile(File file) {
		Date fileDate = null;
		String date = null;
		try {
			date = file.getName().replace("cvmedhome.log.", "").trim();
			fileDate = df.parse(date);
		} catch (ParseException e) {
			LOG.error(String.format("Parsing date: %s, %s", date, e));
		}
		return new LoggerFile(file.getName(), fileDate, new Date());
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

	private void updateFragmentDate(List<ExceptionFragment> fragments) {
		Pattern p = Pattern
				.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ExceptionFragment fragment : fragments) {
			Matcher m = p.matcher(fragment.getDetailMessages());
			if (m.find()) {
				String dateString = m.group();
				Date date = null;
				try {
					date = dateFormat.parse(dateString);
				} catch (ParseException e) {
					LOG.error("fragment({}) date can not be parsed",
							fragment.getId());
				}
				fragment.setDate(date);
			}
		}
	}

	public void setLogFilesDir(File inDir) {
		this.inDir = inDir;
	}

	public void setOutPut(File outDir) {
		this.outDir = outDir;
	}

}
