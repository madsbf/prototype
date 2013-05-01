package dk.partyroulette.runforyourmoney;

public class MiscChallenge extends Challenge {

	public MiscChallenge(long id, String name, String description,
			Repetition repetition, Participant[] participants, boolean active) 
	{
		super(id, name, description, repetition, participants, active);
	}

	@Override
	public String getTypeName()
	{
		return "Challenge";
	}

}