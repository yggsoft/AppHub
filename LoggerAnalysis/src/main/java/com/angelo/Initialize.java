package com.angelo;

import com.angelo.logging.db.DBManagement;

public class Initialize {
	public static void main(String[] args) {
		DBManagement dBManagement = new DBManagement();
		dBManagement.createTable();
	}
}
