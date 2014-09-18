package com.bitmotif.coursetracking.student.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDatabaseTestUtils {

	public static void insertStudent(Connection connection, Integer id, String firstName, String lastName, String date) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO students SET id = ?, first_name = ?, last_name = ?, birth_date = ?");
		try {
			statement.setInt(1, id);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setDate(4, Date.valueOf(date));
			statement.execute();
		} 
		finally {
			statement.close();
		}
	}
	
	public static void deleteStudent(Connection connection, Integer id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
		try {
			statement.setInt(1, id);
			statement.executeUpdate();
		} 
		finally {
			statement.close();
		}
	}
}
