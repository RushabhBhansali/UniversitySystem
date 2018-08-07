<%@ page import="java.util.*" %>
<%@ page import="com.luv2code.web.jdbc.*" %>
<!DOCTYPE html>
<html>
<head>
	<title> Student Tracker App </title>
</head>
<%
 //get the students from the request object (sent by servlet)
 List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
%>

<body>
<%= theStudents %>
</body>

</html>