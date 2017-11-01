package simpsons;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/CharacterProfile")
public class CharacterProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private SimpsonsCharacter getEntry(Integer id ) {
        List<SimpsonsCharacter> entries = (List<SimpsonsCharacter>) getServletContext().getAttribute(
            "characters" );
        for( SimpsonsCharacter entry : entries )
            if( entry.getId().equals( id ) ) return entry;
        return null;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.valueOf( request.getParameter( "id" ) );
        SimpsonsCharacter character = getEntry( id );
        if(character == null) {
        	request.getRequestDispatcher( "ErrorId.jsp" ).forward(
                    request, response );
        }
        
        if(character.numberOfPhotos>1) {
	        
	        int r = (int) (Math.random() * (character.numberOfPhotos));
	        String formatted = String.format("%04d", r);
	        request.setAttribute( "character", character );
	        request.setAttribute("randomNumber", formatted);
	        request.getRequestDispatcher( "CharacterProfile.jsp" ).forward(
	            request, response );
	    }else {
	    	request.setAttribute("character", character);
	    	request.getRequestDispatcher( "NoImage.jsp" ).forward(
		            request, response );
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
