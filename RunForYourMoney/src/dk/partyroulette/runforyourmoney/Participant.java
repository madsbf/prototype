package dk.partyroulette.runforyourmoney;

public class Participant {

	private String name;
	private String imageUrl;
	private int progress;
	
	public Participant(String name, String imageUrl, int progress)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.progress = progress;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getImageUrl()
	{
		return imageUrl;
	}
	
	public int getProgress()
	{
		return progress;
	}
}
