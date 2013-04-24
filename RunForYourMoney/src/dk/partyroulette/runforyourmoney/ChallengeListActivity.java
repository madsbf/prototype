package dk.partyroulette.runforyourmoney;

import java.lang.reflect.Field;

import dk.partyroulette.runforyourmoney.Contact;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.PushService;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.R.id;
import dk.partyroulette.runforyourmoney.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
		ChallengeListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

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
			((ChallengeListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.challenge_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
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
		setContentView(R.layout.activity_challenge_list);
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(lastNameText.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(firstNameText.getWindowToken(), 0);
	}
}
