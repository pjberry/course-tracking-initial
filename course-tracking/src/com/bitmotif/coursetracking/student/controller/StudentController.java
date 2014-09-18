package com.bitmotif.coursetracking.student.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public StudentController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		new ReadHandler().dispatchToPopulatedStudentList(servletContext, request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("create".equals(action)) {
			new CreateHandler().create(request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("update".equals(action)) {
			new UpdateHandler().update(request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("delete".equals(action)) {
			new DeleteHandler().delete(request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("forward".equals(action)) {
			ServletContext servletContext = getServletContext();
			new ForwardHandler().forward(servletContext, request, response);
		}
		else {
			throw new RuntimeException("Unexpected action: " + action);
		}
	}

	private void dispatchToPopulatedCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReadHandler readHandler = new ReadHandler();
		ServletContext servletContext = getServletContext();
		readHandler.dispatchToPopulatedStudentList(servletContext, request, response);
	}
}
