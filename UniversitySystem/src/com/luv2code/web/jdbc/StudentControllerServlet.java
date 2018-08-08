package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		//crete studentDbutil and pass datasource
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception exc){
			throw new ServletException(exc);
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
				//read the "command" parameter
				String theCommand = request.getParameter("command");
				//route to the appropriate piece of code
				if(theCommand == null) {
					listStudent(request,response);
				}
				
				switch(theCommand) {
					case "LIST":
						listStudent(request,response);
						break;
					
					
					case "ADD":{
						addStudent(request, response);
						break;
					}
					
					default:
						listStudent(request,response);
				
				}
			
		}
		catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		// create a new student object
		Student theStudent =new Student(firstName,lastName,email);
		
		// add student to the database
		studentDbUtil.addStudent(theStudent);
		
		//send back to main page (the student list)
		listStudent(request,response);
	}


	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//get the students from db util
		List<Student> students = studentDbUtil.getStudents();
		//add students to the request
		request.setAttribute("STUDENT_LIST", students);		
		//send to JSP page (View)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);		
	}

}
