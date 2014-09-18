package com.bitmotif.coursetracking.roster.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.roster.database.RosterDAO;

public class DeleteStudentFromRosterHandler {

	public void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		Integer courseId = new Integer(request.getParameter("courseId"));
		Integer studentId =  new Integer(request.getParameter("studentId"));
		
		new RosterDAO().removeStudent(courseId, studentId);
	}

}
