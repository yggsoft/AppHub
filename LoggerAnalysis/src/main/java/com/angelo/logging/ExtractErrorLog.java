package com.angelo.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExtractErrorLog {
	private File inDir;
	private File outDir;
	private static final int ROW_Volatility = 20;
	private static final String HEADER = "=========================Begin=============================";
	private static final String FOOTER = "===========================End=============================";
	private static final String LINE   = "-----------------------------------------------------------";
	
	private static final String LINE_SEPRATOR = System
			.getProperty("line.separator");

	public void execute() throws IOException {
		File[] files = this.inDir.listFiles();
		for (File file : files) {
			readAndWrite(file);
		}
	}

	private void readAndWrite(File file) throws FileNotFoundException, IOException {
		Reader reader = null;
		BufferedReader bfReader = null;
		Writer writer = null;
		BufferedWriter bfWriter = null;
		try {
			reader = new FileReader(file);
			bfReader = new BufferedReader(reader);
			
			File wFile = new File(this.outDir.getAbsolutePath() + File.separator
					+ file.getName());

			writer = new FileWriter(wFile);
			bfWriter = new BufferedWriter(writer);

			readAndWrite(bfReader, bfWriter);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}finally{
			close(bfWriter);
			close(writer);
			close(bfReader);
			close(reader);
		}
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

	private void readAndWrite(BufferedReader bfReader, BufferedWriter bfWriter) {
		StringBuffer errorFragment = new StringBuffer();
		RowCache rowCache = new RowCache(ROW_Volatility);
		boolean begin = false;
		boolean fragmentCompleted = false;
		int count = 1;

		String line = null;
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
					String results = results(errorFragment, rowCache, count);

					System.out.println(results);
					fragmentCompleted = false;

					bfWriter.write(results);
					bfWriter.newLine();
					
					errorFragment = new StringBuffer();
					count++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String results(StringBuffer errorFragment, RowCache rowCache,
			int count) {
		String header = count + HEADER;
		String footer = count + FOOTER;
		StringBuffer results = new StringBuffer();
		results.append(header);
		
		results.append(getSummary(errorFragment));
		
		results.append(LINE_SEPRATOR);
		results.append(LINE);
		results.append(LINE_SEPRATOR);
		results.append(rowCache.clear());
		results.append(LINE_SEPRATOR);
		results.append(errorFragment);
		results.append(LINE_SEPRATOR);
		results.append(footer);
		return results.toString();
	}

	private String getSummary(StringBuffer errorFragment) {
		StringBuilder builder = new StringBuilder();
		builder.append("RCA:");
		builder.append(LINE_SEPRATOR);
		builder.append(LINE_SEPRATOR);
		
		builder.append("Reproduce Steps:");
		builder.append(LINE_SEPRATOR);
		
		builder.append("Stack Top:");
		builder.append(getRootException(errorFragment));
		builder.append(LINE_SEPRATOR);
		return builder.toString();
	}

	private String getRootException(StringBuffer errorFragment) {
		// TODO Auto-generated method stub
		//last Caused by:
		return null;
	}

	private boolean isMatchEnding(String input) {
		Pattern pattern = Pattern.compile(".*Request\\stime.*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	private boolean isMatchBeginnig(String input) {
//		Pattern pattern = Pattern.compile(".*exception.*|.*error*",
//				Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(input);
//		return matcher.find();
		
		String[] ignoreRegexs = new String[]{
				".*Error: org.apache.tapestry5.corelib.components.Error.*",
				".*Errors: org.apache.tapestry5.corelib.components.Errors.*",
				".*ExceptionReport: com.singulex.cvmedhome.presentation.pages.ExceptionReport.*",
				".*ExceptionDisplay: org.apache.tapestry5.corelib.components.ExceptionDisplay.*",
				".*ExceptionAnalyzer: DEFINED.*",
				".*ExceptionTracker: DEFINED.*",
				".*RequestExceptionHandler: DEFINED.*"
		};
		
		for (String regex : ignoreRegexs) {
			if(Pattern.matches(regex, input)){
				return false;
			}
		}
		
		Pattern pattern = Pattern.compile(".*exception.*",
				Pattern.CASE_INSENSITIVE);
		boolean b = false;
		
		Matcher matcher = pattern.matcher(input);
		if(!matcher.find()){
			pattern = Pattern.compile(".*error.*",
					Pattern.CASE_INSENSITIVE);
			b = pattern.matcher(input).find();
		}else{
			b = true;
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
