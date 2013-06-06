package com.angelo.logging.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class TextFileWriter {

	private File file;
	private String content;

	public TextFileWriter(File out, String content) {
		this.file = out;
		this.content = content;
	}

	public void print() {
		Writer writer = null;
		BufferedWriter bfWriter = null;
		try {
			writer = new FileWriter(this.file);
			bfWriter = new BufferedWriter(writer);
			bfWriter.write(content);
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
}
