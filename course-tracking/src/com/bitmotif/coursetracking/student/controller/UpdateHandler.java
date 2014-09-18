package com.bitmotif.coursetracking.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.student.Student;
import com.bitmotif.coursetracking.student.database.StudentDAO;

public class UpdateHandler {

	public void update(HttpServletRequest request, HttpServletResponse response) {	
		Integer studentId = new Integer(request.getParameter("studentId"));
		String newFirstName = request.getParameter("newFirstName");
		String newLastName = request.getParameter("newLastName");
		String newBirthDate = request.getParameter("newBirthDate");
		
		saveUpdatedStudent(studentId, newFirstName, newLastName, newBirthDate);
	}

	private void saveUpdatedStudent(Integer studentId, String newFirstName, String newLastName, String newBirthDate) {
		try {
			if (isValidRequest(newFirstName, newLastName, newBirthDate)) {
				Student student = new Student(studentId, newFirstName, newLastName, newBirthDate);
				StudentDAO studentDao = new StudentDAO();
				studentDao.update( student );	
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private boolean isValidRequest(String newFirstName, String newLastName, String newBirthDate) {
		return 
				newFirstName != null && !newFirstName.isEmpty() &&
				newLastName != null && !newLastName.isEmpty() &&
				newBirthDate != null && birthDateParsable(newBirthDate);		
	}
	
	private boolean birthDateParsable(String birthDate) {
		return new BirthDateValidation().isBirthDateParsable(birthDate);
	}
}

