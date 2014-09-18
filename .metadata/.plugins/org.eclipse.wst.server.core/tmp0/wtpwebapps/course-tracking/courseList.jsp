<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Courses</title>
</head>
<body>
	<h2>Courses</h2>
	
	<h3>Add A Course</h3>
	<form method="POST" action='CourseController' name="addCourse">
	Name: <input type="text" name="courseName" />
	<input type="hidden" name="action" value="create">
	<input type="submit" value="Add">
	</form>
	
	<br>
	
	<table>
		<thead>
			<tr> 
				<th>Course Name</th> 
				<th colspan=3>Action</th>
			</tr>
		</thead>
		<c:forEach items="${courses}" var="course">
			<tr>
				<td> <c:out value="${course.name}" /> </td>
				<td>
					<form method="POST" action='CourseController' name="updateCourse">
						<input type="hidden" name="action" value="forward" />
						<input type="hidden" name="dispatchTo" value="/updateCourse.jsp" />
						<input type="hidden" name="courseId" value="<c:out value="${course.id}"/>" />
						<input type="hidden" name="courseName" value="<c:out value="${course.name}"/>" />
						<input type="submit" value="Change Name">
					</form>
				</td>
				<td>
                	<form method="POST" action='RosterController' name="updateRoster">
						<input type="hidden" name="action" value="read" />
						<input type="hidden" name="courseId" value="<c:out value="${course.id}"/>" />
						<input type="submit" value="Add Student/View Roster">
					</form>
                </td>
                <td>
                	<form method="POST" action='CourseController' name="deleteCourse">
						<input type="hidden" name="action" value="delete" />
						<input type="hidden" name="courseId" value="<c:out value="${course.id}"/>" />
						<input type="submit" value="Delete">
					</form>
                </td>
			</tr>
		</c:forEach>	
	</table>
	
	<p>Back To: <a href="/course-tracking">Home</a> | <a href="StudentController">Students</a> </p>
</body>

</html>
