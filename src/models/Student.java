package models;

public class Student {

	static int count = 0;
	static final int NUMBER_OF_ASSIGNMENTS = 5;
	
	int id;	
	String firstName, lastName;	
	String email;
	String password;
	
	double[] scores = new double[NUMBER_OF_ASSIGNMENTS];
	
	public Student(String firstName, String lastName, String email, String password) {
		
		this.id = count++;
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		
		// Randomly assign scores to this student;
		for (int i = 0; i < NUMBER_OF_ASSIGNMENTS; i++)
			scores[i] = Math.random() * 100;
				
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public double[] getScores() {
		return scores;
	}

	public void setScores(double[] scores) {
		this.scores = scores;
	}

	public int getId() {
		return id;
	}
		
	
	
}
