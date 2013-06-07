package com.angelo;

import com.angelo.logging.db.DataCentre;

public class InitializeDatabase {
	public static void main(String[] args) {
		DataCentre dataCentre = new DataCentre();
		dataCentre.createTable();
	}
}
