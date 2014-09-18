<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Edit Student</title>
</head>
<body>
	<h2>Edit Student</h2>
	
	<h3>Student: <c:out value="${existingStudent.firstName}"/> <c:out value="${existingStudent.lastName}"/> <c:out value="${existingStudent.birthDate}"/></h3>
	<form method="POST" action='StudentController' name="updateStudent">
		New First Name: <input type="text" name="newFirstName" value="<c:out value="${existingStudent.firstName}"/>" /> <br/>
		New Last Name: <input type="text" name="newLastName" value="<c:out value="${existingStudent.lastName}"/>"/> <br/>
		New First Birth Date: <input type="text" name="newBirthDate" value="<c:out value="${existingStudent.birthDate}"/>"/>  (Use YYYY-MM-DD Form)<br/>
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="studentId" value="<c:out value="${existingStudent.id}"/>">
		<input type="submit" value="Update">
	</form>

	<p>Back To: <a href="/course-tracking">Home</a> | <a href="StudentController">Students</a> | <a href="CourseController">Courses</a></p>
	
</body>

</html>