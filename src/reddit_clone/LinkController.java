package reddit_clone;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet("/Links")
public class LinkController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LinkController() {
        super();
        
    }
    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection c = null;
        try
        {
        	String url="jdbc:mysql://cs3.calstatela.edu/cs3220stu56";
        	String user="cs3220stu56";
        	String password="x0V4ihSd";

            c = DriverManager.getConnection( url, user, password );
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select * from Links" );
            
            ArrayList<Link> links = new ArrayList<Link>();
            while( rs.next() )
            {
            	links.add(new Link(rs.getString( "title" ),rs.getString( "link" ), rs.getInt("votes"), rs.getInt("id")));
            }
            getServletContext().setAttribute( "links", links );
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }

		
		request.getRequestDispatcher( "/Link.jsp" ).forward(
	            request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
