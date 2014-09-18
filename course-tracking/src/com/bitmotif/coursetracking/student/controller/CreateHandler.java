package com.bitmotif.coursetracking.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitmotif.coursetracking.student.database.StudentDAO;

public class CreateHandler {

	public void create(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String birthDate = request.getParameter("birthDate");
		createStudent(firstName, lastName, birthDate);
	}

	private void createStudent(String firstName, String lastName, String birthDate) {
		try {
			if (isValidStudentInformation(firstName, lastName, birthDate)) {
				StudentDAO studentDao = new StudentDAO();
				studentDao.create(firstName, lastName, birthDate);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private Boolean isValidStudentInformation(String firstName, String lastName, String birthDate) {
		return !firstName.isEmpty() && !lastName.isEmpty() && !birthDate.isEmpty() && birthDateParsable(birthDate);
	}

	private boolean birthDateParsable(String birthDate) {
		return new BirthDateValidation().isBirthDateParsable(birthDate);
	}
}

