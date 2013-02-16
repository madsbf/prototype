package dk.partyroulette.contactsapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment 
{
	private int contentResourceId;
	
	@Override
	public void setArguments(Bundle args) 
	{
		contentResourceId = args.getInt("content_resource_id");
		super.setArguments(args);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		return inflater.inflate(contentResourceId, null);
	}

}
