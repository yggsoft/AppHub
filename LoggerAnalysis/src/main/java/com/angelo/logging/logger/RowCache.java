package com.angelo.logging.logger;

public class RowCache {
	private int capacity;
	private String[] rows;

	public RowCache(int capacity) {
		this.capacity = capacity;
		this.rows = new String[capacity];
	}

	public void cache(String line) {
		emptyLastCell();
		rows[capacity - 1] = line;
	}

	private void emptyLastCell() {
		for (int i = 0; i < rows.length - 1; i++) {
			rows[i] = rows[i + 1];
		}
	}

	public String clear() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < rows.length; i++) {
			if(rows[i] == null) continue;
			
			builder.append(rows[i]);
			rows[i] = null;
		}
		return builder.toString();
	}
	
}
