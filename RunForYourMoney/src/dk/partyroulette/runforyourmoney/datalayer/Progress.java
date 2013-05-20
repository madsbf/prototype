package dk.partyroulette.runforyourmoney.datalayer;

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
	public int getInteger(){
		return getProgressInt();
	}

}