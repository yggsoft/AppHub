package com.angelo.logging.db;

import java.util.UUID;

public class IDGenerator {
	public static String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
