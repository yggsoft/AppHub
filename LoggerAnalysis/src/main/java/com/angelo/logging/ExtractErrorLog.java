package com.angelo.logging;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelo.logging.report.Reports;
import com.angelo.logging.templete.Templete;
import com.angelo.logging.templete.Templetes;


public class ExtractErrorLog {
	private static final Logger LOG = LoggerFactory.getLogger(ExtractErrorLog.class);
	private File inDir;
	private File outDir;
	private static final int ROW_VOLATILITY = 20;
	
	private static final String LINE_SEPRATOR = System.getProperty("line.separator");
	
	private static final String[] IGNORE_REGEXS = new String[]{
			".*Error: org.apache.tapestry5.corelib.components.Error.*",
			".*Errors: org.apache.tapestry5.corelib.components.Errors.*",
			".*ExceptionReport: com.singulex.cvmedhome.presentation.pages.ExceptionReport.*",
			".*ExceptionDisplay: org.apache.tapestry5.corelib.components.ExceptionDisplay.*",
			".*ExceptionAnalyzer: DEFINED.*",
			".*ExceptionTracker: DEFINED.*",
			".*RequestExceptionHandler: DEFINED.*"
	};
	
	private static final String[] BEGINNING_REGEXS = new String[]{
		".*exception.*",
		".*error.*"
	};
	
	private static final String[] END_REGEXS = new String[]{
		".*Request\\stime.*"
	};
	
	
	public void execute() throws IOException {
		LOG.info("Running...");
		File[] files = this.inDir.listFiles();
		LOG.info("Parsing log files...");
		for (File file : files) {
			LOG.info("Parsing log files: " + file.getName());
			List<ExceptionFragment> allFragments = new ArrayList<ExceptionFragment>();
			try {
				allFragments.addAll(read(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOG.info("Complete parsing log files.");
			
			Reports reports = update(allFragments);
			File wFile = new File(this.outDir.getAbsolutePath()
					+ File.separator + file.getName() + ".summary");
			new TextFile(wFile, reports.report()).print();
			
			wFile = new File(this.outDir.getAbsolutePath()
					+ File.separator + file.getName() + ".exceptions");
			new TextFile(wFile, new ExceptionFragmentFormatter(allFragments).format()).print();
		}
	}
	
	private Reports update(List<ExceptionFragment> allFragments) {
		List<Templete> templetes = Templetes.getInstance().getTempletes();
		Reports reports = new Reports();
		reports.report("Matching error records.");
		reports.report("Error records size: " + allFragments.size());
		
		boolean update = false;
		for (int i = 0; i < allFragments.size(); i++) {
			ExceptionFragment exceptionFragment = allFragments.get(i);
			for (Templete templete : templetes) {
				if(templete.matches(exceptionFragment)){
					exceptionFragment.setTitle(templete.getTitle());
					exceptionFragment.setRCA(templete.getRCA());
					exceptionFragment.setReproduceSteps(templete.getReProduceSteps());
					update = true;
					reports.report(String.format("Deal with record: %s, title: %s.", exceptionFragment.getIndex(), exceptionFragment.getTitle()));
					// to 
					break;
				}
			}
			
			if(!update){
				reports.report(exceptionFragment.getIndex() + " is unknown.");
			}
			
			update = false;
		}
		return reports;
	}

	private List<ExceptionFragment> read(File file) throws FileNotFoundException, IOException {
		Reader reader = null;
		BufferedReader bfReader = null;
		List<ExceptionFragment> fragments= null;
		try {
			reader = new FileReader(file);
			bfReader = new BufferedReader(reader);
			
			fragments = read(bfReader);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}finally{
			close(bfReader);
			close(reader);
		}
		return fragments;
	}
	
	private void close(Closeable closeable) throws IOException {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	private List<ExceptionFragment> read(BufferedReader bfReader) throws IOException {
		List<ExceptionFragment> fragments = new ArrayList<ExceptionFragment>();
		RowCache rowCache = new RowCache(ROW_VOLATILITY);
		
		boolean begin = false;
		boolean fragmentCompleted = false;
		int count = 1;
		String line = null;
		
		StringBuffer errorFragment = new StringBuffer();
		try {
			while ((line = bfReader.readLine()) != null) {
				if (isMatchBeginnig(line)) {
					begin = true;
				}
				
				if (!begin) {
					rowCache.cache(line + LINE_SEPRATOR);
				}
				
				if (begin) {
					errorFragment.append(line);
					errorFragment.append(LINE_SEPRATOR);
				}
				
				if (begin && isMatchEnding(line)) {
					begin = false;
					fragmentCompleted = true;
					
					errorFragment.append(line);
					errorFragment.append(LINE_SEPRATOR);
				}
				
				if (fragmentCompleted) {
					String context = rowCache.clear();
					
					ExceptionFragment fragment = new ExceptionFragment(count, "", "", "", context, errorFragment.toString());
					fragments.add(fragment);
					
					errorFragment = new StringBuffer();
					fragmentCompleted = false;
					count++;
				}
			}
		} catch (IOException e) {
			throw e;
		}
		
		return fragments;
	}
	
	private boolean isMatchEnding(String input) {
		return matchIgnoreCase(END_REGEXS, input);
	}

	private boolean isMatchBeginnig(String input) {
		for (String regex : IGNORE_REGEXS) {
			if(Pattern.matches(regex, input)){
				return false;
			}
		}
		
		return matchIgnoreCase(BEGINNING_REGEXS, input);
	}

	private boolean matchIgnoreCase(String[] regexs, String input) {
		boolean b = false;
		for (String regex : regexs) {
			Pattern pattern = Pattern.compile(regex,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(input);
			
			if(!matcher.find()){
				continue;
			}else{
				b = true;
				break;
			}
		}
		return b;
	}
	
	public void setLogFilesDir(File inDir) {
		this.inDir = inDir;
	}

	public void setOutPut(File outDir) {
		this.outDir = outDir;
	}

}
