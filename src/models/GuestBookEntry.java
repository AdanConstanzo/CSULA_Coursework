package models;

import java.util.Date;

public class GuestBookEntry {

	int id;
	Date created;
	String name;
	String message;
	
	static int count = 0;
	
	public GuestBookEntry(String name, String message) {
		super();
		this.name = name;
		this.message = message;
		this.id = count++;
		this.created = new Date();
	}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId() {
		return id;
	}	
}
