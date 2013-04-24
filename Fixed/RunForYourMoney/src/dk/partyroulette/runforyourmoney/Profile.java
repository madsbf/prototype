package dk.partyroulette.runforyourmoney;

public class Profile {

	private String name;
	private String imageUrl;
	
	public Profile(String name, String imageUrl)
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
