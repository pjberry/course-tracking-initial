package com.bitmotif.coursetracking.roster.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bitmotif.coursetracking.course.database.CourseDatabaseTestUtils;
import com.bitmotif.coursetracking.database.Database;
import com.bitmotif.coursetracking.roster.Roster;
import com.bitmotif.coursetracking.student.Student;
import com.bitmotif.coursetracking.student.database.StudentDatabaseTestUtils;

public class RosterDAOTest {

private Connection connection;
	
	private final Integer STUDENT_ID_ONE = -1;
	private final Integer STUDENT_ID_TWO = -2;
	private final String STUDENT_FIRST_NAME_ONE = "First Name For Test--ONE";
	private final String STUDENT_FIRST_NAME_TWO = "First Name For Test--TWO";
	private final String STUDENT_LAST_NAME_ONE = "Last Name For Test--ONE";
	private final String STUDENT_LAST_NAME_TWO = "Last Name For Test--TWO";
	private final String STUDENT_BIRTH_DATE_ONE = "1999-09-09";
	private final String STUDENT_BIRTH_DATE_TWO = "2001-01-01";
	
	private final Integer COURSE_ID_ONE = -1;
	private final String COURSE_NAME_ONE = "Course Name For Test--ONE";
	private final Integer COURSE_ID_TWO = -2;
	private final String COURSE_NAME_TWO = "Course Name For Test--TWO";
	
	@Before
	public void prepareConnection() throws SQLException {
		connection = new Database().getConnection();
	}
	
	@After
	public void closeConnection() throws SQLException {
		connection.close();
	}
	
	@Test
	public void canRetrieveTwo() throws SQLException {
		try {
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_ONE, STUDENT_FIRST_NAME_ONE, STUDENT_LAST_NAME_ONE, STUDENT_BIRTH_DATE_ONE);
			CourseDatabaseTestUtils.insertCourse(connection, COURSE_ID_ONE, COURSE_NAME_ONE);
			
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_TWO, STUDENT_FIRST_NAME_TWO, STUDENT_LAST_NAME_TWO, STUDENT_BIRTH_DATE_TWO);
			CourseDatabaseTestUtils.insertCourse(connection, COURSE_ID_TWO, COURSE_NAME_TWO);
			
			insertRoster(COURSE_ID_ONE, Arrays.asList(STUDENT_ID_ONE));
			insertRoster(COURSE_ID_TWO, Arrays.asList(STUDENT_ID_TWO));
			RosterDAO rosterDao = new RosterDAO();
			
			List<Roster> rosters = rosterDao.retrieveAll();
			
			assertTrue(rosters.size() == 2);
			
			Roster rosterOne = rosters.get(0);
			assertEquals(COURSE_ID_ONE, rosterOne.getCourse().getId());
			assertEquals(COURSE_NAME_ONE, rosterOne.getCourse().getName());
			
			List<Student> rosterOneStudents = rosterOne.getStudents();
			assertEquals(1, rosterOneStudents.size());
			assertEquals(STUDENT_LAST_NAME_ONE, rosterOneStudents.get(0).getLastName());
			assertEquals(STUDENT_FIRST_NAME_ONE, rosterOneStudents.get(0).getFirstName());
			assertEquals(STUDENT_BIRTH_DATE_ONE, rosterOneStudents.get(0).getBirthDate());
			
			Roster rosterTwo = rosters.get(1);
			assertEquals(COURSE_ID_TWO, rosterTwo.getCourse().getId());
			assertEquals(COURSE_NAME_TWO, rosterTwo.getCourse().getName());
			
