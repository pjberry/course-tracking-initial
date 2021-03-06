package com.bitmotif.coursetracking.roster.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RosterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RosterController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		new ReadHandler().dispatchToPopulatedRosterList(servletContext, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("read".equals(action)) {
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("addStudent".equals(action)) {
			new AddStudentToRosterHandler().addStudent(request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("deleteStudent".equals(action)) {
			new DeleteStudentFromRosterHandler().deleteStudent(request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else if ("dumpXML".equals(action)) {
			new DumpToXMLHandler().dump(getServletContext(), request, response);
			dispatchToPopulatedCourseList(request, response);
		}
		else {
			throw new RuntimeException("Unexpected action: " + action);
		}
			
	}

	private void dispatchToPopulatedCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReadHandler readHandler = new ReadHandler();
		ServletContext servletContext = getServletContext();
		readHandler.dispatchToPopulatedRosterList(servletContext, request, response);
	}
}
