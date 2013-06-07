package com.angelo.logging.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionMgr {

	public static void beginTansction(Connection connection) {
		if(connection == null) throw new DataAccessException("can not get database connection.");
		
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	public static void commitAndClose(Connection connection) {
		if(connection == null) return ;
		
		try {
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			close(connection);
		}
	}

	public static void rollbackAndClose(Connection connection) {
		if(connection == null) return ;
		
		try {
			connection.rollback();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			close(connection);
		}
	}

	private static void close(Connection connection) {
		if(connection == null) return ;
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}
}
