package com.bitmotif.coursetracking.roster.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bitmotif.coursetracking.course.database.CourseDAO;
import com.bitmotif.coursetracking.database.CloserHelper;
import com.bitmotif.coursetracking.database.Database;
import com.bitmotif.coursetracking.roster.Roster;
import com.bitmotif.coursetracking.student.Student;
import com.bitmotif.coursetracking.student.database.StudentDAO;
import com.zaneray.coursetracking.course.Course;

public class RosterDAO {

	public List<Roster> retrieveAll() {
		List<Course> courses = new CourseDAO().retrieveAll();
		
		List<Roster> rosters = new ArrayList<Roster>(); 
		for(Course course: courses) {
			rosters.add( retrieveRosterFor(course) );
		}
		
		return rosters; 
	}
	
	public List<Student> retrieveCandidateStudents(Integer courseId) {
		Set<Student> currentStudents = new HashSet<Student>( retrieveCurrentStudents(courseId) );
		Set<Student> allStudents = new HashSet<Student>( new StudentDAO().retreiveAll() );
		
		allStudents.removeAll(currentStudents); // in place mutation
		List<Student> candidateStudents = new ArrayList<Student>(allStudents);
		Collections.sort(candidateStudents, new StudentComparator());
		return candidateStudents;
	}
	
	public Roster retrieveRosterFor(Course course) {
		Connection connection = new Database().getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("SELECT student_id FROM rosters WHERE course_id = ?");
			statement.setInt(1, course.getId());
			ResultSet resultSet = statement.executeQuery();
			List<Student> students = buildStudentList(resultSet);
			return new Roster(course, students);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CloserHelper.close(connection, statement);
		}
	}

	public void addStudent(Integer courseId, Integer studentId) {
		Connection connection = new Database().getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("INSERT INTO rosters SET course_id = ?, student_id = ?");
			statement.setInt(1, courseId);
			statement.setInt(2, studentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CloserHelper.close(connection, statement);
		}		
	}
	

	public void removeStudent(Integer courseId, Integer studentId) {
		Connection connection = new Database().getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("DELETE FROM rosters WHERE course_id = ? AND student_id = ?");
			statement.setInt(1, courseId);
			statement.setInt(2, studentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CloserHelper.close(connection, statement);
		}	
	}
	
	private List<Student> buildStudentList(ResultSet resultSet) throws SQLException {
		List<Student> students = new ArrayList<Student>();
		while(resultSet.next()) {
			Integer studentId = resultSet.getInt(1);
			Student student = new StudentDAO().retrieve(studentId);
			students.add(student);
		}
		Collections.sort(students, new StudentComparator());
		return students;
	}

	private List<Student> retrieveCurrentStudents(Integer courseId) {
		Connection connection = new Database().getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("SELECT student_id FROM rosters WHERE course_id = ?");
			statement.setInt(1, courseId);
			ResultSet resultSet = statement.executeQuery();
			return buildStudentList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CloserHelper.close(connection, statement);
		}
	}
	
	private class StudentComparator implements Comparator<Student> {

		public int compare(Student studentOne, Student studentTwo) {
			String studentOneRepresentation = studentOne.getLastName() + studentOne.getFirstName() + studentOne.getBirthDate();
			String studentTwoRepresentation = studentTwo.getLastName() + studentTwo.getFirstName() + studentTwo.getBirthDate();
			return studentOneRepresentation.compareTo(studentTwoRepresentation);
		}
		
	}


	
}
