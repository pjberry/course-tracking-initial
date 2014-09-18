package com.bitmotif.coursetracking.student.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.student.Student;

public class ForwardHandler {

	public void forward(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer studentId = new Integer(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String birthDate = request.getParameter("birthDate");
		
		Student student = new Student(studentId, firstName, lastName, birthDate);
		request.setAttribute("existingStudent", student);
		
		String dispatchTo = request.getParameter("dispatchTo");
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(dispatchTo);
		requestDispatcher.forward(request, response);
	}

}
