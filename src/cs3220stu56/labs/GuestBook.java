package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.GuestBookEntry;


@WebServlet("/requests/GuestBook")
public class GuestBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		ArrayList<GuestBookEntry> entries = new ArrayList<GuestBookEntry>();
		entries.add(new GuestBookEntry("John", "Hello!"));
		entries.add(new GuestBookEntry("Mary", "Hi!"));
		entries.add(new GuestBookEntry("Joe", "Howdy!"));
		
		ServletContext context = this.getServletContext();
		context.setAttribute("entries", entries);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Set the content type
		response.setContentType("text/html");
		//request.getParameterValues
		
		
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
		out.println("    <title>Guest Book</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		
		String[] params = request.getParameterValues("query");
		
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"page-header\">");
		out.println("    <h1>Guest Book<small>HttpServletRequest</small></h1>");
		out.println("</div>");
		
		out.println("<form class=\"form-inline\" action=\"GuestBook\" method=\"get\">");
		out.println("<div class=\"form-group\">");
		// input search 
		if(params == null)
			out.println("<input class=\"form-control\" type=\"text\" name=\"query\" value=\"\" placeholder=\"Enter your search term(s)\">");
		else if (params[0].length() == 0)
			out.println("<input class=\"form-control\" type=\"text\" name=\"query\" value=\"\" placeholder=\"Enter your search term(s)\">");
		else
			out.println("<input class=\"form-control\" type=\"text\" name=\"query\" value=\""+params[0]+"\" placeholder=\"Enter your search term(s)\">");
		
		out.println("<input class=\"form-control btn btn-primary\" type=\"submit\" value=\"Search\">");
		out.println("</div>");
		out.println("</form>");
		out.println("<hr>");
		
		
		//Table of entries 
		out.println("<table class=\"table table-bordered table-striped table-hover\">");
		out.println("<tr>");
		out.println("  <th>Name</th><th>Message</th><th>Date</th><th>Actions</th>");
		out.println("</tr>");
		
		ArrayList<GuestBookEntry> entries 
		= (ArrayList<GuestBookEntry>) getServletContext().getAttribute("entries");
		
		if(params == null) {
			for (GuestBookEntry entry : entries) 
				printTable(entry,out);
		
		} else if (params[0].length()== 0) {
			for (GuestBookEntry entry : entries) 
				printTable(entry,out);
			
		}else {
			for(GuestBookEntry entry: entries) {
				boolean nameCheck = entry.getName().toUpperCase().contains(params[0].toUpperCase());
				boolean messageCheck = entry.getMessage().toUpperCase().contains(params[0].toUpperCase());
				if(nameCheck || messageCheck)
					printTable(entry,out);
			}
		}
		
		out.println("</table>");
		
		out.println("<a href=\"AddEntry\">Add a New Entry</a>");
		out.println("<br>");
		out.println("<a href=\"Admin\">Admin's Page</a>");
		
		out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
		

	}
	
	void printTable(GuestBookEntry entry,PrintWriter out) {
		
		out.println("<tr>");
		out.println("  <td>" + entry.getName() + "</td>");
		out.println("  <td>" + entry.getMessage() + "</td>");
		out.println("  <td>" + entry.getCreated() + "</td>");
		out.println("  <td>");
		out.println("<a style=\"margin-right:5px;\" href=\"EditEntry?id=" + entry.getId()+"\">Edit</a>");
		out.println("<a href=\"DeleteEntry?id="+entry.getId()+"\">Delete</a>");
		out.println("</td>");
		out.println("<tr>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
