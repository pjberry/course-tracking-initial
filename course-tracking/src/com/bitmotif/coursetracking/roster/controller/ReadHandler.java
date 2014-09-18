package com.bitmotif.coursetracking.roster.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.course.database.CourseDAO;
import com.bitmotif.coursetracking.roster.Roster;
import com.bitmotif.coursetracking.roster.database.RosterDAO;
import com.bitmotif.coursetracking.student.Student;
import com.zaneray.coursetracking.course.Course;

public class ReadHandler {

	public void dispatchToPopulatedRosterList(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer courseId = new Integer(request.getParameter("courseId"));
		
		Course course = retrieveCourse(courseId);
		Roster roster = retrieveRoster(course);
		List<Student> candidateStudents = retrieveCandidateStudents(course); 
		
		request.setAttribute("roster", roster);
		request.setAttribute("candidateStudents", candidateStudents);
		
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/roster.jsp");
		requestDispatcher.forward(request, response);
	}

	private Course retrieveCourse(Integer courseId) {
		CourseDAO courseDao = new CourseDAO();
		Course course = courseDao.retrieve(courseId);
		return course;
	}
	
	private Roster retrieveRoster(Course course) {
		RosterDAO rosterDao = new RosterDAO();
		Roster roster = rosterDao.retrieveRosterFor(course);
		return roster;
	}
	
	private List<Student> retrieveCandidateStudents(Course course) {
		return new RosterDAO().retrieveCandidateStudents(course.getId());
	}

}
