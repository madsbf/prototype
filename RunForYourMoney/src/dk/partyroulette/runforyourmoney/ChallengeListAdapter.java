package dk.partyroulette.runforyourmoney;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChallengeListAdapter extends ArrayAdapter<Challenge> {

	// declaring our ArrayList of items
	private List<Challenge> objects;

	/* here we must override the constructor for ArrayAdapter
	 * the only variable we care about now is ArrayList<Item> objects,
	 * because it is the list of objects we want to display.
	 */
	public ChallengeListAdapter(Context context, int textViewResourceId, List<Challenge> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.challenge, null);

			Challenge challenge = objects.get(position);

			if (challenge != null) 
			{
				ChallengeView challengeView = new ChallengeView(challenge, getContext());
				challengeView.createListView(v);
			}
		}

		// the view must be returned to our activity
		return v;
	}
}