package com.bitmotif.coursetracking.student.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BirthDateValidation {

	public boolean isBirthDateParsable(String birthDate) {
		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
