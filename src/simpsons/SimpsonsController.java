package simpsons;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpsonsController")
public class SimpsonsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SimpsonsController() {
        super();
        
    }
    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );
        ArrayList<SimpsonsCharacter> characters = new ArrayList<SimpsonsCharacter>();
        characters.add(new SimpsonsCharacter("Homer Simpson",2246) );
        characters.add(new SimpsonsCharacter("Ned Flanders",1454) );
        characters.add(new SimpsonsCharacter("Moe Szyslak",1452) );
        characters.add(new SimpsonsCharacter("Lisa Simpson",1354) );
        characters.add(new SimpsonsCharacter("Bart Simpson",1342) );
        characters.add(new SimpsonsCharacter("Marge Simpson",1291) );
        characters.add(new SimpsonsCharacter("Krusty The Clown",1206) );
        characters.add(new SimpsonsCharacter("Principal Skinner",1194) );
        characters.add(new SimpsonsCharacter("Charles Montgomery Burns",1193) );
        characters.add(new SimpsonsCharacter("Milhouse Van Houten",1079) );
        characters.add(new SimpsonsCharacter("Chief Wiggum",986) );
        characters.add(new SimpsonsCharacter("Abraham Grampa Simpson",913) );
        characters.add(new SimpsonsCharacter("Sideshow Bob",877) );
        characters.add(new SimpsonsCharacter("Apu Nahasapeemapetilon",623) );
        characters.add(new SimpsonsCharacter("Kent Brockman",498) );
        characters.add(new SimpsonsCharacter("Comic Book Guy",469) );
        characters.add(new SimpsonsCharacter("Edna Krabappel",457) );
        characters.add(new SimpsonsCharacter("Nelson Muntz",358) );
        characters.add(new SimpsonsCharacter("Lenny Leonard",310) );
        characters.add(new SimpsonsCharacter("Mayor Quimby",246) );
        characters.add(new SimpsonsCharacter("Waylon Smithers",181) );
        characters.add(new SimpsonsCharacter("Maggie Simpson",128) );
        characters.add(new SimpsonsCharacter("Groundskeeper Willie",121) );
        characters.add(new SimpsonsCharacter("Barney Gumble",106) );
        characters.add(new SimpsonsCharacter("Selma Bouvier",103) );
        characters.add(new SimpsonsCharacter("Carl Carlson",98) );
        characters.add(new SimpsonsCharacter("Ralph Wiggum",89) );
        characters.add(new SimpsonsCharacter("Patty Bouvier",72) );
        characters.add(new SimpsonsCharacter("Martin Prince",71) );
        characters.add(new SimpsonsCharacter("Professor John Frink",65) );
        characters.add(new SimpsonsCharacter("Snake Jailbird",55) );
        characters.add(new SimpsonsCharacter("Cletus Spuckler",47) );
        characters.add(new SimpsonsCharacter("Rainier Wolfcastle",45) );
        characters.add(new SimpsonsCharacter("Agnes Skinner",42) );
        characters.add(new SimpsonsCharacter("Sideshow Mel",40) );
        characters.add(new SimpsonsCharacter("Otto Mann",32) );
        characters.add(new SimpsonsCharacter("Fat Tony",27) );
        characters.add(new SimpsonsCharacter("Gil",27) );
        characters.add(new SimpsonsCharacter("Miss Hoover",17) );
        characters.add(new SimpsonsCharacter("Disco Stu",8) );
        characters.add(new SimpsonsCharacter("Troy Mcclure",8) );
        characters.add(new SimpsonsCharacter("Lionel Hutz",3) );
        characters.add(new SimpsonsCharacter("Jimbo Jones",0) );
        characters.add(new SimpsonsCharacter("Bumblebee Man",0) );
        characters.add(new SimpsonsCharacter("Hans Moleman",0) );
        characters.add(new SimpsonsCharacter("Helen Lovejoy",0) );
        characters.add(new SimpsonsCharacter("Jasper Beardly",0) );
        getServletContext().setAttribute( "characters", characters );
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( "/Simpsons.jsp" ).forward(
	            request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
