<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Edit Course</title>
</head>
<body>
	<h2>Edit Course</h2>
	
	<h3>Add A Course</h3>
	<form method="POST" action='CourseController' name="addCourse">
	Name: <input type="text" name="courseName" />
	<input type="hidden" name="action" value="update">
	<input type="submit" value="Add">
	</form>

	<p>Back To: <a href="/course-tracking">Home</a> | <a href="StudentController">Students</a> | <a href="CourseController">Courses</a></p>
</body>

</html>
