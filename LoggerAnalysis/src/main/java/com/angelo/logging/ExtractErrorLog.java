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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.angelo.logging.templete.Templete;
import com.angelo.logging.templete.Templetes;


public class ExtractErrorLog {
	private File inDir;
	private File outDir;
	private static final int ROW_VOLATILITY = 20;
	private static final String HEADER = "=========================Begin=============================";
	private static final String FOOTER = "===========================End=============================";
	private static final String LINE   = "-----------------------------------------------------------";
	
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
		File[] files = this.inDir.listFiles();
		for (File file : files) {
			readAndWrite(file);
		}
	}
	
	public void execute(File out){
		File[] files = this.inDir.listFiles();
		List<ExceptionFragment> allFragments = new ArrayList<ExceptionFragment>();
		for (File file : files) {
			try {
				allFragments.addAll(read(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		allFragments = cleanup(allFragments);
		update(allFragments);
		new TextFile(out, allFragments).print();
	}
	
	private void update(List<ExceptionFragment> allFragments) {
		List<Templete> templetes = Templetes.getInstance().getTempletes();

		boolean update = false;
		for (int i = 0; i < allFragments.size(); i++) {
			ExceptionFragment exceptionFragment = allFragments.get(i);
			for (Templete templete : templetes) {
				if(templete.matches(exceptionFragment)){
					exceptionFragment.setTitle(templete.getTitle());
					exceptionFragment.setRCA(templete.getRCA());
					exceptionFragment.setReproduceSteps(templete.getReProduceSteps());
					update = true;
					System.out.println(i + " update.");
				}
			}
			
			if(!update){
				System.out.println(i + " no update.");
			}
			
			update = false;
		}
	}

//	private List<ExceptionFragment> cleanup(List<ExceptionFragment> allFragments) {//similarity comparison
//		
//		List<Templete> templetes = Templetes.getInstance().getTempletes();
//		List<ExceptionFragment> fragments = new ArrayList<ExceptionFragment>();
//		
//		for (ExceptionFragment exceptionFragment : allFragments) {
//			for (Templete templete : templetes) {
//				if(templete.matches(exceptionFragment)){
//					;
//				}
//			}
//		}
//		return fragments;
//	}

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
	
	private void readAndWrite(BufferedReader bfReader, BufferedWriter bfWriter) {
		StringBuffer errorFragment = new StringBuffer();
		RowCache rowCache = new RowCache(ROW_VOLATILITY);
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
		results.append("Details:");
		
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
		builder.append(LINE_SEPRATOR);
		builder.append("RCA:");
		builder.append(LINE_SEPRATOR);
		builder.append(LINE_SEPRATOR);
		
		builder.append("Reproduce Steps:");
		builder.append(LINE_SEPRATOR);
		
//		builder.append("Stack Top:");
//		builder.append(getRootException(errorFragment));
//		builder.append(LINE_SEPRATOR);
		return builder.toString();
	}

	private String getRootException(StringBuffer errorFragment) {
		// TODO Auto-generated method stub
		//last Caused by:
		return null;
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
