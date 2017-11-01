package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Student;

@WebServlet(urlPatterns="/sessions/Login", loadOnStartup=3)
public class LoginSessions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		// Create a few students 
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(new Student("John", "Doe", "john@doe.com", "abcd"));
		students.add(new Student("Mary", "Jane", "mary@jane.com", "efgh"));
		students.add(new Student("Joe", "Boxer", "joe@boxer.com", "ijkl"));
		students.add(new Student("Adan","Constanzo","a","a"));
		
		// Add the students to the application scope (Servlet Context)
		getServletContext().setAttribute("students", students);
		
	}
	
	public String hashStudentId(int id) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(Integer.toString(id).getBytes(StandardCharsets.UTF_8));
			return  bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set the content type
		response.setContentType("text/html");
		
		// Get a reference to the PrintWriter that lets us talk to the client
		PrintWriter out = response.getWriter();
		
		ArrayList<Student> students = (ArrayList<Student>) getServletContext().getAttribute("students");
		Cookie[] cookies = request.getCookies();
		
		Cookie studentCookie = returnCookie(cookies,"student");
		String studentCookieValue;
		
		if(studentCookie != null) {
			studentCookieValue = studentCookie.getValue();
			for(Student student:students) {
				if(hashStudentId(student.getId()).equals(studentCookieValue)) {
					HttpSession session = request.getSession();
					session.setAttribute("authenticatedStudent", student);
					response.sendRedirect("MyProfile");
				}
				
			}
		}
		
		// Generate the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\" ");
		out.println("    <meta charset=\"UTF-8\">");
		
		/* Page Title goes here */
		out.println("    <title>Login (Sessions)</title>");
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
		out.println("<form action=\"Login\" method=\"post\">");
		out.println("	<div class=\"form-group\">");
		out.println("		<label>Username (E-mail Address)</label>");
		out.println("		<input class=\"form-control\"type=\"text\" name=\"username\" placeholder=\"Email\">");
		out.println("	</div>");
		out.println("	<div class=\"form-group\">");
		out.println("		<label>Password</label>");
		out.println("		<input class=\"form-control\"type=\"password\" name=\"password\" placeholder=\"Password\">");
		out.println("	</div>");
		out.println("	<div class=\"checkbox\">");
		out.println("		<label>");
		out.println("			<input type=\"checkbox\" name=\"rememberMe\"> Remember Username");
		out.println("		</label>");
		out.println("	</div>");
		out.println("	<button type=\"submit\" class=\"btn btn-primary\">Login</button>");
		out.println("</form>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
		

	}
	
	public Cookie returnCookie(Cookie[] cookies,String cookieName) {
		if (cookies != null)
			 for (Cookie cookie : cookies) 
			   if (cookie.getName().equals(cookieName))
				   return cookie;
		return null;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the credentials from the request object
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// If the user submitted bad input, just redisplay the form
		if (username == null || username.trim().length() == 0 ||
			password == null || password.trim().length() == 0) {
			
			doGet(request, response);
			
			// Don't forget, calling `doGet` does not stop the execution of this method.
			// We need to `return`.
			return;
		}
		
		// If we get here then the Student submitted a username and password.
		
		// Do something with the "Remember Me" checkbox
		// To do...
		String checkboxValues = request.getParameter("rememberMe");
		
		// Authenticate the Student by searching all of the Students in our app
		// and comparing the submitted credentials against each student's email and password
		ArrayList<Student> students = (ArrayList<Student>) getServletContext().getAttribute("students");
		
		for (Student student : students) {
			if (student.getEmail().toLowerCase().equals(username.trim().toLowerCase()) &&
				student.getPassword().equals(password)) {
				// If we get here, the username and password match the current `student`.
				// Let's create a session attribute that references `this current student`.
				if(checkboxValues != null) {
					String originalString = Integer.toString(student.getId());	
					
					MessageDigest digest;
					try {
						digest = MessageDigest.getInstance("SHA-256");
						byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
						String hash = bytesToHex(encodedhash);
						Cookie myCookie = new Cookie("student",hash);
						response.addCookie(myCookie);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} // end if
				HttpSession session = request.getSession();
				session.setAttribute("authenticatedStudent", student);
				
				// Now that we've set an attribute in the session scope, let's
				// redirect the Student to the "Student's Profile" area.
				response.sendRedirect("MyProfile");
				return;				
			}
		}
		
		// if we get here then we couldn't find a Student that matched the submitted credentials
		// So, we add an error message to the request scope and redisplay the form
		request.setAttribute("error", "Invalid username and/or password");
		doGet(request, response);
	}

}
