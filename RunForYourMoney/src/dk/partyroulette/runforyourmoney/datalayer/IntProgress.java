package dk.partyroulette.runforyourmoney.datalayer;

public class IntProgress extends Progress
{
	private int progressInt;

	public IntProgress(int progressInt) 
	{
		super();
		this.progressInt = progressInt;
	}

	@Override
	public int getProgressInt()
	{
		return progressInt;
	}

}
