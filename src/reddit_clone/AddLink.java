package reddit_clone;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class AddLink
 */
@WebServlet("/AddLink")
public class AddLink extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
   
    public AddLink() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String link = request.getParameter("link");
		String title = request.getParameter("title");
		HttpSession session = request.getSession();

		String error = "";
		Connection c = null;
		try {
			String url="jdbc:mysql://cs3.calstatela.edu/cs3220stu56";
        	String user="cs3220stu56";
        	String password="x0V4ihSd";

			String sql = "insert into Links (title, link, votes) values (?, ?, ?)";

			c = DriverManager.getConnection(url, user, password);
			if (link == null || link.equals("") || title == null || title.equals("")) {
				response.sendRedirect("Links");
				return;
			} else {

				PreparedStatement pstmt = (PreparedStatement) c.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, link);
				pstmt.setInt(3, 1);
				pstmt.executeUpdate();
			}

			session.setAttribute("error", error);

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

}
