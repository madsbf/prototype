package dk.partyroulette.runforyourmoney;

public class LearningChallenge extends Challenge 
{

	public LearningChallenge(long id, String name, String description,
			Repetition repetition, Participant[] participants, boolean active) 
	{
		super(id, name, description, repetition, participants, active);
	}
	
	@Override
	public String getTypeName()
	{
		return "Learning Challenge";
	}

}
