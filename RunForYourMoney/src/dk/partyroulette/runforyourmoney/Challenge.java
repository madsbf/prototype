package dk.partyroulette.runforyourmoney;

import java.util.Date;

public class Challenge {

	private long id;

	private Goal goal;
	private Repetition repetition;
	private VerificationUnit vValue;
	private Date Deadline;
	private long betAmount;

	private Participant[] participants;

	private String name;

	private String description;

	private boolean active;

	public Challenge(long id, String name, String description, Repetition repetition, Participant[] participants, boolean active)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.repetition = repetition;
		this.participants = participants;
		this.active = active;
	}

	public boolean isActive()
	{
		return active;
	}

	public Participant[] getParticipants()
	{
		return participants;
	}

	public Repetition getRepetition()
	{
		return repetition;
	}

	public String getTypeName()
	{
		return "Running Challenge";
	}

	public String getDescription()
	{
		return description;
	}

	public String getName()
	{
		return name;
	}

	public Long getId()
	{
		return id;
	}

}
