package dk.partyroulette.runforyourmoney;

public class HealthChallenge extends Challenge 
{

	public HealthChallenge(long id, String name, String description,
			Repetition repetition, Participant[] participants, boolean active) 
	{
		super(id, name, description, repetition, participants, active);
	}
	
	@Override
	public String getTypeName()
	{
		return "Health Challenge";
	}


}
