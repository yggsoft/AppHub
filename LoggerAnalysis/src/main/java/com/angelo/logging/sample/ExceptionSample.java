package com.angelo.logging.sample;

import java.io.File;
import java.util.regex.Matcher;

import com.angelo.logging.Constants;
import com.angelo.logging.io.TextFileReader;
import com.angelo.logging.templete.Templete;

public class ExceptionSample {
	private File file;

	public ExceptionSample(File sampleFile) {
		this.file = sampleFile;
	}

	public Templete getTemplete() {
		TextFileReader reader = new TextFileReader(this.file);
		String content = reader.read();
		// Pattern p = Pattern.compile("\\(.*\\)", Pattern.MULTILINE |
		// Pattern.DOTALL);
		// Matcher m = p.matcher(content);
		// StringBuffer sf = new StringBuffer();
		// m.appendReplacement(sf, "");

		content = content.replaceAll("(?m)\\(.*\\)", ".*");
		content = content.replaceAll("(?m)" + Constants.LINE_SEPRATOR, ".*");
//		content = content.replaceAll("(?m)" + Constants.LINE_SEPRATOR, ".*\n");
		content = content.replaceAll("(?m)\\$", Matcher.quoteReplacement("\\$"));
		content = ".*" + content + ".*";
		return new Templete(content);
	}
}
