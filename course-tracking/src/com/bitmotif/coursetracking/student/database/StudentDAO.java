package com.bitmotif.coursetracking.student.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bitmotif.coursetracking.database.CloserHelper;
import com.bitmotif.coursetracking.database.Database;
import com.bitmotif.coursetracking.student.Student;


public class StudentDAO {

	public Student retrieve(Integer id) {
		Connection connection = new Database().getConnection();
		PreparedStatement statement = null;

		try {		
			statement = connection.prepareStatement("SELECT id, first_name, last_name, birth_date FROM students WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery(); 
			resultSet.next();
			return buildStudent(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection, statement);
		}
	}

	public List<Student> retreiveAll() {
		Connection connection = new Database().getConnection();
		Statement statement = null;

		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id, first_name, last_name, birth_date FROM students ORDER BY last_name");
			return buildStudents(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection, statement);
		}
	}

	public Student create(String firstName, String lastName, String birthDate) {
		Connection connection = new Database().getConnection();
		Integer id = new Random().nextInt();
		try {
			insertStudent(connection, id, firstName, lastName, birthDate);
			return new Student(id, firstName, lastName, birthDate);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}

	public void update(Student student) {
		Connection connection = new Database().getConnection();
		try {
			updateStudent(connection, student.getId(), student.getFirstName(), student.getLastName(), student.getBirthDate());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}

	public void delete(Integer id) {
		Connection connection = new Database().getConnection();
		try {
			deleteStudent(connection, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			CloserHelper.close(connection);
		}
	}

	private List<Student> buildStudents(ResultSet resultSet) throws SQLException {
		List<Student> students = new ArrayList<Student>();
		while(resultSet.next()) {
			students.add( buildStudent(resultSet) );
		}
		return students;	
	}

	private Student buildStudent(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt(1);
		String firstName = resultSet.getString(2);
		String lastName = resultSet.getString(3);
		String birthDate = resultSet.getString(4);
		return new Student(id, firstName, lastName, birthDate);
	}

	private void updateStudent(Connection connection, Integer id, String firstName, String lastName, String birthDate) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?");
		statement.setString(1, firstName);
		statement.setString(2, lastName);
		statement.setDate(3, Date.valueOf(birthDate));
		statement.setInt(4, id);
		statement.executeUpdate();
		CloserHelper.close(statement);
	}

	private void insertStudent(Connection connection, Integer id, String firstName, String lastName, String birthDate) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO students SET id = ?, first_name = ?, last_name = ?, birth_date = ?");
		statement.setInt(1, id);
		statement.setString(2, firstName);
		statement.setString(3, lastName);
		statement.setDate(4, Date.valueOf(birthDate));
		statement.executeUpdate();
		CloserHelper.close(statement);
	}

	private void deleteStudent(Connection connection, Integer id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
		statement.setInt(1, id);
		statement.executeUpdate();
		CloserHelper.close(statement);
	}

}
