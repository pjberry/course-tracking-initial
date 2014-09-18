package com.bitmotif.coursetracking.roster.xml;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.bitmotif.coursetracking.roster.Roster;
import com.bitmotif.coursetracking.student.Student;

public class RosterXMLWriter {

	public Document write(List<Roster> rosters) {
		Element classes = new Element("classes");

		for(Roster roster : rosters) {
			classes.addContent( buildClassElement(roster));
		}
		
		return new Document(classes);
	}

	private Element buildClassElement(Roster roster) {
		Element classElement = new Element("class");
		
		Element name = new Element("name").addContent(roster.getCourse().getName());	
		classElement.addContent(name);
		classElement.addContent( buildStudentsElement(roster.getStudents()) );
		
		return classElement;
	}
	
	private Element buildStudentsElement(List<Student> students) {
		Element studentsElement = new Element("students");
		
		for(Student student : students) {
			Element studentElement = buildStudentElement(student);
			studentsElement.addContent(studentElement);
		}
		
		return studentsElement;
	}
	
	private Element buildStudentElement(Student student) {
		Element studentElement = new Element("student");
		studentElement.addContent(student.getFirstName() + " " + student.getLastName());
		return studentElement;
	}
	
	
}
