package dk.partyroulette.runforyourmoney;
import com.parse.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

public class Challenge {

	private long id;

	private Goal goal;
	private Repetition repetition;
	private VerificationUnit vValue;
	private Date Deadline;
	private long betAmount;
	private String name;
	private String description;
	private boolean active;
	
	private Participant[] participants;
	
	public Challenge( String name, String description, Date deadline, Participant[] participants, boolean active)
	{
		
		this.name = name;
		this.description = description;
		this.Deadline = deadline;
		this.participants = participants;
		this.active = active;
	}
	
	public Challenge(long id, String name, String description,
			Repetition repetition, Participant[] participants, boolean active){
		// TODO Dummie for test code in rest of app
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
	
	public Date getDeadline()
	{
		return Deadline;
	}
	
	public static void addChallengeToDB(Challenge c){
		System.out.println("Saving challenge");
		ParseObject challenge = new ParseObject("challenge");

		// Change objects to simple types for db interpretation
		String[] participantNames = new String[c.getParticipants().length];
		String[] participantProgress = new String[c.getParticipants().length];
		String[] participantImageUrl = new String[c.getParticipants().length];
		for(int i = 0; i<c.getParticipants().length; i++){
			participantNames[i] = c.getParticipants()[i].getName();
			participantProgress[i] = Integer.toString(c.getParticipants()[i].getProgress().getProgressInt());
			participantImageUrl[i] = c.getParticipants()[i].getImageUrl();
		}
		challenge.put("active", c.isActive());
		challenge.put("participants", Arrays.asList(participantNames));
		challenge.put("progress",Arrays.asList(participantProgress));
		challenge.put("imageurl", Arrays.asList(participantImageUrl));
		challenge.put("deadline", c.getDeadline().toString());
		challenge.put("typename", c.getTypeName());
		challenge.put("description", c.getDescription());
		challenge.put("name", c.getName());
		challenge.saveInBackground();
		
	}
	
	public static void retrieveChallengeFromDB(){
		
	}

}
