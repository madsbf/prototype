package dk.partyroulette.contactsapp;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

public class MyTabListener implements TabListener 
{
	private ListFragment fragment;
	
	public MyTabListener(int resourceId)
	{	
		fragment = new ListFragment();
		Bundle args = new Bundle();
		args.putInt("content_resource_id", resourceId);
		fragment.setArguments(args);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		ft.replace(R.id.fragmentContainer, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
		ft.remove(fragment);
	}

}
