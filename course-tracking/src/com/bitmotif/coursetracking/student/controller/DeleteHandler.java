package com.bitmotif.coursetracking.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.student.database.StudentDAO;

public class DeleteHandler {

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Integer studentId = new Integer( request.getParameter("studentId") );
		StudentDAO studentDao = new StudentDAO();
		studentDao.delete(studentId);	
	}
}
