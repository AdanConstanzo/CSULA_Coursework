package reddit_clone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownVote
 */
@WebServlet("/DownVote")
public class DownVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DownVote() {
        super();
        // TODO Auto-generated constructor stub
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Integer idpars = Integer.parseInt(id);
		
		Connection c = null;
		try {
			String url="jdbc:mysql://cs3.calstatela.edu/cs3220stu56";
        	String user="cs3220stu56";
        	String password="x0V4ihSd";
			String sql = "update Links set votes = votes-1 where id = ?;";
			c = DriverManager.getConnection(url, user, password);
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, idpars);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
		
		response.sendRedirect("Links");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
