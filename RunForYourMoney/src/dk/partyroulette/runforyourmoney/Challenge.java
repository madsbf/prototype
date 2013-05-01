package dk.partyroulette.runforyourmoney;
import com.parse.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Challenge{

	private long id;

	private Goal goal;
	private Repetition repetition;
	private VerificationUnit vValue;
	private Date Deadline;
	private long betAmount;
	private String name;
	private String description;
	private boolean active;
	private int length;
	private Participant[] participants;
	
	public Challenge( String name, String description, Date deadline, Participant[] participants, boolean active, int length)
	{
		
		this.name = name;
		this.description = description;
		this.Deadline = deadline;
		this.participants = participants;
		this.active = active;
		this.length = length;
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
	
	public int getLength()
	{
		return length;
	}
	
	/**
	 * Add challenge to DB
	 * @param Challenge
	 */
	public static void addChallengeToDB(Challenge c){
		System.out.println("Saving challenge");
		ParseObject challenge = new ParseObject("challenge");

		// Change objects to simple types for db interpretation
		String[] participantNames = new String[c.getParticipants().length];
		String[] participantProgress = new String[c.getParticipants().length];
		String[] participantImageUrl = new String[c.getParticipants().length];
		boolean[] participantAccepted = new boolean[c.getParticipants().length];
		for(int i = 0; i<c.getParticipants().length; i++){
			participantNames[i] = c.getParticipants()[i].getName();
			participantProgress[i] = Integer.toString(c.getParticipants()[i].getProgress().getProgressInt());
			participantImageUrl[i] = c.getParticipants()[i].getImageUrl();
			participantAccepted[i] = c.getParticipants()[i].getAccepted();
		}
		challenge.put("active", c.isActive());
		challenge.put("participants", Arrays.asList(participantNames));
		challenge.put("progress",Arrays.asList(participantProgress));
		challenge.put("imageurl", Arrays.asList(participantImageUrl));
		challenge.put("accepted", Arrays.asList(participantAccepted));
		challenge.put("deadline", c.getDeadline().toString());
		challenge.put("typename", c.getTypeName());
		challenge.put("description", c.getDescription());
		challenge.put("name", c.getName());
		challenge.put("length", c.getLength());
		challenge.saveInBackground();
		
	}
	
	
	/**
	 * Retrieve all challenges for a specific user.
	 * @param retrievedObjectListener
	 */
	public static void retrieveChallengeFromDBForUser(final RetrievedObjectListener retrievedObjectListener, final String user){
		ParseQuery query = new ParseQuery("challenge");
		 query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> objects, ParseException e) {
		         if (e == null) {
		        	 System.out.println("Retrieved challenges.");
		        	 if(retrievedObjectListener != null){
		        		 retrievedObjectListener.onRetrievedChallengeObjects(generateChallengeObjectsForUser(objects, user));
		        	 }
		        	 
		         } else {
		             System.out.println("Couldn't retrieve challenges. Error: "+e.toString());
		         }
		     }
		 });
	}
	
	/**
	 * Generate challenge objects from parse objects.
	 * 
	 * @param Parse objects
	 * @return a list of challenges
	 */
	private static List<Challenge> generateChallengeObjectsForUser(List<ParseObject> objects, String user){
		List<Challenge> challenges = new ArrayList<Challenge>();
		for(ParseObject o : objects){
			
			//Generate Participant Object
			
			List<String> participantNames = o.getList("participants");	
			// Check if user of app is participant in challenge
			if(!participantNames.contains(user)){
				continue;
			}
			List<Boolean> participantAccepted = o.getList("accepted");
			List<String> participantProgresses = o.getList("progress");
			List<String> participantImageUrls = o.getList("imageurl");
			
			List<Participant> participants = new ArrayList<Participant>();
			for(int i = 0; i<participantNames.size(); i++){
				Participant p = new Participant(participantNames.get(i), participantImageUrls.get(i), new IntProgress(Integer.parseInt(participantProgresses.get(i))),participantAccepted.get(i));
				participants.add(p);
			}

			boolean active =Boolean.valueOf(o.getString("active"));
			Date deadline = new Date(o.getString("deadline"));
			String description = o.getString("description");
			String name = o.getString("name");
			int length = Integer.parseInt(o.getString("length"));
			
			
			Challenge c = new Challenge(name, description, deadline, (Participant[]) participants.toArray(), active, length);
			challenges.add(c);
		}
		return challenges;
	}




}
