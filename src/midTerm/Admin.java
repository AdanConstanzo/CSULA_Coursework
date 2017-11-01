package midTerm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tutor;


@WebServlet("/midterm/Admin")
public class Admin extends HttpServlet {
	
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
		out.println("    <title>ADMIN</title>");
		out.println("</head>");
		
		/* Page Body goes here */
		out.println("<body>");
		
		
		out.println("<div class=\"container\">");
		
		out.println("<div class=\"page-header\">");
		out.println("    <h1>Admin's <small>list of tutors</small></h1>");
		out.println("</div>");
		
		String[] params = request.getParameterValues("query");
		
		out.println("<form class=\"form-inline\" action=\"Admin\" method=\"get\">");
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
		
		out.println("<h3>Tutor Information </h3>");
        out.println("<table class=\"table table-bordered table-striped table-hover\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Name</th>");
        out.println("<th>Email</th>");
        out.println("<th>Courses</th>");
        out.println("<th>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        
        ArrayList<Tutor> tutors 
		= (ArrayList<Tutor>) getServletContext().getAttribute("tutors");
        
        if(params == null) {
			for (Tutor tutor : tutors) 
				printAllTable(tutor,out);
		
		} else if (params[0].length()== 0) {
			for (Tutor tutor : tutors) 
				printAllTable(tutor,out);
			
		}else {
			for(Tutor tutor: tutors) {
				boolean nameCheck = tutor.getFullName().toUpperCase().contains(params[0].toUpperCase());
				boolean emailCheck = tutor.getEmail().toUpperCase().contains(params[0].toUpperCase());
				if(nameCheck || emailCheck) {
					printAllTable(tutor,out);
				}else {
					String[] temp = tutor.getCourses();
					for(String s:temp) {
						if( s.toUpperCase().contains(params[0].toUpperCase()) ) {
							printTable(tutor,s,out);
						}
					}
				}
			}
		}
        
        out.println("</tbody>");
        out.println("</table>");
        out.println("<a href=\"HomePage\">Home Page</a>");
        out.println("</div>");
		out.println("</body>");
		
		out.println("</html>");
		
	}
	
	void printAllTable(Tutor tutor,PrintWriter out) {
		String[] temp = tutor.getCourses();
    	for(String course:temp) {
    		out.println("<tr>");
            out.println("<td>"+tutor.getFullName()+"</td>");
            out.println("<td>"+tutor.getEmail()+"</td>");
            out.println("<td>"+course+"</td>");
            out.println("<td>"+course+"</td>");
            out.println("<td>");
            out.println("<a href=\"DeleteTutor?id="+tutor.getId()+"\">Delete</a>");
            out.println("</td>");
            out.println("</tr>");
    	}
	}
	
	void printTable(Tutor tutor,String course,PrintWriter out) {
		out.println("<tr>");
	    out.println("<td>"+tutor.getFullName()+"</td>");
	    out.println("<td>"+tutor.getEmail()+"</td>");
	    out.println("<td>"+course+"</td>");
	    out.println("</tr>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
