package com.angelo;

import java.sql.SQLException;

import org.h2.tools.Server;


public class StartH2 {
	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.main(args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
