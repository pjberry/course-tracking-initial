package com.bitmotif.coursetracking.course.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDatabaseTestUtils {

	public static void insertCourse(Connection connection, Integer id, String courseName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO courses SET id = ?, name = ?");
		statement.setInt(1, id);
		statement.setString(2, courseName);
		statement.execute();
	}
	
	public static void deleteCourse(Connection connection, Integer id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE id = ?");
		statement.setInt(1, id);
		statement.executeUpdate();
	}
}
