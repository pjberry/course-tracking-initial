package com.bitmotif.coursetracking.course.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.course.database.CourseDAO;

public class CreateHandler {

	public void create(HttpServletRequest request, HttpServletResponse response) {
		String courseName = request.getParameter("courseName");
		CourseDAO courseDao = new CourseDAO();
		if(!courseName.isEmpty() && !courseDao.exists(courseName)) courseDao.create(courseName);
	}
}