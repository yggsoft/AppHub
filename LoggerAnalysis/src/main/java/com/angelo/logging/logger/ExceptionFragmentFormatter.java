//package com.angelo.logging.logger;
//
//import java.util.List;
//
//import com.angelo.logging.util.Constants;
//
//public class ExceptionFragmentFormatter {
//	private List<ExceptionFragment> fragments;
//	private static final String HEADER = "=========================Begin=============================";
//	private static final String FOOTER = "===========================End=============================";
//	private static final String LINE = "-----------------------------------------------------------";
//	
//	public ExceptionFragmentFormatter(List<ExceptionFragment> fragments) {
//		this.fragments = fragments;
//	}
//
//	public String format() {
//		StringBuilder builder = new StringBuilder();
//		for (ExceptionFragment fragment : fragments) {
//			builder.append(getSummary(fragment) + Constants.LINE_SEPRATOR);
//		}
//		return builder.toString();
//	}
//
//	private String getSummary(ExceptionFragment fragment) {
//		String header = fragment.getId() + HEADER;
//		String footer = fragment.getId() + FOOTER;
//		StringBuilder results = new StringBuilder();
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(header);
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(fragment.getTitle());
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append("RCA:");
//		results.append(Constants.LINE_SEPRATOR);
//		results.append('\t' + fragment.getRCA());
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append("Reproduce Steps:");
//		results.append(Constants.LINE_SEPRATOR);
//		results.append('\t' + fragment.getReproduceSteps());
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append("Details:");
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(LINE);
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(fragment.getContext());
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(fragment.getDetailMessages());
//
//		results.append(Constants.LINE_SEPRATOR);
//		results.append(footer);
//
//		return results.toString();
//	}
//}
