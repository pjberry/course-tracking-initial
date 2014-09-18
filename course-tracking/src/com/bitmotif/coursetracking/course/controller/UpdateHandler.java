package com.bitmotif.coursetracking.course.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.course.database.CourseDAO;
import com.zaneray.coursetracking.course.Course;

public class UpdateHandler {

	public void update(HttpServletRequest request, HttpServletResponse response) {
		Integer courseId = new Integer(request.getParameter("courseId"));
		String newCourseName = request.getParameter("newCourseName");
		
		CourseDAO courseDao = new CourseDAO();
		courseDao.update(new Course(courseId, newCourseName));
	}
}
