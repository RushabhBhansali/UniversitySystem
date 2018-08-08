<%@ page import="java.util.*" %>
<%@ page import="com.luv2code.web.jdbc.*" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
	<title> Student Tracker App </title>
	<link type="text/css" rel="stylesheet" href="css/style.css"/>
</head>
<%
 //get the students from the request object (sent by servlet)
 	List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
	pageContext.setAttribute("studentList", theStudents);
%>

<body>

	<div id="wrapper">
		<div id="header">
			<h2> University Name </h2>
		</div>
	</div>

	<div id="wrapper">
		<div id="content">
	<!-- 	put new button -->
			<br/>	
			<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" 
			class="add-student-button"/>
		
			<table>
				<tr> 
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
				</tr>
				
				<c:forEach var="student" items="${studentList}">
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="studentId" value="${student.getId()}"/>
					</c:url>
					<tr>
						<td>  ${student.getFirstName()} </td>
						<td> ${student.getLastName()} </td>
						<td> ${student.getEmail()}</td>
						<td><a href="${tempLink}">Update</a></td>
					</tr>
				</c:forEach>
				
			<%-- 
				<% for(Student tempStudent: theStudents){%>
					<tr>
						<td> <%= tempStudent.getFirstName() %> </td>
						<td> <%= tempStudent.getLastName() %> </td>
						<td> <%= tempStudent.getEmail() %> </td>
					</tr>
			<%	} %>
				 --%>
				
				
				
				
			</table>
		</div>
	</div>
</body>

</html>

