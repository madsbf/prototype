package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import dk.partyroulette.runforyourmoney.datalayer.RunObject;

public class ProfileActivity extends FragmentActivity implements RunListFragment.Callbacks, OverviewFragment.Callbacks
{
	private List<RunObject> runObjects = new ArrayList<RunObject>();
	public void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		runObjects = intent.getParcelableArrayListExtra("runObjects");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//a little hack? why isn't this automatically set to the activity title?
		getActionBar().setTitle("Profile");
	}

	public List<RunObject> getRunObjects() {
		return runObjects;
	}

	public void setRunObjects(List<RunObject> runObjects) {
		this.runObjects = runObjects;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.statsmenu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			this.finish();
			return true;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onItemSelected(RunObject runObject) 
	{
		if(runObject != null)
		{
			Intent intent = new Intent(this.getApplicationContext(), RunStatisticsActivity.class);
			intent.putExtra("runObject", runObject);
			startActivity(intent);
		} else {
			Log.e("ProfileActivity", "runObject is null");
		}
	}

	@Override
	public void onProfileSelected() 
	{
		// Already in profile view
	}
}
