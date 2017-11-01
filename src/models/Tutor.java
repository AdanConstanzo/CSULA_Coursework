package models;

public class Tutor {
	
	static int count = 0;
	String fullname, email;
	String[] courses;
	int id;
	
	public Tutor(String fullname, String email, String[] course) {
		
		this.id = count++;
		this.fullname = fullname;
		this.email = email;
		this.courses = new String[course.length];
		// go through every element of course and assign it.
		for(int i = 0; i < course.length; i++)
			this.courses[i] = course[i];		
	}

	public String getFullName() { return fullname; }
	public void setFullName(String fullname) { this.fullname = fullname; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String[] getCourses() {return courses;}
	public void setCourses(String[] courses) {this.courses = courses;}
	public int getId() { return id;}
	
} // End of class Tutor.
