package dk.partyroulette.runforyourmoney.datalayer;

import java.util.ArrayList;
import java.util.List;
import dk.partyroulette.runforyourmoney.control.*;


import com.parse.*;


public class Contact {
	public Contact(String firstname, String lastname){
		setFirstName(firstname);
		setLastName(lastname);
	}
	
	/**
	 * The first name of the contact.
	 */
	private String FirstName;
	public String getFirstName(){
		return this.FirstName;
	}
	public void setFirstName(String firstname){
		this.FirstName = firstname;
	}
	
	/**
	 * The last name of the contact.
	 */
	private String LastName;
	public String getLastName(){
		return this.LastName;
	}
	public void setLastName(String lastname){
		this.LastName = lastname;
	}
	
	
	/**
	 * Unique identifier of the user.
	 */
	private String contactId;
	public String getId(){
		return this.contactId;
	}
	public void setId(String Id){
		this.contactId = Id;
	}
	
	/**
	 * Add an object of any type to the database.
	 * If the type of object haven't been added,
	 * a new table will be created corresponding to
	 * the object.
	 * @param The name of the object table.
	 * @param An array of the parameters acting as
	 * attributes in the database table.
	 * @param Values added for each parameter of
	 * the object.
	 */
	public static boolean addObjectToDB(String objectType, String[] objectParameters, String[] obJectValues){
		System.out.println("Saving object");
		ParseObject obj = new ParseObject(objectType);
		if(objectParameters.length != obJectValues.length){
			return false;
		}
		for(int i = 0; i<objectParameters.length; i++){
			obj.put(objectParameters[i], obJectValues);
		}
		obj.saveInBackground();
		return true;
	}
	
	/**
	 * Retrieve all objects of a certain type from
	 * the Parse database.
	 * @param The name of the object table.
	 */
	public static void retrieveObjectsFromDB(String objectType,final RetrievedObjectListener retrievedObjectListener){
		ParseQuery query = new ParseQuery(objectType);
		 query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> objects, ParseException e) {
		         if (e == null) {
		        	 System.out.println("objects.");
		        	 if(retrievedObjectListener != null){
		        		 retrievedObjectListener.onRetrievedObject(objects);
		        	 }
		        	 
		         } else {
		             System.out.println("Couldn't retrieve objects. Error: "+e.toString());
		         }
		     }
		 });
	}
	
	
	/**
	 * Add contact to Parse database.
	 * @param contact object
	 */
	public static void addContactToDB(Contact c){
		System.out.println("Saving contact");
		ParseObject contact = new ParseObject("contact");
		contact.put("firstname", c.FirstName);
		contact.put("lastname", c.LastName);
		contact.saveInBackground();
		// Save user name settings into installation table
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put("fullname",c.FirstName+c.LastName);
		installation.saveInBackground();
		
	}

	/**
	 * Retrieve all contact into subscribers interface instance 'RetrievedObjectListener'.
	 * @param retrievedObjectListener
	 */
	public static void retrieveAllContacts(final RetrievedObjectListener retrievedObjectListener){
	
		ParseQuery query = new ParseQuery("contact");
		 query.findInBackground(new FindCallback() {
		     public void done(List<ParseObject> objects, ParseException e) {
		         if (e == null) {
		        	 System.out.println("Retrieved contacts.");
		        	 if(retrievedObjectListener != null){
		        		 retrievedObjectListener.onRetrievedContactObject(generateContactObjects(objects));
		        	 }
		        	 
		         } else {
		             System.out.println("Couldn't retrieve contacts. Error: "+e.toString());
		         }
		     }
		 });
	}
	
	/**
	 * Generate contact objects from parse objects.
	 * 
	 * @param Parse objects
	 * @return a list of contacts
	 */
	private static List<Contact> generateContactObjects(List<ParseObject> objects){
		List<Contact> contacts = new ArrayList<Contact>();
		for(ParseObject o : objects){
			Contact c = new Contact(o.getString("firstname"), o.getString("lastname"));
			contacts.add(c);
		}
		return contacts;
	}
	
	/**
	 * Send notification to contact.
	 * @param notification message
	 * @param fullname
	 */
	public static void sendNotification(String notificationMsg, String fullname){
		//JSONObject data = new JSONObject("{\"title\":\"Run Notification\",\"alert\": \""+notificationMsg+"\"}");
		
		// Create our Installation query
		ParseQuery pushQuery = ParseInstallation.getQuery();
		pushQuery.whereEqualTo("fullname", fullname);
		 
		// Send push notification to query
		ParsePush push = new ParsePush();
		push.setQuery(pushQuery); // Set our Installation query
		push.setMessage(notificationMsg);
		push.sendInBackground();
	}
	
	/**
	 * Check whether the app is already installed on the device.
	 * @return true if the app is already installed otherwise false.
	 */
	public static boolean checkForInstallation(){
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.getInstallationId();
		String fullname = installation.getString("fullname");
		System.out.println("FULLNAME: "+fullname);
		if(fullname == null){
			return false;
		}
		return true;
		
	}
	
	/**
	 * Get name of the user who has registered with the app.
	 * @return the full name of the user (this acts like the unique identifier) in this app.
	 */
	public static String getCurrentUser(){
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.getInstallationId();
		String fullname = installation.getString("fullname");
		return fullname;
	}
	
	
}


