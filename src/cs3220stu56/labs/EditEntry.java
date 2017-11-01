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


@WebServlet("/requests/EditEntry")
public class EditEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	GuestBookEntry getEntry(HttpServletRequest request) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			ArrayList<GuestBookEntry> entries = (ArrayList<GuestBookEntry>) getServletContext().getAttribute("entries");
			
			for(GuestBookEntry entry:entries)
				if(entry.getId() == id)
					return entry;
		}
		catch(Exception e) {}
		return null;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the ID of the entry we are editing from the query search
		
		GuestBookEntry entry = getEntry(request);
		
		if(entry == null) {
			response.sendRedirect("GuestBook");
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
		out.println("    <title>Edit Entry</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"page-header\">");
		out.println("    <h1>Edit Entry <small>Guest Book</small></h1>");
		out.println("</div>");
		
		//Form
		out.println("<form class=\"form-horizontal\" action=\"EditEntry\" method=\"post\">");
		
		//Form Group for Name
		out.println("<div class=\"form-group\" >");
		out.println("<label class=\"col-sm-2 control-label\">Name</label> ");
		out.println("<div class=\"col-sm-4\">");
		out.println("<input type=\"text\" class=\"form-control\" name=\"name\" placeholder=\"Name\" value=\""+entry.getName()+"\">");
		out.println("</div>");
		out.println("</div>");
		out.println("<br>");
		
		// Form Group for Message and Submit button
		out.println("<div class=\"form-group\">");
		out.println("<label class=\"col-sm-2 control-label\">Message</label>");
		out.println("<div class=\"col-sm-8\">");
		out.println("<textarea class=\"form-control\" name=\"message\" placeholder=\"Enter a message here please\" rows=\"20\" cols=\"20\">"+entry.getMessage()+"</textarea>");
		out.print("<br>");
		out.println("<input type=\"submit\" name=\"editEntry\" value=\"Edit Entry\">");
		out.println("<input type=\"hidden\" name=\"id\" value=\""+entry.getId()+"\">");
		out.println("</div>");
		out.print("</div>");
		out.println("</form>");
		
		out.println("</form>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
			
	
		}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookEntry entry = getEntry(request);
		String newName = request.getParameter("name");
		String newMessage = request.getParameter("message");
		
		entry.setName(newName);
		entry.setMessage(newMessage);
		
		response.sendRedirect("GuestBook");
		
		doGet(request, response);
	}

}
