package com.bitmotif.coursetracking.course.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.course.database.CourseDAO;

public class DeleteHandler {
	
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		Integer courseId = new Integer( request.getParameter("courseId") );
		CourseDAO courseDao = new CourseDAO();
		courseDao.delete(courseId);
	}
}

