package com.bitmotif.coursetracking.course.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bitmotif.coursetracking.database.CloserHelper;
import com.bitmotif.coursetracking.database.Database;
import com.zaneray.coursetracking.course.Course;

public class CourseDAO {

	public List<Course> retrieveAll() {
		Connection connection = new Database().getConnection();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet resultSet  = statement.executeQuery("SELECT id, name FROM courses ORDER BY name");
			return buildCourses(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection, statement);
		}
	}

	public Course create(String courseName) {
		Connection connection = new Database().getConnection();	
		
		Integer id = new Random().nextInt();
		try {
			insertCourse(connection, id, courseName);
			return new Course(id, courseName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}

	public void update(Course course) {
		Connection connection = new Database().getConnection();
		
		try {
			updateCourse(connection, course.getId(), course.getName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}
	
	public void delete(Integer courseId) {
		Connection connection = new Database().getConnection();
		
		try {
			deleteCourse(connection, courseId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}
	
	public boolean exists(String courseName) {
		Connection connection = new Database().getConnection();
		try {
			return courseExists(connection, courseName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}
	
	public Course retrieve(Integer courseId) {
		Connection connection = new Database().getConnection();
		try {
			return retrieve(connection, courseId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);	
		}
	}
	
	private List<Course> buildCourses(ResultSet resultSet) throws SQLException {
		List<Course> courses = new ArrayList<Course>();
		while(resultSet.next()) {
			Course course = buildCourse(resultSet);
			courses.add(course);
		}
		return courses;
	}
	
	private void insertCourse(Connection connection, Integer id, String courseName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO courses SET id = ?, name = ?");
		statement.setInt(1, id);
		statement.setString(2, courseName);
		statement.executeUpdate();
		CloserHelper.close(statement);
	}
	
	private void updateCourse(Connection connection, Integer id, String courseName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE courses SET name = ? WHERE id = ?");
		statement.setString(1, courseName);
		statement.setInt(2, id);
		statement.executeUpdate();
		CloserHelper.close(statement);
	}
	
	private void deleteCourse(Connection connection, Integer id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE id = ?");
		statement.setInt(1, id);
		statement.executeUpdate();
		CloserHelper.close(statement);
	}

	private boolean courseExists(Connection connection, String courseName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses WHERE name = ?");
		try {
			statement.setString(1, courseName);
			ResultSet result = statement.executeQuery();
			return result.next();
		}
		finally {
			CloserHelper.close(statement);	
		}
	}
	
	private Course retrieve(Connection connection, Integer courseId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM courses WHERE id = ?");
		try {
			statement.setInt(1, courseId);
			ResultSet result = statement.executeQuery();
			result.next();
			return buildCourse(result);
		}
		finally {
			CloserHelper.close(statement);	
		}
	}


	private Course buildCourse(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(1);
		String name = resultSet.getString(2);
		return new Course(id, name);
	}
}
