<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Student / Course Tracking System</title>
</head>
<body>
	<p><a href="StudentController">Students</a></p>
	<p><a href="CourseController">Courses</a></p>
	
	<form method="POST" action='RosterController' name="dumpXML">
		<input type="hidden" name="action" value="dumpXML">
		<input type="submit" value="Dump To XML">
	</form>
	
</body>
</html>