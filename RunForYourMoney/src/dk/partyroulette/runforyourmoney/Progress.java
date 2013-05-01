package dk.partyroulette.runforyourmoney;

public abstract class Progress 
{
	public Progress()
	{

	}

	protected abstract int getProgressInt();

	public int getPercentage(int max) 
	{
		int percentage = Math.round((((float) getProgressInt() / max) * 100));
		return percentage;
	}

}