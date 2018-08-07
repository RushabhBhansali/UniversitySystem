package com.luv2code.web.jdbc;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


//This is a test servlet written to test the database connection of the app.
/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. set up the printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//2. get a connection to the database;
		Connection myConn  = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
		//3. create a SQL statement
			myConn = datasource.getConnection();
			String query = "select * from student";
			myStmt = myConn.createStatement();
		
		//4. Execute SQL query
			myRs = myStmt.executeQuery(query);
		
		//5. process the result set
			while(myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	

}
