package cs3220stu56.labs;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Labs/RequestSummary")
public class RequestSummary extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    
		response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();
        SimpleDateFormat df2 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
        String dateText = df2.format(new Date());
        
        out.println( "<html>" );
        out.println( "<head><title>Display Request Info</title>");
        out.println("<link rel=\\\"stylesheet\\\" href=\\\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\\\" integrity=\\\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\\\" crossorigin=\\\"anonymous\\\">");
        out.println("<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\" ");
        out.println("</head>" );
        
        out.println( "<body>" );
        
        
        out.println("<div class=\"container\">");
        
        out.println("<div class=\"jumbotron\">");
        out.println("<h1>Request Paramaters <small>lab 2</small></h1>");
        out.println("<p> The Following <code>"+request.getMethod()+"</code> request was submited on <code>"+dateText+"</code>.</p>");
        out.println("</div>");
        
        out.println("<h3>Request Paramaters</h3>");
        out.println("<table class=\"table table-bordered table-striped table-hover\" >");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Paramater Name</th>");
        out.println("<th>Paramater Value</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        Enumeration<String> paramaterNames = request.getParameterNames();
        while (paramaterNames.hasMoreElements()) {
            String param = paramaterNames.nextElement();
            String[] value = request.getParameterValues(param);
            out.println("<tr>");
            out.println("<td>"+param+"</td>");
            out.println("<td>");
            for(String i : value) {
                out.println("<span class=\"label label-info\" >"+i+"</span>");
            }
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        
        out.println("<h3>Header Information</h3>");
        out.println("<table class=\"table table-bordered table-striped table-hover\">");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Header Field</th>");
        out.println("<th>Header Value</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
        	String currentHeader = headerNames.nextElement();
        	if(currentHeader != null) {
        		String headerValue = request.getHeader(currentHeader);
        		out.println("<tr>");
                out.println("<td>"+currentHeader+"</td>");
                out.println("<td>");
                out.println("<span class=\"label label-info\" >"+ headerValue +"</span>");
                out.println("</td>");
                out.println("</tr>");
        	}
        }
        out.println("</tbody>");
        out.println("</table>");
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
