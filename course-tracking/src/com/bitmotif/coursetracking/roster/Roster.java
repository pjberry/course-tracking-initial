package com.bitmotif.coursetracking.roster;

import java.util.List;

import com.bitmotif.coursetracking.student.Student;
import com.zaneray.coursetracking.course.Course;

public class Roster {

	private final Course course;
	private final List<Student> students;
	
	public Roster(Course course, List<Student> students) {
		this.course = course;
		this.students = students;
	}

	public Course getCourse() {
		return course;
	}

	public List<Student> getStudents() {
		return students;
	}
	
}
