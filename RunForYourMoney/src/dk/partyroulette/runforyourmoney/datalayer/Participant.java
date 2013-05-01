package dk.partyroulette.runforyourmoney.datalayer;

public class Participant {

	private String name;
	private String imageUrl;
	private Progress progress;
	private boolean hasAccepted;

	public Participant(String name, String imageUrl, Progress progress, boolean hasAccepted)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.progress = progress;
		this.hasAccepted = hasAccepted;
	}
	
	public Participant(String name, String imageUrl, Progress progress)
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

	public Progress getProgress()
	{
		return progress;
	}
	
	public boolean getAccepted()
	{
		return hasAccepted;
	}
}