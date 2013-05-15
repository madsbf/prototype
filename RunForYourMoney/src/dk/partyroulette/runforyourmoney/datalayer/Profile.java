package dk.partyroulette.runforyourmoney.datalayer;

public class Profile {

	private String name;
	private String imageUrl;
	private int coins;
	
	public Profile(String name, String imageUrl, int coins)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.coins = coins;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getImageUrl()
	{
		return imageUrl;
	}
	
	public int getCoins()
	{
		return coins;
	}
}
