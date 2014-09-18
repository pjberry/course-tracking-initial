package com.bitmotif.coursetracking.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CloserHelper {
	
	public static void close(Connection connection, Statement statement) {
		close(statement);
		close(connection);
	}
	
	public static void close(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
