package com.bitmotif.coursetracking.roster.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.roster.database.RosterDAO;

public class AddStudentToRosterHandler {

	public void addStudent(HttpServletRequest request, HttpServletResponse response) {
		if (isValidRequest(request)) {
			Integer courseId = new Integer(request.getParameter("courseId"));
			Integer studentId =  new Integer(request.getParameter("studentId"));
			new RosterDAO().addStudent(courseId, studentId);
			request.setAttribute("courseId", courseId);
		}		
	}

	private boolean isValidRequest(HttpServletRequest request) {
		String courseParameter = request.getParameter("courseId");
		String studentParameter = request.getParameter("studentId");
		return courseParameter != null && !courseParameter.isEmpty() && studentParameter != null && !studentParameter.isEmpty();
	}
}