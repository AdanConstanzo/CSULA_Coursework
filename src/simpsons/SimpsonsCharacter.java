package simpsons;

public class SimpsonsCharacter {

	String name;
	int numberOfPhotos;
	String directoryName;
	int id = 0;
	static int count= 0;
	public SimpsonsCharacter( String name, int numberOfPhotos )
	{
	    this.id = this.count;
	    this.count ++;
	    this.name = name;
	    this.numberOfPhotos = numberOfPhotos;
	    this.directoryName = name.toLowerCase().replace(" ", "_");
	}
	
	public Integer getId()
	{
	    return id;
	}
	
	public String getDirectoryName() {
		return directoryName;
	}
	
	public String getName()
	{
	    return name;
	}
	
	public void setName( String name )
	{
	    this.name = name;
	}
	
	public int getNumberOfPhotos() {
		return numberOfPhotos;
	}
	
	public void setNumberOfPhotos(int numberOfPhotos) {
		this.numberOfPhotos = numberOfPhotos;
	}
}
