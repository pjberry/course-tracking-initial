<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Course Roster</title>
</head>
<body>
	
	<h2>Course Roster: <c:out value="${roster.course.name}" /></h2>
	
	<h3>Add A Student</h3>
	
	<form method="POST" action='RosterController' name="addStudent">
	Student:
		<select name="studentId">
			<c:forEach items="${candidateStudents}" var="candidate" >
				<option value=<c:out value="${candidate.id}" /> > 
					<c:out value="${candidate.firstName}" /> 
					<c:out value="${candidate.lastName}" /> 
					<c:out value="${candidate.birthDate}" /> 
				</option>
			</c:forEach>
		</select>
		<input type="hidden" name="courseId" value="<c:out value="${roster.course.id}"/>" >
		<input type="hidden" name="action" value="addStudent">
		<input type="submit" value="Add">
	</form>
	
	<br>
	
	<c:set var="currentStudents" scope="request" value="${roster.students}"/>
	<h3>Students</h3>
	<table>
		<thead>
			<tr> 
				<th>First Name</th> 
				<th>Last Name</th>
				<th>Birth Date</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach items="${currentStudents}" var="student">
			<tr>
				<td> <c:out value="${student.firstName}" /> </td>
				<td> <c:out value="${student.lastName}" /> </td>
				<td> <c:out value="${student.birthDate}" /> </td>
                <td>
                	<form method="POST" action='RosterController' name="deleteStudent">
						<input type="hidden" name="action" value="deleteStudent" />
						<input type="hidden" name="studentId" value="<c:out value="${student.id}"/>" />
						<input type="hidden" name="courseId" value="<c:out value="${roster.course.id}"/>" />
						<input type="submit" value="Delete">
					</form>
                </td>
			</tr>
		</c:forEach>	
	</table>
	
	<p>Back To:  <a href="/course-tracking">Home</a> | <a href="StudentController">Students</a> | <a href="CourseController">Courses</a></p>
	
</body>

</html>
