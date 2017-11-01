package midTerm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tutor;

@WebServlet("/midterm/Register")
public class Register extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Set the content type
		response.setContentType("text/html");
		
		// Get a reference to the PrintWriter that lets us talk to the client
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\" ");
		out.println("    <meta charset=\"UTF-8\">");
		
		/* Page Title goes here */
		out.println("    <title>Tutor Register</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		
		// Display the error message if it exists
		String error = (String) request.getAttribute("error");
		
		if (error != null)
		out.println("<p class=\"text-center text-danger\">" + error + "</p>");
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"page-header\">");
		out.println("    <h1>Login <small>HttpSessions</small></h1>");
		out.println("</div>");
			
		// Create the login form
		out.println("<form action=\"Register\" method=\"post\">");
		
		if(request.getAttribute("nameError") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("nameError") + "</p>");
		}

		String fullname = request.getParameter("fullname");
		if(fullname == null) fullname = "";
		
		out.println("	<div class=\"form-group\">");
		out.println("		<label>Full Name</label>");
		out.println("		<input class=\"form-control\"type=\"text\"  name=\"fullname\" value=\""+fullname+"\" placeholder=\"Ex. John Doe\">");
		out.println("	</div>");
		
		if(request.getAttribute("emailError") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("emailError") + "</p>");
		}

		String email = request.getParameter("email");
		if(email == null) email = "";
		
		
		out.println("	<div class=\"form-group\">");
		out.println("		<label>Email</label>");
		out.println("		<input class=\"form-control\"type=\"email\"  name=\"email\" placeholder=\"Ex. John@doe.com\" value=\""+email+"\" >");
		out.println("	</div>");
		
		if(request.getAttribute("courseError") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("courseError") + "</p>");
		}

		String courses = request.getParameter("courses");
		if(courses == null) courses = "";
		
		
		
		out.println("	<div class=\"form-group\">");
		out.println("		<label>Courses</label>");
		out.println("		<br>");
		out.println("		<label>Seperate with a comma (,)</label>");
		out.println("		<input class=\"form-control\"type=\"text\"  name=\"courses\" placeholder=\"Ex. Calculus, Data Structures, PE\" value=\""+courses+"\" >");
		out.println("	</div>");
		out.println("	<button type=\"submit\" class=\"btn btn-primary\">Register</button>");
		out.println("</form>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fullname = request.getParameter("fullname");
		String email = request.getParameter("email");
		String courses = request.getParameter("courses");
		
		boolean isValidFullname = fullname != null && fullname.trim().length() > 0;
		boolean isValidEmail = email != null && email.trim().length() > 0;
		boolean isValidCourse = courses != null && courses.trim().length()>0;

		// Validate the input
		if (isValidFullname && isValidEmail && isValidCourse) {
			
			//parse courses into array.
			String[] ArrayCourse = courses.split(",");
			
			// Create a new entry
			Tutor entry = new Tutor(fullname, email,ArrayCourse);
			
			ArrayList<Tutor> entries
				= (ArrayList<Tutor>) getServletContext().getAttribute("tutors");

			// Add the new entry to our array list of entries
			entries.add(entry);

			response.sendRedirect("HomePage");
		}

		if(!isValidFullname) 
			request.setAttribute("nameError", "You must specify a full name");
		if(!isValidEmail) 
			request.setAttribute("emailError", "You must enter an email");
		if(!isValidCourse)
			request.setAttribute("courseError","You must enter at least one course");

		
		doGet(request, response);
	}

}
