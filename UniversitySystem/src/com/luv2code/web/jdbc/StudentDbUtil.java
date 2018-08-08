package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


//This class will work as a Model of the MVC structure
public class StudentDbUtil {

	private DataSource datasource;

	public StudentDbUtil(DataSource theDatasource) {
		this.datasource = theDatasource;
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			//get a connection
			myConn = datasource.getConnection();
			
			//create sql statement
			String sql = "select * from student order by last_name";
			myStmt = myConn.createStatement();
			
			//execute query
			myRs = myStmt.executeQuery(sql);
			
			//process result set
			while(myRs.next()) {
				//retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				//create new student objec
				Student tempStudent =new Student(id, firstName, lastName, email);
				
				// add it to the list of the students
				students.add(tempStudent);
			}
			
			
			return students;
		}
		finally {
			//close JDBC objects
			close(myConn,myStmt,myRs);
		}
	
		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myConn != null) {
				myRs.close();
			}
			
			if(myStmt != null) {
				myStmt.close();
			}
			
			if(myConn != null) {
				myConn.close();
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = datasource.getConnection();
			
			// create sql for insert
			String sql = "insert into student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public Student getStudent(String theStudentId) throws Exception {
		//TODO 
		Student theStudent = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			//conveert studentid to int
			studentId = Integer.parseInt(theStudentId);
			
			//get connection to the databse
			myConn = datasource.getConnection();
			//create sql to get the selected the student
			String sql = "select * from student where id=?";
			
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, studentId);
			
			//execute statement
			myRs = myStmt.executeQuery();
			
			//retrieve data form result set row
			if(myRs.next()) {
				String fName =  myRs.getString("first_name");
				String lName = myRs.getString("last_name");
				String email = myRs.getString("email");
				theStudent = new Student(studentId,fName,lName,email);
			}
			else {
				throw new Exception("Could not find student id: "+studentId);
			}		
			
			return theStudent;
			
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
	}
	
	
	
	
	
}
