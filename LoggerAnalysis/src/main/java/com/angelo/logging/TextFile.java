package com.angelo.logging;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class TextFile {

	private File file;
	private List<ExceptionFragment> fragments;
	private static final String HEADER = "=========================Begin=============================";
	private static final String FOOTER = "===========================End=============================";
	private static final String LINE   = "-----------------------------------------------------------";
	private static final String LINE_SEPRATOR = System.getProperty("line.separator");

	public TextFile(File out, List<ExceptionFragment> allFragments) {
		this.file = out;
		this.fragments = allFragments;
	}

	public void print() {
		Writer writer = null;
		BufferedWriter bfWriter = null;
		try {
			writer = new FileWriter(this.file);
			bfWriter = new BufferedWriter(writer);
			for (ExceptionFragment fragment : fragments) {
				write(bfWriter, fragment);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				close(bfWriter);
				close(writer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public void write(BufferedWriter bfWriter, ExceptionFragment fragment) {
		try {
			bfWriter.write(getSummary(fragment));
			bfWriter.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getSummary(ExceptionFragment fragment) {
		String header = fragment.getIndex() + HEADER;
		String footer = fragment.getIndex()  + FOOTER;
		StringBuilder results = new StringBuilder();
		
		results.append(LINE_SEPRATOR);
		results.append(header);
		
		results.append(LINE_SEPRATOR);
		results.append(fragment.getTitle());
		
		results.append(LINE_SEPRATOR);
		results.append("RCA:");
		results.append(LINE_SEPRATOR);
		results.append('\t'+fragment.getRCA());
		
		results.append(LINE_SEPRATOR);
		results.append("Reproduce Steps:");
		results.append(LINE_SEPRATOR);
		results.append('\t'+fragment.getReproduceSteps());
		
		// results.append(LINE_SEPRATOR);
		// results.append("Stack Top:");
		// results.append(LINE_SEPRATOR);
		// results.append(fragment.getRootException());
		results.append(LINE_SEPRATOR);
		results.append("Details:");
		
		results.append(LINE_SEPRATOR);
		results.append(LINE);
		results.append(LINE_SEPRATOR);
		results.append(fragment.getContext());
		results.append(LINE_SEPRATOR);
		results.append(fragment.getDetailMessages());
		
		results.append(LINE_SEPRATOR);
		results.append(footer);
		
		return results.toString();
	}
}
