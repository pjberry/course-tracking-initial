<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Edit Course</title>
</head>
<body>
	<h2>Edit Course</h2>
	
	<h3>Current Name: <c:out value="${existingCourse.name}"/></h3>
	<form method="POST" action='CourseController' name="updateCourse">
	New Name: <input type="text" name="newCourseName" value="<c:out value="${existingCourse.name}"/>" />
	<input type="hidden" name="action" value="update">
	<input type="hidden" name="courseId" value="<c:out value="${existingCourse.id}"/>">
	<input type="submit" value="Update">
	</form>

	<p>Back To: <a href="/course-tracking">Home</a> |  <a href="StudentController">Students</a> | <a href="CourseController">Courses</a></p>
</body>

</html>