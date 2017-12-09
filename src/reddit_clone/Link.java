package reddit_clone;

public class Link {
	String title,link;
	int votes;
	int id;
	
	public Link( String title, String link, int votes, int id)
	{
	    this.id = id;
	    this.title = title;
	    this.link = link;
	    this.votes = votes;
	}
	
	public Integer getId()
	{
	    return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLink()
	{
	    return link;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public void setTitle( String title )
	{
	    this.title = title;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}

}