			List<Student> rosterTwoStudents = rosterTwo.getStudents();
			assertEquals(1, rosterTwoStudents.size());
			assertEquals(STUDENT_LAST_NAME_TWO, rosterTwoStudents.get(0).getLastName());
			assertEquals(STUDENT_FIRST_NAME_TWO, rosterTwoStudents.get(0).getFirstName());
			assertEquals(STUDENT_BIRTH_DATE_TWO, rosterTwoStudents.get(0).getBirthDate());
		}
		finally {
			deleteRoster(COURSE_ID_ONE);
			deleteRoster(COURSE_ID_TWO);
			CourseDatabaseTestUtils.deleteCourse(connection, COURSE_ID_ONE);
			CourseDatabaseTestUtils.deleteCourse(connection, COURSE_ID_TWO);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_ONE);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_TWO);
		}
	}
	
	@Test
	public void canFindCandidateStudentsForClass() throws SQLException {
		try {
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_ONE, STUDENT_FIRST_NAME_ONE, STUDENT_LAST_NAME_ONE, STUDENT_BIRTH_DATE_ONE);
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_TWO, STUDENT_FIRST_NAME_TWO, STUDENT_LAST_NAME_TWO, STUDENT_BIRTH_DATE_TWO);
			CourseDatabaseTestUtils.insertCourse(connection, COURSE_ID_ONE, COURSE_NAME_ONE);
			
			RosterDAO rosterDao = new RosterDAO();
			
			List<Student> students = rosterDao.retrieveCandidateStudents(COURSE_ID_ONE);
			
			assertTrue(students.size() == 2);
			
			assertEquals(2, students.size());
			assertEquals(STUDENT_LAST_NAME_ONE, students.get(0).getLastName());
			assertEquals(STUDENT_FIRST_NAME_ONE, students.get(0).getFirstName());
			assertEquals(STUDENT_BIRTH_DATE_ONE, students.get(0).getBirthDate());
			
			assertEquals(STUDENT_LAST_NAME_TWO, students.get(1).getLastName());
			assertEquals(STUDENT_FIRST_NAME_TWO, students.get(1).getFirstName());
			assertEquals(STUDENT_BIRTH_DATE_TWO, students.get(1).getBirthDate());
		}
		finally {
			deleteRoster(COURSE_ID_ONE);
			CourseDatabaseTestUtils.deleteCourse(connection, COURSE_ID_ONE);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_ONE);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_TWO);
		}
	}
	
	@Test
	public void canAddAStudentToACourse() throws SQLException {
		try {
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_ONE, STUDENT_FIRST_NAME_ONE, STUDENT_LAST_NAME_ONE, STUDENT_BIRTH_DATE_ONE);
			CourseDatabaseTestUtils.insertCourse(connection, COURSE_ID_ONE, COURSE_NAME_ONE);
			
			RosterDAO rosterDao = new RosterDAO();
			rosterDao.addStudent(COURSE_ID_ONE, STUDENT_ID_ONE);
			
			assertTrue(rosterRecordExists(COURSE_ID_ONE, STUDENT_ID_ONE));
		}
		finally {
			deleteRoster(COURSE_ID_ONE);
			CourseDatabaseTestUtils.deleteCourse(connection, COURSE_ID_ONE);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_ONE);
		}
	}
	
	@Test
	public void canRemoveAStudentFromACourse() throws SQLException {
		try {
			StudentDatabaseTestUtils.insertStudent(connection, STUDENT_ID_ONE, STUDENT_FIRST_NAME_ONE, STUDENT_LAST_NAME_ONE, STUDENT_BIRTH_DATE_ONE);
			CourseDatabaseTestUtils.insertCourse(connection, COURSE_ID_ONE, COURSE_NAME_ONE);
			insertRosterRecord(COURSE_ID_ONE, STUDENT_ID_ONE);
			assertTrue(rosterRecordExists(COURSE_ID_ONE, STUDENT_ID_ONE));
			
			RosterDAO rosterDao = new RosterDAO();
			rosterDao.removeStudent(COURSE_ID_ONE, STUDENT_ID_ONE);
			
			assertFalse(rosterRecordExists(COURSE_ID_ONE, STUDENT_ID_ONE));
		}
		finally {
			deleteRoster(COURSE_ID_ONE);
			CourseDatabaseTestUtils.deleteCourse(connection, COURSE_ID_ONE);
			StudentDatabaseTestUtils.deleteStudent(connection, STUDENT_ID_ONE);
		}
	}
	
	private void deleteRoster(Integer courseId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM rosters WHERE course_id = ?");
		try {
			statement.setInt(1, courseId);
			statement.executeUpdate();
		} 
		finally {
			statement.close();
		}	
	}
	
	private void insertRosterRecord(Integer courseId, Integer studentId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO rosters SET course_id = ?, student_id = ?");
		try {
			statement.setInt(1, courseId);
			statement.setInt(2, studentId);
			statement.executeUpdate();
		} 
		finally {
			statement.close();
		}	
	}
	
	private boolean rosterRecordExists(Integer courseId, Integer studentId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM rosters WHERE course_id = ? AND student_id = ?");
		try {
			statement.setInt(1, courseId);
			statement.setInt(2, studentId);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1) == 1;
		}
		finally {
			statement.close();
		}
	}
	
	private void insertRoster(Integer courseId, List<Integer> studentIds) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO rosters SET course_id = ?, student_id = ?");
		
		try {
			statement.setInt(1, courseId);
			
			for(Integer id : studentIds) {
				statement.setInt(2, id);
				statement.execute();
			}
		}
		finally {
			statement.close();
		}
	}

}
