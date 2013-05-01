package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
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
		System.out.println("Challenge clicked");
		/*
		Participant[] participants = { 
				new Participant("Mads", "http://graph.facebook.com/madsbf/picture?type=normal", new IntProgress(2)),
				new Participant("Du", "http://graph.facebook.com/dunguyen90/picture?type=normal", new IntProgress(3)), 
				new Participant("Noel", "http://graph.facebook.com/noelvang/picture?type=normal", new IntProgress(0)), 
				new Participant("Helge", "http://graph.facebook.com/helgemunkjacobsen/picture?type=normal", new IntProgress(5))};
		
		DummyContent.addItem(new ExerciseChallenge(DummyContent.ITEMS.size(), editName.getText().toString(), "Description", new Repetition(Integer.parseInt(editLength.getText().toString())), participants, true));
		*/
		
		this.finish();
	}
	
	public void addClicked(View arg0) 
	{
		System.out.println("Add Clicked");
		String friend = (String) addFriends.getSelectedItem();
		
		
		if(!friendsAdded.contains(friend) && addFriends.getSelectedItemPosition() != 0){
			friendsAdded.add(friend);
			setAddedList();
		}
	}
	
	
	public void removeClicked(View arg0) 
	{
		System.out.println("Remove Clicked");
		String friend = (String) addedFriends.getSelectedItem();
		
		
		friendsAdded.remove(friend);
		setAddedList();
		
	}
	
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

	@Override
	public void onRetrievedContactObject(List<Contact> contacts) {
		List<String> names = new ArrayList<String>();
		for(Contact c: contacts){
			names.add(c.getFirstName()+" "+c.getLastName());
		}
		names.add(0,"Add friends to challenge...");
		
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,names.toArray());
		addFriends.setAdapter(adapter);
		
	}

	@Override
	public void onRetrievedObject(List<ParseObject> obj) {
		// TODO Auto-generated method stub
		
	}
}
