package dk.partyroulette.contactsapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
		View view = inflater.inflate(contentResourceId, null);
		
		LinearLayout allanLayout = (LinearLayout) view.findViewById(R.id.allanLayout);
		if(allanLayout != null)
		{
			allanLayout.setOnClickListener((MainActivity) getActivity());
		}
		
		return view;
	}

}
