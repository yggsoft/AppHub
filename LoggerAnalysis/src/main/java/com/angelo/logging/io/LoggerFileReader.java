package com.angelo.logging.io;

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

import com.angelo.logging.logger.ExceptionFragment;
import com.angelo.logging.logger.RowCache;
import com.angelo.logging.util.Constants;

public class LoggerFileReader {
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

	private File file;
	
	public LoggerFileReader(File file) {
		this.file = file;
	}
	
	public List<ExceptionFragment> read()
			throws FileNotFoundException, IOException {
		Reader reader = null;
		BufferedReader bfReader = null;
		List<ExceptionFragment> fragments = null;
		try {
			reader = new FileReader(file);
			bfReader = new BufferedReader(reader);

			fragments = read(bfReader);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
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
		RowCache rowCache = new RowCache(Constants.getInstance().getRowValitility());
		
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
					rowCache.cache(line + Constants.LINE_SEPRATOR);
				}
				
				if (begin) {
					errorFragment.append(line);
					errorFragment.append(Constants.LINE_SEPRATOR);
				}
				
				if (begin && isMatchEnding(line)) {
					begin = false;
					fragmentCompleted = true;
					
					errorFragment.append(line);
					errorFragment.append(Constants.LINE_SEPRATOR);
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
}
