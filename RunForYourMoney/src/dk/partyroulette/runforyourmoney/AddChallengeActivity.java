package dk.partyroulette.runforyourmoney;

import java.text.ParseException;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.datalayer.*;
import dk.partyroulette.runforyourmoney.control.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.parse.ParseObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddChallengeActivity extends Activity implements OnClickListener, RetrievedObjectListener
{
	private ImageButton buttonChallenge;
	private EditText editName;
	private EditText editLength;
	private EditText editDeadline;
	private EditText editCoins;
	private Spinner addFriends;
	private Spinner addedFriends;
	private ImageButton buttonAdd;
	private ImageButton buttonRemove;
	
	private List<String> friendsAdded;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_challenge);
		
		
		friendsAdded = new ArrayList<String>();
		
		// Retrieve all contact elements.
		Contact.retrieveAllContacts(this);

		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		registerViews();
		registerOnClickListener();
	}
	
	/**
	 * Assign all view components to variables.
	 */
	private void registerViews()
	{
		buttonChallenge = (ImageButton) findViewById(R.id.buttonChallenge);
		editName = (EditText) findViewById(R.id.editName);
		editLength = (EditText) findViewById(R.id.editLength);
		editDeadline = (EditText) findViewById(R.id.editDeadline);
		editCoins = (EditText) findViewById(R.id.editCoins);
		addFriends = (Spinner) findViewById(R.id.challengeFriendSpinner);
		addedFriends = (Spinner) findViewById(R.id.removeFriendSpinner);
		buttonAdd = (ImageButton) findViewById(R.id.addButton);
		buttonRemove = (ImageButton) findViewById(R.id.removeFriend);
	}
	
	private void registerOnClickListener()
	{
		buttonChallenge.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) 
	{
		// If any of the fields are not filled out an alert shall show.
		if(editName.getText().toString() == "" || editLength.getText().toString() == "" || editDeadline.getText().toString() == "" || editCoins.getText().toString() == "" || friendsAdded.size() == 0){
			showAlert("Please fill out all information.");
			return;
		}
		
		// Set date object
		SimpleDateFormat deadline = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		deadline.setLenient(false);
		Date d = null;
		try {
			d = deadline.parse(editDeadline.getText().toString());
			if(d.getTime() < date.getTime()){
				showAlert("Please enter date in the future.");
				return;
			}
		} catch (ParseException e) {
			showAlert("Please enter date in the correct format.");
			return;
		}
		
		// Set participants and corresponding progress objects.
		Participant[] participants= new Participant[friendsAdded.size()+1];
		for(int i = 0; i<participants.length-1; i++){
			Participant participant = new Participant(friendsAdded.get(i), "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(0),false);
			participants[i] = participant;
		}
		Participant p = new Participant(Contact.getCurrentUser(), "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(0),true);
		participants[friendsAdded.size()] = p;
		
		// Save challenge in DB
		Challenge c = new Challenge(editName.getText().toString(), "challenge", d, participants, true, Integer.parseInt(editLength.getText().toString()),Contact.getCurrentUser());
		Challenge.addChallengeToDB(c);
		
		// Send push messages to participants
		for(String n: friendsAdded){
			Contact.sendNotification("You have been invited to challenge: "+editName.getText().toString(), n);
		}
		Contact.sendNotification("You are the owner of challenge: "+editName.getText().toString(), Contact.getCurrentUser());
		
		this.finish();
	}
	
	/**
	 * Show alert with message.
	 * @param Message
	 */
	private void showAlert(String msg){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle("Error");
 
			// set dialog message
			alertDialogBuilder
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	
	/**
	 * Add contact to list of added contacts
	 * @param arg0
	 */
	public void addClicked(View arg0) 
	{
		System.out.println("Add Clicked");
		String friend = (String) addFriends.getSelectedItem();
		
		
		if(!friendsAdded.contains(friend) && addFriends.getSelectedItemPosition() != 0){
			friendsAdded.add(friend);
			setAddedList();
		}
	}
	
	
	/**
	 * Remove contact from list of added contacts.
	 * @param arg0
	 */
	public void removeClicked(View arg0) 
	{
		System.out.println("Remove Clicked");
		String friend = (String) addedFriends.getSelectedItem();
		
		
		friendsAdded.remove(friend);
		setAddedList();
		
	}
	
	/**
	 * This is where the list of all added contacts are generated.
	 */
	private void setAddedList(){
		if(friendsAdded.size() == 0){
			List<String> friendsEmpty = new ArrayList<String>();
			friendsEmpty.add("No friends added...");
			ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,friendsEmpty.toArray());
			addedFriends.setAdapter(adapter);
			return;
		}
		List<String> tmp = friendsAdded;
		
		Collections.reverse(tmp);
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,tmp.toArray());
		addedFriends.setAdapter(adapter);
	}
	
	/**
	 * Is called when all contacts has been retrieved from DB. 
	 */
	@Override
	public void onRetrievedContactObject(List<Contact> contacts) {
		List<String> names = new ArrayList<String>();
		for(Contact c: contacts){
			String contactName = c.getFirstName()+" "+c.getLastName();
			if(contactName.equals(Contact.getCurrentUser())){
				continue;
			}
			names.add(contactName);
		}
		names.add(0,"Add friends to challenge...");
		
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,names.toArray());
		addFriends.setAdapter(adapter);
		
	}

	@Override
	public void onRetrievedObject(List<ParseObject> obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRetrievedChallengeObjects(List<Challenge> challenges) {
		// TODO Auto-generated method stub
		
	}
}
