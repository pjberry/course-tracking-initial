package com.bitmotif.coursetracking.course.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaneray.coursetracking.course.Course;

public class ForwardHandler {

	public void forward(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer courseId = new Integer(request.getParameter("courseId"));
		String courseName = request.getParameter("courseName");
		
		request.setAttribute("existingCourse", new Course(courseId, courseName));
		
		String dispatchTo = request.getParameter("dispatchTo");
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(dispatchTo);
		requestDispatcher.forward(request, response);
	}
}
