package com.angelo.logging.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.angelo.logging.Constants;

public class TextFileReader {

	private File file;

	public TextFileReader(File file) {
		this.file = file;
	}

	public String read() {
		Reader reader = null;
		BufferedReader bfReader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new FileReader(file);
			bfReader = new BufferedReader(reader);

			String line = null;
			while ((line = bfReader.readLine()) != null) {
				builder.append(line.trim());
				builder.append(Constants.LINE_SEPRATOR);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(bfReader);
			close(reader);
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
