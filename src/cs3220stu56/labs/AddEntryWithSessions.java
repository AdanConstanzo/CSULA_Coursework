package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.GuestBookEntry;

/**
 * Servlet implementation class AddEntryWithSessions
 */
@WebServlet("/sessions/AddEntry")
public class AddEntryWithSessions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Set the content type
		response.setContentType("text/html");
		
		// Get a reference to the PrintWriter that lets us talk to the client
		PrintWriter out = response.getWriter();
		
		// Generate the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		out.println("    <meta charset=\"UTF-8\">");
		
		/* Page Title goes here */
		out.println("    <title>Add Entry</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"page-header\">");
		out.println("    <h1>Add Entry <small>Guest Book</small></h1>");
		out.println("</div>");
		
		out.println("<form action=\"AddEntry\" method=\"post\">");
		
		String nameError = (String) request.getAttribute("nameError");
		
		if (nameError != null)
			out.println("<p class=\"text-danger\">" + nameError + "</p>");		
		
		// check if the user's name is stored in session. If so, display the
        // user's name; otherwise display an input field.
        String name = (String) request.getSession().getAttribute( "name" );
        
        if( name != null )
            out.println( "Name: <strong>" + name + "</strong><br />" );
        else {
	        	name = request.getParameter("name");
			if (name == null) name ="";
			out.println("Name: <input type=\"text\" name=\"name\" value=\"" + name + "\"><br>");
        }
		
		
		String messageError = (String) request.getAttribute("messageError");
		
		if (messageError != null)
			out.println("<p class=\"text-danger\">" + messageError + "</p>");
		
		String message = request.getParameter("message");
		if (message == null) message = "";
		
		out.println("Message:");
		out.println("<textarea name=\"message\" rows=\"5\" cols=\"20\">" + message + "</textarea><br>");
		out.println("<input type=\"submit\" name=\"addEntry\" value=\"Add Entry\">");
		out.println("</form>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
		

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// the user's name should either be in session or a request parameter
        HttpSession session = request.getSession();
        
		// First, try to read the name from the cookie 
        String name = (String) session.getAttribute( "name" );
		
		// If the cookie doesn't exist, search the request for the name parameter
		if (name == null)
			name = request.getParameter("name");
		
		String message = request.getParameter("message");
		
		boolean isNameError = name == null || name.trim().length() == 0;
		boolean isMessageError = message == null || message.trim().length() == 0;
		
		// Validate the input
		if (!isNameError && !isMessageError) {
			
			// Create a new entry
			GuestBookEntry entry = new GuestBookEntry(name, message);
			
			ArrayList<GuestBookEntry> entries 
				= (ArrayList<GuestBookEntry>) getServletContext().getAttribute("entries");
			
			// Add the new entry to our array list of entries
			entries.add(entry);
			
			// Store the user's name in the session
            session.setAttribute("name", name );            
			
			response.sendRedirect("../requests/GuestBook");
		}
		
		if (isNameError)
			request.setAttribute("nameError", "You must enter your name");
		
		if (isMessageError)
			request.setAttribute("messageError", "You must enter a message");
		
				
		doGet(request, response);
	}


}
