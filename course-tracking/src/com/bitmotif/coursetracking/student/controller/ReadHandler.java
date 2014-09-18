package com.bitmotif.coursetracking.student.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.student.Student;
import com.bitmotif.coursetracking.student.database.StudentDAO;

public class ReadHandler {

	public void dispatchToPopulatedStudentList(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDAO studentDao = new StudentDAO();
		List<Student> students = studentDao.retreiveAll();
		request.setAttribute("students", students);
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/studentList.jsp");
		requestDispatcher.forward(request, response);
	}
}
