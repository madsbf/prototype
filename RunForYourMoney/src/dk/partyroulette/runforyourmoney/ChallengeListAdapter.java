package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Challenge challenge = objects.get(position);

		if (challenge != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView textName = (TextView) v.findViewById(R.id.textName);
			ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
			TextView textType = (TextView) v.findViewById(R.id.textType);
			TextView textUpperBound = (TextView) v.findViewById(R.id.textUpperBound);
			TextView textLowerBound = (TextView) v.findViewById(R.id.textLowerBound);
			LinearLayout layoutParticipants = (LinearLayout) v.findViewById(R.id.layoutParticipants);

			// check to see if each individual textview is null.
			// if not, assign some text!
			if (textName != null){
				textName.setText(challenge.getName());
				progressBar.setProgress(challenge.getParticipants()[0].getProgress());
				textType.setText(challenge.getTypeName());
				textUpperBound.setText(String.valueOf(challenge.getRepetition().getAmount()));
				textLowerBound.setText("0");
			}

			new ImageLoader(layoutParticipants).execute(challenge.getParticipants());
		}

		// the view must be returned to our activity
		return v;
	}

	private class ImageLoader extends AsyncTask<Participant, Void, Bitmap[]>
	{
		private LinearLayout parent;
		
		public ImageLoader(LinearLayout parent)
		{
			super();
			this.parent = parent;
		}

		@Override
		protected Bitmap[] doInBackground(Participant... participants) {

			if(participants.length > 0)
			{
				ArrayList<Participant> sortedParticipants = new ArrayList<Participant>();
				sortedParticipants.add(participants[0]);
				for(int i = 1; i < participants.length; i++)
				{
					int j = 1;
					while(i >= j && participants[i].getProgress() > sortedParticipants.get(i - j).getProgress())
					{
						j++;
					}
					sortedParticipants.add(i - j + 1, participants[i]);
				}
				
				Bitmap[] bitmaps = new Bitmap[participants.length];

				for(int i = 0; i < participants.length; i++)
				{
					bitmaps[i] = BitmapUtilities.loadBitmap(sortedParticipants.get(i).getImageUrl());
				}

				return bitmaps;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap[] bitmaps) 
		{
			for(Bitmap bitmap : bitmaps)
			{
				Bitmap fixedBitmap = BitmapUtilities.roundCornersBitmap(Bitmap.createScaledBitmap(BitmapUtilities.cropBitmap(bitmap), 80, 80, false));
				
				ImageView imageView = new ImageView(getContext());
				imageView.setPadding(5, 5, 0, 5);
				imageView.setImageBitmap(fixedBitmap);
				parent.addView(imageView);
			}
		}

	}
}
