package dk.partyroulette.runforyourmoney;

public class Participant {

	private String name;
	private String imageUrl;
	
	public Participant(String name, String imageUrl)
	{
		this.name = name;
		this.imageUrl = imageUrl;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getImageUrl()
	{
		return imageUrl;
	}
}
