package dk.partyroulette.contactsapp;
import dk.partyroulette.contactsapp.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;


public class MainActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);

		ActionBar actionBar = getActionBar();		
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);

		
		Tab tabAll = actionBar.newTab()
	            .setText("All Contacts")
	            .setTabListener(new MyTabListener(R.layout.list_view_all));
	    actionBar.addTab(tabAll);
	    
	    Tab tabFavourites = actionBar.newTab()
	            .setText("Favourites")
	            .setTabListener(new MyTabListener(R.layout.list_view_favourites));
	    actionBar.addTab(tabFavourites);
	    
	    Tab tabNetwork = actionBar.newTab()
	            .setText("Network")
	            .setTabListener(new MyTabListener(R.layout.list_view_network));
	    actionBar.addTab(tabNetwork);
	    
	    Tab tabFamily = actionBar.newTab()
	            .setText("Family")
	            .setTabListener(new MyTabListener(R.layout.list_view_family));
	    actionBar.addTab(tabFamily);
	}

	

}
