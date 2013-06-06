package com.angelo.logging.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.angelo.logging.util.Constants;

public class TextFileReader {

	private BufferedReader bfReader;

	public TextFileReader(File file) {
		try {
			bfReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public TextFileReader(String resource) {
		InputStream is = null;

		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResource(resource).openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		bfReader = new BufferedReader(new InputStreamReader(is));
	}

	public String read() {
		StringBuilder builder = new StringBuilder();
		try {
			String line = null;
			while ((line = bfReader.readLine()) != null) {
				builder.append(line.trim());
				builder.append(Constants.LINE_SEPRATOR);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(bfReader);
		}
		return builder.toString();
	}

	private void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
