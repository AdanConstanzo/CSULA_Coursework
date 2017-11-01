package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Student;

/**
 * Servlet implementation class StudentProfile
 */
@WebServlet("/sessions/MyProfile")
public class StudentProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Let's get a reference to the student who is currently logged in
		Student student = (Student) request.getSession().getAttribute("authenticatedStudent");
		
		// If there is no student logged in, redirect back to the login page
		if (student == null) {
			response.sendRedirect("Login");
			return;
		}
		
		// Set the content type
		response.setContentType("text/html");
		
		// Get a reference to the PrintWriter that lets us talk to the client
		PrintWriter out = response.getWriter();
		
		// Generate the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\" ");
		out.println("    <meta charset=\"UTF-8\">");
		
		/* Page Title goes here */
		out.println("    <title>Student Profile</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"jumbotron\">");
		out.println("    <h1>" + student.getFirstName() + "'s Profile</small></h1>");
		out.println("    <p class=\"lead\">This is a Student's Only area.</p>");
		out.println("	 <a class=\"btn btn-primary\" href=\"Logout\">Logout</a>");
		out.println("</div>");
		
		out.println("<h3>Grades <small>" + student.getFullName() + "</small></h3>");
		out.println("<table class=\"table table-bordered table-striped table-hover\">");
		out.println("	<tr>");
		out.println("		<th>Assignment</th>");
		out.println("		<th>Score</th>");		
		out.println("	</tr>");
				
		// Print all of the student's scores.
		double[] scores = student.getScores();
		for (int i = 0; i < scores.length; i++) {
			out.println("	<tr>");
			out.println("		<td>Assignment " + (i+1) + "</td>");
			out.println("		<td>" + scores[i] + "</td>");
			out.println("	</tr>");
		}
		
		out.println("</table>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
		

	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
