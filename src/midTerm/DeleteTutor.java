package midTerm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.Tutor;


@WebServlet("/midterm/DeleteTutor")
public class DeleteTutor extends HttpServlet {
	
	void deletEntry(HttpServletRequest request) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			ArrayList<Tutor> entries = (ArrayList<Tutor>) getServletContext().getAttribute("tutors");
			int i = 0;
			for(Tutor entry:entries) {
				if(entry.getId() == id)
					entries.remove(i);
				i++;
			}
		}
		catch(Exception e) {}
		return;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deletEntry(request);
		response.sendRedirect("Admin");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
