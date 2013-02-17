package dk.partyroulette.contactsapp;
import dk.partyroulette.contactsapp.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements OnClickListener
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);

		ActionBar actionBar = getActionBar();		
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		addTab("All Contacts", R.layout.list_view_all, actionBar);
		addTab("Favourites", R.layout.list_view_favourites, actionBar);
		addTab("Colleagues", R.layout.list_view_network, actionBar);
		addTab("Family", R.layout.list_view_family, actionBar);
	}
	
	private void addTab(String text, int viewId, ActionBar actionBar)
	{
		Tab tab = actionBar.newTab()
	            .setText(text)
	            .setTabListener(new MyTabListener(viewId));
	    actionBar.addTab(tab);
	}

	@Override
	public void onClick(View clicked) 
	{
	    startActivity(new Intent(this, ContactActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options, menu);
	    return true;
	}
}
