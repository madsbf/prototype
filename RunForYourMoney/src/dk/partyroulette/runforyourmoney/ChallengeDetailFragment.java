package dk.partyroulette.runforyourmoney;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import dk.partyroulette.runforyourmoney.R;
import dk.partyroulette.runforyourmoney.R.id;
import dk.partyroulette.runforyourmoney.R.layout;
import dk.partyroulette.runforyourmoney.DummyContent;

/**
 * A fragment representing a single Challenge detail screen. This fragment is
 * either contained in a {@link ChallengeListActivity} in two-pane mode (on
 * tablets) or a {@link ChallengeDetailActivity} on handsets.
 */
public class ChallengeDetailFragment extends Fragment implements OnClickListener
{
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Challenge mItem;
	
	private Button startButton;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ChallengeDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getLong(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_challenge_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.textName))
					.setText(mItem.getName());
			
			LinearLayout layoutProgress = (LinearLayout) rootView.findViewById(R.id.layoutProgress);
			
			LinearLayout[] participantViews = new LinearLayout[mItem.getParticipants().length];
			for(int i = 0; i < mItem.getParticipants().length; i++)
			{
				LinearLayout participantView = (LinearLayout) inflater.inflate(R.layout.participant,
						container, false);
				
				TextView textUpperBound = (TextView) participantView.findViewById(R.id.textUpperBound);
				TextView textLowerBound = (TextView) participantView.findViewById(R.id.textLowerBound);
				
				textUpperBound.setText(String.valueOf(mItem.getRepetition().getAmount()));
				textLowerBound.setText("0");
				
				layoutProgress.addView(participantView);
				participantViews[i] = participantView;
			}
			
			new ImageLoader(participantViews).execute(mItem.getParticipants());
		}
		
		startButton = (Button) rootView.findViewById(R.id.buttonStart);
		startButton.setOnClickListener(this);

		return rootView;
	}
	
	private class ImageLoader extends AsyncTask<Participant, Void, Bitmap[]>
	{
		private LinearLayout[] parents;
		ArrayList<Participant> sortedParticipants;
		
		public ImageLoader(LinearLayout[] parents)
		{
			super();
			this.parents = parents;
		}

		@Override
		protected Bitmap[] doInBackground(Participant... participants) {

			if(participants.length > 0)
			{
				sortedParticipants = new ArrayList<Participant>();
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
			for(int i = 0; i < bitmaps.length; i++)
			{
				Bitmap fixedBitmap = BitmapUtilities.roundCornersBitmap(Bitmap.createScaledBitmap(BitmapUtilities.cropBitmap(bitmaps[i]), 100, 100, false));
				
				ProgressBar progressBar = (ProgressBar) parents[i].findViewById(R.id.progressBar);
				ImageView imageParticipant = (ImageView) parents[i].findViewById(R.id.imageProfile);
				
				progressBar.setProgress(sortedParticipants.get(i).getProgress());
				imageParticipant.setImageBitmap(fixedBitmap);
			}
		}

	}

	@Override
	public void onClick(View view) 
	{
		if(view.getId() == R.id.buttonStart)
		{
			Intent intent = new Intent(getActivity(), RunStatisticsActivity.class);
		    startActivity(intent);
		}
		
	}
}
