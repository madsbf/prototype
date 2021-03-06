package dk.partyroulette.runforyourmoney;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.control.RetrievedObjectListener;
import dk.partyroulette.runforyourmoney.datalayer.Challenge;
import dk.partyroulette.runforyourmoney.datalayer.Contact;
import dk.partyroulette.runforyourmoney.datalayer.Participant;
import dk.partyroulette.runforyourmoney.datalayer.RunObject;

/**
 * An activity representing a list of Challenges. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ChallengeDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ChallengeListFragment} and the item details (if present) is a
 * {@link ChallengeDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ChallengeListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ChallengeListActivity extends FragmentActivity implements
ChallengeListFragment.Callbacks, OverviewFragment.Callbacks
{

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private static ChallengeListFragment challengeListFragment;
	private List<RunObject> runObjects = new ArrayList<RunObject>();
	public static ChallengeListActivity challengeListActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		// Set Parse parameters
		Parse.initialize(this, "aAnnNfcUtvwSLy7XD2ImtnbLpwYko5Dp1FdCheuC","DDDbciaF9P4DcXXodmAJIfVn1M3bQTJ0CtS0Cc9t"); 
		PushService.setDefaultPushCallback(this, ChallengeListActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());
		if(Contact.checkForInstallation()){
			System.out.println("INSTALLED");
			setContentView(R.layout.activity_challenge_list);
		}else{
			System.out.println("NOT INSTALLED");
			setContentView(R.layout.login);

		}
		challengeListActivity = this;

		// HACK til at vise menu-knap
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		getActionBar().setDisplayShowTitleEnabled(false);

		if (findViewById(R.id.challenge_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			challengeListFragment = ((ChallengeListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.challenge_list));

			challengeListFragment.setActivateOnItemClick(true);
		}

		//this.challengeListFragment = (ChallengeListFragment) getFragmentManager().findFragmentById(R.id.challenge_list);

		// TODO: If exposing deep links into your app, handle intents here.

		//get data from parse first!
		String fullname = Contact.getCurrentUser();
		ParseQuery query = new ParseQuery("GPSData");
		query.whereEqualTo("owner", fullname);
		query.include("gpsCoord.objectId");
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e==null)
				{
					for(ParseObject p : objects)
					{	//list of parseObjects
						ArrayList<ArrayList<Float>> run = new ArrayList<ArrayList<Float>>();
						for(Object gpsCoord : p.getList("gpsCoord"))
						{
							if(gpsCoord instanceof ParseObject)
							{
								//save data
								ParseObject coordObject = (ParseObject) gpsCoord;
								ArrayList<Float> coord = new ArrayList<Float>(); 
								coord.add(coordObject.getNumber("lat").floatValue());
								coord.add(coordObject.getNumber("ln").floatValue());
								coord.add(coordObject.getNumber("acc").floatValue());
								coord.add(coordObject.getNumber("time").floatValue());
								run.add(coord);
							}
						}

						runObjects.add(new RunObject(gpsDataSort(run),p.getCreatedAt()));
					}
					//update runObjects
					System.out.println("Got runobjects");
					Collections.sort(runObjects, new RunObjectComparator());
				} else {
					Log.d("Error retrieving GPS data for user " + Contact.getCurrentUser(), e.getMessage());
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//inflate with your particular xml
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (mTwoPane) {
			// TODO

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			switch(item.getItemId())
			{
			case R.id.item1:
				Intent detailIntent = new Intent(this,
						AddChallengeActivity.class);
				startActivity(detailIntent);
				return true;
			case R.id.item2:
				if(runObjects!=null)
				{
					Intent intent = new Intent(this, StatsActivity.class);
					intent.putParcelableArrayListExtra("runObjects", (ArrayList<? extends Parcelable>) runObjects);
					startActivity(intent);
				}
				return true;
			case R.id.item3:
				if(runObjects!=null)
				{
					Intent intent = new Intent(this, ProfileActivity.class);
					intent.putParcelableArrayListExtra("runObjects", (ArrayList<? extends Parcelable>) runObjects);
					startActivity(intent);
				}
				return true;
			case R.id.item4:
				Intent intent = new Intent(this, NAActivity.class);
				intent.putExtra("title", "Find friends");
				startActivity(intent);
				return true;
			}			
		}
		return true;
	}

	/**
	 * Callback method from {@link ChallengeListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Long id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(ChallengeDetailFragment.ARG_ITEM_ID, id);
			ChallengeDetailFragment fragment = new ChallengeDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.challenge_detail_container, fragment)
			.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					ChallengeDetailActivity.class);
			detailIntent.putExtra(ChallengeDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	/**
	 * Register the user of the app. This method should only be called first time app opens.
	 * @param view
	 */
	public void registerUser(View view){
		EditText firstNameText = (EditText) findViewById(R.id.firstNameText);
		EditText lastNameText = (EditText) findViewById(R.id.lastNameText);
		System.out.println("firstname: "+firstNameText.getText().toString());
		if(firstNameText.getText().toString().equals("") || lastNameText.getText().toString().equals("")){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Enter Credentials");

			// set dialog message
			alertDialogBuilder
			.setMessage("Please enter first and last name.")
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

			return;
		}

		Contact.addContactToDB(new Contact(firstNameText.getText().toString(),lastNameText.getText().toString()));
		//add name to shared preferences
		SharedPreferences settings = getApplicationContext().getSharedPreferences("runforyourmoney", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("name", firstNameText.getText().toString() + " " + lastNameText.getText().toString());
		editor.commit();

		setContentView(R.layout.activity_challenge_list);
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(lastNameText.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(firstNameText.getWindowToken(), 0);
	}

	
	@Override
	public void onProfileSelected() 
	{
		if(runObjects!=null)
		{
			Intent intent = new Intent(this, ProfileActivity.class);
			intent.putParcelableArrayListExtra("runObjects", (ArrayList<? extends Parcelable>) runObjects);
			startActivity(intent);
		}
	}

	private ArrayList<ArrayList<Float>> gpsDataSort(ArrayList<ArrayList<Float>> run) 
	{
		Collections.sort(run, new GPSCoordComparator());
		return run;
	}
}
