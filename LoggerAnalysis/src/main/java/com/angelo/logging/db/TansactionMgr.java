package com.angelo.logging.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TansactionMgr {

	public static void beginTansction(Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	public static void commitAndClose(Connection conn) {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			close(conn);
		}
	}

	public static void rollbackAndClose(Connection conn) {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} finally {
			close(conn);
		}
	}

	private static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
		}
	}
}
