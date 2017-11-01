package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.GuestBookEntry;

/**
 * Servlet implementation class AddEntry
 */
@WebServlet("/requests/AddEntry")
public class AddEntry extends HttpServlet {
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
		out.println("<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\" ");
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

		out.println("<form class=\"form-horizontal\" action=\"AddEntry\" method=\"post\">");

		if(request.getAttribute("nameError") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("nameError") + "</p>");
		}

		String name = request.getParameter("name");
		if(name == null) name = "";


		out.println("<div class=\"form-group\" >");
		out.println("<label class=\"col-sm-2 control-label\">Name</label> ");
		out.println("<div class=\"col-sm-4\">");
		out.println("<input type=\"text\" class=\"form-control\" name=\"name\" placeholder=\"Name\" value=\""+name+"\">");
		out.println("</div>");
		out.println("</div>");
		out.println("<br>");

		if(request.getAttribute("messageError") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("messageError") + "</p>");
		}

		String message = request.getParameter("message");
		if(message == null) message = "";
		
		out.println("<div class=\"form-group\">");
		out.println("<label class=\"col-sm-2 control-label\">Message</label>");
		out.println("<div class=\"col-sm-8\">");
		out.println("<textarea class=\"form-control\" name=\"message\" placeholder=\"Enter a message here please\" rows=\"20\" cols=\"20\">"+message+"</textarea>");
		out.print("<br>");
		out.println("<input class=\"btn btn-default\" type=\"submit\" name=\"addEntry\" value=\"Add Entry\">");
		out.println("</div>");
		out.print("</div>");
		out.println("</form>");

		out.println("</div>");
		out.println("</body>");

		out.println("</html>");


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String message = request.getParameter("message");

		boolean isValidName = name != null && name.trim().length() > 0;
		boolean isValidMessage = message != null && message.trim().length() > 0;


		// Validate the input
		if (isValidName && isValidMessage) {

			// Create a new entry
			GuestBookEntry entry = new GuestBookEntry(name, message);

			ArrayList<GuestBookEntry> entries
				= (ArrayList<GuestBookEntry>) getServletContext().getAttribute("entries");

			// Add the new entry to our array list of entries
			entries.add(entry);

			response.sendRedirect("GuestBook");
		}

		if(!isValidName) {
			request.setAttribute("nameError", "You must specify a name");
		}
		if(!isValidMessage) {
			request.setAttribute("messageError", "You must enter a message");
		}



		doGet(request, response);
	}

}
